package main;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import basicplayer1.BasicPlayer;
import basicplayer1.BasicPlayerException;
import listeners.KeyboardListener;
import listeners.MousekeyListener;
import main.Render;
import res.Player;
import res.NPC;
import res.Sword;
import res.Item;
//import res.NPCS AND STUFF;

/**
 * Update class for Trash Smash, updates at 60 ups, runs game logic
 * @author Brian Chen
 */
public class Update implements Runnable {
	//Update resources
	String mapID = "Rurikton";
	public volatile Player PC = new Player(GraphicsMain.WIDTH/2 - 96, GraphicsMain.HEIGHT - GraphicsMain.HEIGHT/16 - 96);
	public volatile LinkedList<NPC> NPCs = new LinkedList<NPC>(); 
	public volatile LinkedList<Sword> shopSwords = new LinkedList<Sword>(); 
	public volatile LinkedList<Item> shopItems = new LinkedList<Item>(); 
	public NPC speakingWith;
	public int commenceDialogue;
	public long startTime = 0;
	public long currentTime = 0;
	private int movementSpeed = 2;
	public boolean nextDialogue = false;
	public boolean shopSpawned = false;
	public boolean shopping = false;
	public boolean drawInfo = false;
	public boolean invScreen = false;
	public int insufficientGold = 0;
	public int drawInfoIndx = 0;
	public int dialogueOptions = 0;
	public int purchased = 0;
	//Music resources
	private static BasicPlayer player;
	
	//Thread resources
	public volatile ReentrantReadWriteLock lck = Main.lck;
	private Thread updateThread;
	public volatile static boolean running;
	
	/**
	 * Starts update thread
	 */
	public synchronized void start() {
		running = true;
		updateThread = new Thread(this, "Update Thread");
		updateThread.start();
		init();
	}
	
	/**
	 * Run loop for update thread, keeps its own UPS
	 */
	public void run() { 
		long lastTime = System.nanoTime();
		double nanoPerUpdate = 1000000000D/60D;
		double delta = 0D;
		if(Main.appState == Main.GAME_STATE) {
			while(running) {
				long now = System.nanoTime();
				delta += (now - lastTime) / nanoPerUpdate;
				lastTime = now;
				
				while(delta >= 1) {
					update();
					delta--;
				}
			}
		}
		if(running = false) {
			return;
		}
	}
	
	/**
	 * Terminates update thread
	 */
	public synchronized void stop() {
		running  = false;
	}
	
	private void init() {
		playMusic();
		startTime = System.currentTimeMillis();
	} 
	
	/**
	 * Opens new music stream and begins playing it
	 */
	private void playMusic() {
		player = new BasicPlayer();
		try {
			player.stop();
			player.open(getClass().getClassLoader().getResource("Music/Town.mp3"));
		    player.play();
		} catch (BasicPlayerException e) {
		    e.printStackTrace();
		}
	}

	/**
	 * Executes all game actions
	 */
	private void update() {
		handleMovement();
		spawnNPCs();
		collisionDetection();
		toggleMusic();
		repeatMusic();
	}
	
	
	private void collisionDetection(){
		Rectangle2D PlayerCharacter = PC.getBoundbox();
		Rectangle2D NPCharacter;
		for(int i = 0; i < NPCs.size(); i++){
			NPCharacter = NPCs.get(i).getBoundbox();
			if(PlayerCharacter.intersects(NPCharacter) && NPCs.get(i).hasDialogue()){
				if(dialogueOptions < 2){
					dialogueOptions = 3;
				}
				speakingWith = NPCs.get(i);
			}
			if(dialogueOptions == 0){
				commenceDialogue = 0;
			}
		}
	}
	
	private void spawnNPCs() {
		if(!shopSpawned){
			NPCs.add(new NPC(500, 500, "shop"));
			NPCs.add(new NPC(320, 400, "blacksmith"));
			shopSpawned = true;
			shopSwords.add(new Sword(30, 90, "Iron Sword"));
			shopSwords.add(new Sword(30, 200, "Katana"));
			shopSwords.add(new Sword(30, 310, "Obsidian Sword"));
			shopSwords.add(new Sword(30, 420, "Serrated Blade"));
			shopItems.add(new Item(30, 70, "Pie"));
			shopItems.add(new Item(30, 140, "Fish"));
			shopItems.add(new Item(30, 240, "Cake"));
			shopItems.add(new Item(30, 340, "Healing Salve"));
			shopItems.add(new Item(30, 450, "Teleport to Town"));
		}
	}

