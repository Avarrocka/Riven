package main;

import java.awt.Point;
import java.awt.geom.Line2D;
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
import res.Area;
import res.Armor;
import res.CollisionRects;
import res.Enemy;
import res.Player;
import res.NPC;
import res.Sword;
import res.Item;
import res.Quest;
//import res.NPCS AND STUFF;

/**
 * Update class for Trash Smash, updates at 60 ups, runs game logic
 * @author Brian Chen
 */
public class Update implements Runnable {
	//Update resources
	public String mapID = "";
	public Area area;
	
	public volatile Player PC = new Player(GraphicsMain.WIDTH/2 - 96, GraphicsMain.HEIGHT - GraphicsMain.HEIGHT/16 - 96);
	
	public volatile LinkedList<NPC> NPCs = new LinkedList<NPC>(); 
	public volatile LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	
	public volatile LinkedList<Sword> shopSwords = new LinkedList<Sword>(); 
	public volatile LinkedList<Item> shopItems = new LinkedList<Item>(); 
	public volatile LinkedList<Armor> shopArmor = new LinkedList<Armor>();
	
	public volatile LinkedList<Rectangle2D> leaveArea = new LinkedList<Rectangle2D>();
	public volatile LinkedList<Rectangle2D> collisionRectangles = new LinkedList<Rectangle2D>();
	public volatile LinkedList<String> leaveAreaName = new LinkedList<String>();
	public volatile LinkedList<Integer> moveDir = new LinkedList<Integer>();
	
	public volatile LinkedList<Quest> quests = new LinkedList<Quest>();
	
	public NPC speakingWith;
	public Line2D grapple;
	public long startTime = 0;
	public long currentTime = 0;
	private int movementSpeed = 2;
	public int commenceDialogue;
	public int healingTime = 0;
	public int splashScreenTime = 0;
	public int enemySpawnTime = 200;
	public Rectangle2D nb;
	public Rectangle2D attackBox;
	
	public boolean nextDialogue = false;
	public boolean NPCsSpawned = false;
	public boolean shopping = false;
	public boolean drawInfo = false;
	
	public boolean invScreen = false;
	public boolean questScreen = false;
	
	public boolean portalOnline = false;
	public boolean map = false;
	
	//ability cooldowns/checks
	public boolean shooting = false;
	public boolean healing = false;
	public boolean hasBeenHooked = false;
	public int qCD = 0;
	public int wCD = 0;
	public boolean areasSpawned = false;
	
	//quest variables
	public boolean gem = false;
	public boolean priamTask = false;
	public boolean priamDone = false;
	public int priamIndex = 0;
	
	public int slimesSlain = 0;
	public boolean fixingPortal = false;
	