	private void handleMovement(){
		handlePCCommands();
		movePC();
		//moveNPC();
	}
	
	private void handlePCCommands(){
		lck.writeLock().lock();
		PC.setImage(0);
		if(KeyboardListener.up) {
			PC.setYvelocity(-movementSpeed);
			PC.setImage(1);
		}
		if(KeyboardListener.down) {
			PC.setYvelocity(movementSpeed);
			PC.setImage(2);
		}
		if(KeyboardListener.left) {
			PC.setXvelocity(-movementSpeed);
			PC.setImage(3);
		}
		if(KeyboardListener.right) {
			PC.setXvelocity(movementSpeed);
			PC.setImage(4);
		}
		if(KeyboardListener.R){
			if(dialogueOptions > 0)
				commenceDialogue = speakingWith.getDialogueLines();
		}
		if(KeyboardListener.space){
			nextDialogue = true;
			KeyboardListener.space = false;
		}
		if(KeyboardListener.escape){
			commenceDialogue = 0;
			drawInfo = false;
			drawInfoIndx = 0;
			nextDialogue = false;
			shopping = false;
			purchased = 0;
			invScreen = false;
		}
		if(KeyboardListener.I == true){
			invScreen = true;
			updateTime();
		}
		else
			invScreen = false;
		if(Main.update.commenceDialogue == 1 && (speakingWith.getID() == "shop" || speakingWith.getID() == "blacksmith")){
			Point p = new Point(MousekeyListener.getX(), MousekeyListener.getY());
			//System.out.println(MousekeyListener.getX() + " , " + MousekeyListener.getY());
			boolean somethingsTrue = false;
			if(speakingWith.getID() == "shop"){
				for(int i = 0; i < shopItems.size(); i++){
					Rectangle2D boundBox = shopItems.get(i).getBoundbox();
					if(boundBox.contains(p)){
						somethingsTrue = true;
						drawInfo = true;
						drawInfoIndx = i;
						if(MousekeyListener.mouseClicked){
							MousekeyListener.mouseClicked = false;
							if(PC.getGold() >= shopItems.get(i).getValue()){
								PC.setGold(PC.getGold() - shopItems.get(i).getValue());
								purchased = 12;
							}
							else{
								insufficientGold = 15;
							}
						}
					}
				}
				if(!somethingsTrue){
					drawInfo = false;
				}
			}
			else{
				for(int i = 0; i < shopSwords.size(); i++){
					Rectangle2D boundBox = shopSwords.get(i).getBoundbox();
					if(boundBox.contains(p)){
						somethingsTrue = true;
						drawInfo = true;
						drawInfoIndx = i;
					}
				}
				if(!somethingsTrue){
					drawInfo = false;
				}
			}
		}
		else
			drawInfo = false;
	}
	private void updateTime(){
		currentTime = (System.currentTimeMillis() - startTime);
	}
	private void movePC(){
		PC.setY(PC.getY() + PC.getYvelocity());
		PC.setX(PC.getX() + PC.getXvelocity());
		PC.setXvelocity(0);
		PC.setYvelocity(0);
		PC.updateBoundbox();
	}
	/**
	 * Switching between Muting and Un-muting the music.
	 */
	private void toggleMusic(){ 
		if(!KeyboardListener.toggle) return; 
		else KeyboardListener.toggle = false;
		lck.writeLock().lock();
		if(player.getStatus() != BasicPlayer.PAUSED){
			try {
				player.pause();
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
		}
		else{
			try {
				player.resume();
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
		}
		lck.writeLock().unlock();
	}
	
	/**
	 * Allows the music player to repeat the music once it has ended.
	 */
	public void repeatMusic(){
		if(player.getStatus() == 2){ //If Player status is STOPPED
			try {
				player.play();
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
		}
	}
}