	//dialogue variables/inventory
	public int drawWhich = 0;
	public int insufficientGold = 0;
	public int alreadyHave = 0;
	public int drawInfoIndx = 0;
	public int dialogueOptions = 0;
	public int purchased = 0;
	public int drawInvIndx = 0;
	public int moneyDrop = 0;
	public int moneyDraw = 0;
	public int lootDraw = 0;
	public int levelUp = 0;
	public Armor dropArmor = null;
	public Sword dropSword = null;
	public Item dropItem = null;
	Random RNG = new Random();
	public final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	//Music resources
	private static BasicPlayer player;
	private static BasicPlayer voice;
	
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
		mapID = "Turandal1";
		splashScreenTime = 25;
		grapple = new Line2D.Double(0,0,0,0);
		voice = new BasicPlayer();
		nb = PC.getBoundbox();
		attackBox = PC.getBoundbox();
		playMusic();
		startTime = System.currentTimeMillis();
		area = new Area(mapID);
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
		if(splashScreenTime == 0){
			handleMovement();
			spawnThings();
			collisionDetection();
			toggleMusic();
			repeatMusic();
			updateObjects();
		}
	}
	
	
	private void updateObjects() {
		for(int i = 0; i < NPCs.size(); i++){
			NPCs.get(i).updateBoundbox();
			NPCs.get(i).updateSmall();
			NPCs.get(i).onMapUpdate();
		}
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update();
			if(enemies.get(i).getHP() <= 0){
				if(priamTask && slimesSlain <= 5){
					if(enemies.get(i).getID() == "slime"){
						slimesSlain++;
					}
				}
				moneyDrop = enemies.get(i).rollMoney(enemies.get(i).getID());
				moneyDraw = 60;
				PC.setGold(PC.getGold() + moneyDrop);
				PC.setEXP(enemies.get(i).getEXP());
				enemies.get(i).rollLoot();
				enemies.remove(i);
			}
		}
		for(int i = 0; i < quests.size(); i++){
			quests.get(i).update();
			if(quests.get(i).getDone()){
				quests.remove(i);
			}
		}
		if(PC.getHealth() <= 0){
			area = new Area("Taverly");
			PC.setHealth(PC.getMaxHealth());
			PC.setX(GraphicsMain.WIDTH/2 - 96);
			PC.setY(GraphicsMain.HEIGHT - GraphicsMain.HEIGHT/16 - 96);
		}
	}

	private void spawnThings() {
		spawnMap();
		spawnLeaveAreas();
		if(!(mapID == "Taverly")){
			spawnEnemies();
		}
	}

	private void spawnEnemies() {
		if(enemySpawnTime == 0){
			Enemy e = new Enemy(RNG.nextInt(1024), RNG.nextInt(700), "slime");
			Rectangle2D eBox = e.getBoundbox();
			boolean notInCorners = true;
			for(int i = 0; i < collisionRectangles.size(); i++){
				if(collisionRectangles.get(i).intersects(eBox)){
					notInCorners = false;
				}
			}
			if(notInCorners){
				enemySpawnTime = 1000;
				enemies.add(e);
			}
		}
		else{
			enemySpawnTime--;
		}
	}

	private void spawnLeaveAreas() {
		if(mapID == "Taverly" && !areasSpawned){
			leaveArea.add(new Rectangle2D.Double(0, 125, 10, 40));
			leaveAreaName.add("Turandal1");
			moveDir.add(LEFT);
			areasSpawned = true;
		}
		else if(mapID == "Turandal1" && !areasSpawned){
			leaveArea.add(new Rectangle2D.Double(GraphicsMain.WIDTH-10, 125, 10, 40));
			leaveAreaName.add("Taverly");
			moveDir.add(RIGHT);
			leaveArea.add(new Rectangle2D.Double(0, 350, 10, 70));
			leaveAreaName.add("Turandal2");
			moveDir.add(LEFT);
			areasSpawned = true;
		}
		else if(mapID == "Turandal2" && !areasSpawned){
			leaveArea.add(new Rectangle2D.Double(GraphicsMain.WIDTH-10, 350, 10, 70));
			leaveAreaName.add("Turandal1");
			moveDir.add(RIGHT);
			leaveArea.add(new Rectangle2D.Double(570, 20, 50, 9));
			leaveAreaName.add("RuinsofLargos");
			moveDir.add(UP);
			areasSpawned = true;
		}
		else if(mapID == "RuinsofLargos" && !areasSpawned){
			leaveArea.add(new Rectangle2D.Double(570, GraphicsMain.HEIGHT-10, 50, 9));
			leaveAreaName.add("Turandal2");
			moveDir.add(DOWN);
			areasSpawned = true;
		}
	}

	private void collisionDetection(){
		collisionWithNPCs(); //For interaction
		if(!leaveArea.isEmpty() && !leaveAreaName.isEmpty()){
			collisionWithLAreas();
		}
	}
	
	private void collisionWithLAreas() {
		Rectangle2D PlayerCharacter = PC.getBoundbox();
		Rectangle2D LArea;
		for(int i = 0; i < leaveArea.size(); i++){
			LArea = leaveArea.get(i);
			if(PlayerCharacter.intersects(LArea)){
				mapID = leaveAreaName.get(i);
				area = new Area(mapID);
				splashScreenTime = 50;
				NPCs.clear();
				enemies.clear();
				speakingWith = null;
				NPCsSpawned = false;
				if(moveDir.get(i) == LEFT){
					PC.setX(GraphicsMain.WIDTH - 80 -(int)LArea.getX());
					PC.setY((int)LArea.getY());
				}
				else if(moveDir.get(i) == RIGHT){
					PC.setX(((int)Math.abs(LArea.getX() - GraphicsMain.WIDTH)) + 20);
					PC.setY((int)LArea.getY());
				}
				if(moveDir.get(i) == UP){
					PC.setX((int)LArea.getX());
					PC.setY(GraphicsMain.HEIGHT - 85);				
				}
				if(moveDir.get(i) == DOWN){
					PC.setX((int)LArea.getX());
					PC.setY(40);
				}
				areasSpawned = false;
				leaveArea.clear();
				leaveAreaName.clear();
				collisionRectangles.clear();
				moveDir.clear();
			}
		}
	}

	private void collisionWithNPCs(){
		Rectangle2D PlayerCharacter = PC.getBoundbox();
		Rectangle2D NPCharacter;
		boolean stillTouching = false;
		for(int i = 0; i < NPCs.size(); i++){
			NPCharacter = NPCs.get(i).getBoundbox();
			if(PlayerCharacter.intersects(NPCharacter) && NPCs.get(i).hasDialogue()){
				if(dialogueOptions < 2){
					dialogueOptions = 3;
				}
				stillTouching = true;
				speakingWith = NPCs.get(i);
			}
		}
		if(dialogueOptions == 0 && !stillTouching){
			commenceDialogue = 0;
		}
	}
	
	private void spawnMap() {
		if(mapID == "Taverly" && !NPCsSpawned){
			NPCsSpawned = true;
			shopItems.add(new Item(30, 90, "Cinnamon Pumpkin Pie"));
			shopItems.add(new Item(30, 180, "Fish Steak"));
			shopItems.add(new Item(30, 270, "Chocolate Raspberry Cake"));
			shopItems.add(new Item(30, 360, "Healing Salve"));
			shopItems.add(new Item(30, 450, "Teleport to Town"));
			shopArmor.add(new Armor(30, 90, "Plated Armor"));
			shopArmor.add(new Armor(30, 200, "Tempered Armor"));
			shopArmor.add(new Armor(30, 310, "Steel Armor"));
			shopArmor.add(new Armor(30, 420, "Darksteel Armor"));
			shopSwords.add(new Sword(30, 90, "Iron Sword"));
			shopSwords.add(new Sword(30, 200, "Katana"));
			shopSwords.add(new Sword(30, 310, "Steel Sword"));
			shopSwords.add(new Sword(30, 420, "Serrated Blade"));
			NPCs = area.getNPCs();
			collisionRectangles = area.getCollisionRects();
		}
		else{
			NPCsSpawned = true;
			NPCs = area.getNPCs();
			collisionRectangles = area.getCollisionRects();
		}
	}

	private void handleMovement(){
		handlePCCommands();
		movePC();
		moveThings();
		updateTimes();
		if(shooting){
			moveGrapple();
			pullNPC();
		}
	}
	private void moveThings() {
		if(area.getID() != "Taverly"){
			for(int i = 0; i < enemies.size(); i++){
				if(!enemies.get(i).moving()){
					int face = RNG.nextInt(4)+1;
					enemies.get(i).move(face);
				}
			}
		}
	}

	private void pullNPC() {
 		if(grapple != null){
 			Point p = new Point((int)grapple.getX2(), (int)grapple.getY2());
 			for(int i = 0; i < enemies.size(); i++){
 				if(enemies.get(i).getSmall().contains(p)){
 					NPCHooked(i);
 				}
 			}
 		}
 	}
 	
 	private void NPCHooked(int i){
		if(qCD == 801){
			enemies.get(i).setHP(enemies.get(i).getHP() - 51);
			PC.setEXP(10);
 			playSFX("hooked");
 			hasBeenHooked = true;
 			qCD--;
 		}
 		Point p = new Point((int)grapple.getX2(), (int)grapple.getY2());
 		if(p.getX()-16 > enemies.get(i).getX())
 			enemies.get(i).setX((int)p.getX()-(enemies.get(i).getWidth()-20));
 		else{
 			enemies.get(i).setX((int)p.getX());
 		}
 		enemies.get(i).setY((int)p.getY() - (enemies.get(i).getHeight()-40));
 		enemies.get(i).update();
 	}
	
	private void playSFX(String s){
		if(s == "hooked"){
			int a = RNG.nextInt(2);
			if(a == 0){
				try {
					voice.stop();
					voice.open(getClass().getClassLoader().getResource("Music/hookHit.mp3"));
				    voice.play();
				} catch (BasicPlayerException e) {
				    e.printStackTrace();
				}
			}
			else if(a == 1){
				try {
					voice.stop();
					voice.open(getClass().getClassLoader().getResource("Music/hookHit2.mp3"));
				    voice.play();
				} catch (BasicPlayerException e) {
				    e.printStackTrace();
				}
			}		
		}
		if(s == "not hooked"){
			try {
				voice.stop();
				voice.open(getClass().getClassLoader().getResource("Music/hookMissed.mp3"));
			    voice.play();
			} catch (BasicPlayerException e) {
			    e.printStackTrace();
			}
		}
		if(s == "meditate"){
			try {
				voice.stop();
				voice.open(getClass().getClassLoader().getResource("Music/meditate.mp3"));
			    voice.play();
			} catch (BasicPlayerException e) {
			    e.printStackTrace();
			}
		}
	}
	private void moveGrapple() {
		Point p = new Point(PC.getX()+(PC.getWidth()/2), PC.getY() + (PC.getHeight()/2));
		if(grapple.getX2() >= p.getX()){
			grapple.setLine(p, new Point((int)grapple.getX2() - 4, (int)grapple.getY2()));
		}
		else if(grapple.getX2() <= p.getX()){
			grapple.setLine(p, new Point((int)grapple.getX2() + 4, (int)grapple.getY2()));
		}
		if(grapple.getY2() >= p.getY()){
			grapple.setLine(p, new Point((int)grapple.getX2(), (int)grapple.getY2() - 4));
		}
		else if(grapple.getY2() <= p.getY()){
			grapple.setLine(p, new Point((int)grapple.getX2(), (int)grapple.getY2() + 4));
		}
		if((Math.abs(grapple.getY2() - p.getY()) <= 70) && (Math.abs(grapple.getX2() - p.getX()) <= 70)){
			grapple = null;
			shooting = false;
			if(!hasBeenHooked)
				playSFX("not hooked");
			hasBeenHooked = false;
		}
	}

	private void handlePCCommands(){
		lck.writeLock().lock();
		movementSpeed = 2;
		PC.setImage(0);
		if(KeyboardListener.up && !(PC.getAttacking())) {
			PC.setYvelocity(-movementSpeed);
			PC.setImage(1);
		}
		else if(KeyboardListener.down && !(PC.getAttacking())) {
			PC.setYvelocity(movementSpeed);
			PC.setImage(2);
		}
		else if(KeyboardListener.left && !(PC.getAttacking())) {
			PC.setXvelocity(-movementSpeed);
			PC.setImage(3);
		}
		else if(KeyboardListener.right && !(PC.getAttacking())) {
			PC.setXvelocity(movementSpeed);
			PC.setImage(4);
		}
		if(KeyboardListener.R){
			if(dialogueOptions > 0){
				commenceDialogue = speakingWith.getDialogueLines();
			}
		}
		if(KeyboardListener.W){
			if(wCD == 0){
				playSFX("meditate");
				healing = true;
				healingTime = 100;
				wCD = 3000;
			}
		}
		if(KeyboardListener.space){
			if(commenceDialogue > 0){
				nextDialogue = true;
				KeyboardListener.space = false;
			}
			else if(!PC.getAttacking()){
				PC.attacking(40);
				attack();
				KeyboardListener.space = false;
			}
		}
		if(KeyboardListener.Q){
			if(qCD == 0){
				spawnGrapplingHook();
				shooting = true;
				qCD = 801;
			}				
		}
		if(KeyboardListener.U){
			questScreen = true;
		}
		else
			questScreen = false;
		if(KeyboardListener.escape){
			KeyboardListener.escape = false;
			//Cancels all UIs
			commenceDialogue = 0;
			drawInfo = false;
			drawInfoIndx = 0;
			nextDialogue = false;
			shopping = false;
			purchased = 0;
			invScreen = false;
			questScreen = false;
			map = false;
			KeyboardListener.I = false;
			KeyboardListener.U = false;
			drawInvIndx = 0;
			drawWhich = 0;
		}
		if(KeyboardListener.I == true){
			invScreen = true;
			manageInventory();
		}
		else
			invScreen = false;
		if(KeyboardListener.R == true){
			KeyboardListener.R = false;
			map = !map;
		}
		if(Main.update.commenceDialogue == 1 && (speakingWith.getID() == "shop" || speakingWith.getID() == "blacksmith"|| speakingWith.getID() == "armorsmith")){
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
								PC.addItem(shopItems.get(i));
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
			if(speakingWith.getID() == "blacksmith"){
				for(int i = 0; i < shopSwords.size(); i++){
					Rectangle2D boundBox = shopSwords.get(i).getBoundbox();
					if(boundBox.contains(p)){
						somethingsTrue = true;
						drawInfo = true;
						drawInfoIndx = i;
						if(MousekeyListener.mouseClicked){
							MousekeyListener.mouseClicked = false;
							if(PC.getGold() >= shopSwords.get(i).getValue()){
								if(!(PC.invSwords.contains(shopSwords.get(i)))){
									PC.setGold(PC.getGold() - shopSwords.get(i).getValue());
									PC.addItem(shopSwords.get(i));
									purchased = 12;
								}
								else
									alreadyHave = 15;
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
			if(speakingWith.getID() == "armorsmith"){
				for(int i = 0; i < shopArmor.size(); i++){
					Rectangle2D boundBox = shopArmor.get(i).getBoundbox();
					if(boundBox.contains(p)){
						somethingsTrue = true;
						drawInfo = true;
						drawInfoIndx = i;
						if(MousekeyListener.mouseClicked){
							MousekeyListener.mouseClicked = false;
							if(PC.getGold() >= shopArmor.get(i).getValue()){
								if(!(PC.invArmor.contains(shopArmor.get(i)))){
									PC.setGold(PC.getGold() - shopArmor.get(i).getValue());
									PC.addItem(shopArmor.get(i));
									purchased = 12;
								}
								else
									alreadyHave = 15;
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
		}
		else
			drawInfo = false;
	}


	private void attack() {
		int face = PC.getFace(); //3left 4right 1up 2down?
		if(face == 1){
			attackBox = new Rectangle2D.Double(PC.getX()-10, PC.getY()-20, PC.getWidth()+20, 20);
		}
		else if(face == 2){
			attackBox = new Rectangle2D.Double(PC.getX()-10, PC.getY()+PC.getHeight(), PC.getWidth()+20, 20);
		}
		else if(face == 3){
			attackBox = new Rectangle2D.Double(PC.getX()-20, PC.getY()-10, 20, PC.getHeight()+20);
		}
		else if(face == 4){
			attackBox = new Rectangle2D.Double(PC.getX() + PC.getWidth(), PC.getY()-10, 20, PC.getHeight()+20);
		}
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).getBoundbox().intersects(attackBox)){
				enemies.get(i).damage(PC.getDamage());
				enemies.get(i).retaliate();
			}
		}
		attackBox = null;
	}

	private void spawnGrapplingHook() {
		int X = MousekeyListener.getX();
		int Y = MousekeyListener.getY();
		Point p = new Point(PC.getX()+(PC.getWidth()/2), PC.getY() + (PC.getHeight()/2));
		Point p2 = new Point(X, Y);
		grapple = new Line2D.Double(p, p2);
	}

	private void manageInventory() {
		boolean somethingsTrue = false;
		Point p = new Point(MousekeyListener.getX(), MousekeyListener.getY());
		for(int i = 0; i < Main.update.PC.invSwords.size(); i++){
			Rectangle2D boundBox = new Rectangle2D.Double(400+(65*i), 220, 50, 50);
			if(boundBox.contains(p)){
				if(MousekeyListener.mouseClicked){
					MousekeyListener.mouseClicked = false;
					PC.setWeapon(Main.update.PC.invSwords.get(i), i);
				}
				drawInvIndx = i;
				drawWhich = 2;
				somethingsTrue = true;
			}
		}
		for(int i = 0; i < Main.update.PC.invArmor.size(); i++){
			Rectangle2D boundBox = new Rectangle2D.Double(400+(65*i), 350, 50, 50);
			if(boundBox.contains(p)){
				if(MousekeyListener.mouseClicked){
					MousekeyListener.mouseClicked = false;
					PC.setArmor(Main.update.PC.invArmor.get(i), i);
				}
				drawInvIndx = i;
				drawWhich = 3;
				somethingsTrue = true;
			}
		}
		for(int i = 0; i < Main.update.PC.invItems.size(); i++){
			Rectangle2D boundBox = new Rectangle2D.Double(400+(65*i), 90, 50, 50);
			if(boundBox.contains(p)){
				if(MousekeyListener.mouseClicked){
					MousekeyListener.mouseClicked = false;
					PC.activateItem(Main.update.PC.invItems.get(i), i);
					drawInvIndx = i-1;
				}
				else
					drawInvIndx = i;
				drawWhich = 1;
				somethingsTrue = true;
			}
		}
		for(int i = 0; i < Main.update.PC.qItems.size(); i++){
			Rectangle2D boundBox = new Rectangle2D.Double(400+(65*i), 480, 50, 50);
			if(boundBox.contains(p)){
				drawInvIndx = i;
				drawWhich = 4;
				somethingsTrue = true;
			}
		}
		if(!somethingsTrue){
			drawInvIndx = -1;
			drawWhich = 0;
		}
	}

	private void updateTimes(){
		if(healingTime == 1){
			PC.heal(20);
			healingTime--;
		}
		if(healingTime == 0){
			healing = false;
		}
		if(qCD > 0 && !shooting)
			qCD--;
		if(wCD > 0 && !healing){
			wCD--;
		}
		//PC.setEXP(1);
		currentTime = (System.currentTimeMillis() - startTime);
	}
	private void movePC(){
		if(!(healingTime > 0)){
			nb = new Rectangle2D.Double(PC.getX() + 4, PC.getY() + 4, PC.getWidth() - 8, PC.getHeight() - 8);
			boolean touch = false;
			int Rx = PC.getX()+PC.getWidth();
			int Lx = PC.getX();
			int Uy = PC.getY();
			int Dy = PC.getY() + PC.getHeight();
			//Checks for NPC Collisionbox
			for(int i = 0; i < NPCs.size(); i++){
				NPC rn = NPCs.get(i);
				if(nb.intersects(rn.getSmall())){
					Rectangle2D rm = rn.getSmall();
					if((Rx >= rm.getX() && Rx <= rm.getX()+rm.getWidth() && PC.getYvelocity() == 0)){
						PC.setX(PC.getX() - movementSpeed);
					}
					else if(Lx <= rm.getX() + rm.getWidth() && PC.getYvelocity() == 0){
						PC.setX(PC.getX() + movementSpeed);
					}
					if((Dy >= rm.getY() && Dy <= rm.getY()+rm.getHeight() && PC.getXvelocity() == 0)){
							PC.setY(PC.getY() - (movementSpeed));
						}
					else if(Uy <= rm.getY() + rm.getHeight() && PC.getXvelocity() == 0){
							PC.setY(PC.getY() + (movementSpeed));
					}			
				}
			}
			for(int i = 0; i < enemies.size(); i++){
				Enemy rn = enemies.get(i);
				if(nb.intersects(rn.getSmall())){
					Rectangle2D rm = rn.getSmall();
					if((Rx >= rm.getX() && Rx <= rm.getX()+rm.getWidth() && PC.getYvelocity() == 0)){
						PC.setX(PC.getX() - movementSpeed);
					}
					else if(Lx <= rm.getX() + rm.getWidth() && PC.getYvelocity() == 0){
						PC.setX(PC.getX() + movementSpeed);
					}
					if((Dy >= rm.getY() && Dy <= rm.getY()+rm.getHeight() && PC.getXvelocity() == 0)){
							PC.setY(PC.getY() - (movementSpeed));
						}
					else if(Uy <= rm.getY() + rm.getHeight() && PC.getXvelocity() == 0){
							PC.setY(PC.getY() + (movementSpeed));
					}			
				}
			}
			for(int i = 0; i < collisionRectangles.size(); i++){
				Rectangle2D rn = collisionRectangles.get(i);
				if(nb.intersects(rn)){
					if((Rx >= rn.getX() && Rx <= rn.getX()+rn.getWidth() && PC.getYvelocity() == 0)){
						PC.setX(PC.getX() - movementSpeed);
					}
					else if(Lx <= rn.getX() + rn.getWidth() && PC.getYvelocity() == 0){
						PC.setX(PC.getX() + movementSpeed);
					}
					if((Dy >= rn.getY() && Dy <= rn.getY()+rn.getHeight() && PC.getXvelocity() == 0)){
							PC.setY(PC.getY() - (movementSpeed));
						}
					else if(Uy <= rn.getY() + rn.getHeight() && PC.getXvelocity() == 0){
							PC.setY(PC.getY() + (movementSpeed));
					}			
				}
			}
			if(!touch){
				PC.setY(PC.getY() + PC.getYvelocity());
				PC.setX(PC.getX() + PC.getXvelocity());
				PC.setXvelocity(0);
				PC.setYvelocity(0);
			}
			else{
				PC.setXvelocity(0);
				PC.setYvelocity(0);
			}
			
			//Done code regarding boundaries.
			if(PC.getX() <= 2){
				PC.setX(3);
			}
			if(PC.getX() >= 970){
				PC.setX(969);
			}
			if(PC.getY() <= 26){
				PC.setY(27);
			}
			if(PC.getY() >= 700){
				PC.setY(699);
			}
			PC.updateBoundbox();
		}
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
