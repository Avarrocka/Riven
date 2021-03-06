package main;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Random;
import basicplayer1.BasicPlayer;
import basicplayer1.BasicPlayerException;
import listeners.KeyboardListener;
import listeners.MousekeyListener;

import res.Area;
import res.Armor;
import res.Dart;
import res.Enemy;
import res.Player;
import res.NPC;
import res.Sword;
import res.Item;
import res.Quest;
//import res.NPCS AND STUFF;

/**
 * Update class for Riven, updates at 60 ups, runs game logic and stores important global variables.
 * @author Brian Chen
 */
public class Update implements Runnable {
	//Update Resources
	
	//Area/Map Resources
	public String mapID = "";
	public Area area;
	//Main Player Character
	public volatile static Player PC = new Player(GraphicsMain.WIDTH/2 - 96, GraphicsMain.HEIGHT - GraphicsMain.HEIGHT/16 - 96);
	//Interactable objects/projectiles
	public volatile LinkedList<NPC> NPCs = new LinkedList<NPC>(); 
	public volatile LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	public volatile LinkedList<Dart> darts = new LinkedList<Dart>();
	public Point2D p1, p2; //Grappling hook points
	//Collison rectangle creator
	public volatile LinkedList<Rectangle2D> testRects = new LinkedList<Rectangle2D>();
	//Shopkeeper inventory LinkedLists
	public volatile LinkedList<Sword> shopSwords = new LinkedList<Sword>(); 
	public volatile LinkedList<Item> shopItems = new LinkedList<Item>(); 
	public volatile LinkedList<Armor> shopArmor = new LinkedList<Armor>();
	//Global variables of other importance	
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
	public boolean frostBoss = false;
	public boolean flameBoss = false;
	public boolean earthBoss = false;
	public boolean invScreen = false;
	public boolean questScreen = false;
	public boolean portalScreen = false;
	public boolean portalOnline = false;
	public boolean map = false;
	//Ability Cooldowns and Booleans
	public boolean shooting = false;
	public boolean healing = false;
	public boolean hasBeenHooked = false;
	public int qCD = 0;
	public int wCD = 0;
	public int eCD = 0;
	public boolean areasSpawned = false;
	//Variables associated with Quests
	public volatile LinkedList<Quest> quests = new LinkedList<Quest>();
	public int ouroboros = 0;
	public boolean gem = false;
	public boolean priamTask = false;
	public boolean priamDone = false;
	public int priamIndex = 0;
	public int slimesSlain = 0;
	public boolean fixingPortal = false;
	public boolean SWTCH = false;
	public boolean fbChest = false;
	public Rectangle2D magicSwitch = new Rectangle2D.Double(276, 360, 23, 18);
	public Rectangle2D treasureChest = new Rectangle2D.Double(500, 400, 38, 32);
	//Inventory and HUD variables
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
	//Random Generator
	Random RNG = new Random();
	//Directional constants for direction PC is facing.
	public final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	//Music resources
	private static BasicPlayer player;	
	//Thread resources
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
		mapID = "Taverly";
		splashScreenTime = 10;
		grapple = new Line2D.Double(0,0,0,0);
		nb = PC.getBoundbox();
		attackBox = PC.getBoundbox();
		playMusic();
		startTime = System.currentTimeMillis();
		area = new Area(mapID);
		shopItems.add(new Item(140, 90, "Cinnamon Pumpkin Pie"));
		shopItems.add(new Item(140, 180, "Fish Steak"));
		shopItems.add(new Item(140, 270, "Chocolate Raspberry Cake"));
		shopItems.add(new Item(140, 360, "Healing Salve"));
		shopItems.add(new Item(140, 450, "Teleport to Town"));
		shopArmor.add(new Armor(140, 90, "Plated Armor"));
		shopArmor.add(new Armor(140, 200, "Tempered Armor"));
		shopArmor.add(new Armor(140, 310, "Steel Armor"));
		shopArmor.add(new Armor(140, 420, "Darksteel Armor"));
		shopSwords.add(new Sword(140, 90, "Iron Sword"));
		shopSwords.add(new Sword(140, 200, "Katana"));
		shopSwords.add(new Sword(140, 310, "Steel Sword"));
		shopSwords.add(new Sword(140, 420, "Serrated Blade"));
	} 
	
	/**
	 * Opens new music stream and begins playing it
	 */
	private void playMusic() {
		player = new BasicPlayer();
		System.out.print("playing music");
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
	
	/**
	 * Most objects that need to be Updated and Updated here, including collision boxes, modes, HP, and location
	 */
	private void updateObjects() {
		area.update();
		//Updates Portal object to see if Fixing Portal has been completed
		if(area.hasPortal()){
			area.getPortal().updatePortal();
		} //Updates and checks locations of NPCs
		for(int i = 0; i < NPCs.size(); i++){
			NPCs.get(i).updateBoundbox();
			NPCs.get(i).updateSmall();
			NPCs.get(i).onMapUpdate();
		} //Updates the location of Darts
		for(int i = 0; i < darts.size(); i++){
			darts.get(i).update();
			if(darts.get(i).returnHit() == true){
				darts.remove(i);
			}
			else if(darts.get(i).getX() > 1024 || darts.get(i).getX() < 0 || darts.get(i).getY() > 768 || darts.get(i).getY() < 0){
				darts.remove(i);
			}
			else{
				for(int m = 0; m < enemies.size(); m++){
					if(darts.get(i).getBoundbox().intersects(enemies.get(m).getSmall())){
						darts.get(i).updateHit(true);
						enemies.get(m).damage(darts.get(i).getDamage());
					}
				}
			}
		}
		//Updates location and status of Enemy
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update();
			if(enemies.get(i).getHP() <= 0){
				if(priamTask && slimesSlain <= 5){ //Quest completion
					if(enemies.get(i).getID() == "slime"){
						slimesSlain++;
					}
				}
				//Enemy death loot spawns
				moneyDrop = enemies.get(i).rollMoney();
				moneyDraw = 60;
				PC.setGold(PC.getGold() + moneyDrop);
				PC.setEXP(enemies.get(i).getEXP());
				enemies.get(i).rollLoot();
				if(enemies.get(i).getID() == "squid"){//Boolean if boss defeated
					this.frostBoss = true;
				}
				enemies.remove(i);
			}
		}//Checks quest progress and removes it according to completion
		for(int i = 0; i < quests.size(); i++){
			quests.get(i).update();
			if(quests.get(i).getDone()){
				quests.remove(i);
			}
		}//Checks death of Player Character, respawns in Taverly.
		if(PC.getHealth() <= 0){
			area = new Area("Taverly");
			PC.setHealth(PC.getMaxHealth());
			PC.setX(GraphicsMain.WIDTH/2 - 96);
			PC.setY(GraphicsMain.HEIGHT - GraphicsMain.HEIGHT/16 - 96);
			NPCs.clear();
			enemies.clear();
			darts.clear();
		}
	}

	/**
	 * Spawns things into the game
	 */
	private void spawnThings() {
		spawnMap();
		if(!(area.getID() == "Taverly")){
			spawnEnemies();
		}
	}
	
	/**
	 * Spawns Map and NPCs according to NPCs contained in this.area
	 */
	private void spawnMap() {
		if(mapID == "Taverly" && !NPCsSpawned){
			NPCsSpawned = true;
			NPCs = area.getNPCs();
		}
		else{
			NPCsSpawned = true;
			NPCs = area.getNPCs();
		}
	}

	/**
	 * Spawns enemies according to enemies available in this.area.
	 */
	private void spawnEnemies() {
		if(enemySpawnTime == 0 && this.enemies.size() <= 20){
			Enemy e = area.spawnEnemy();
			if(e != null)
				enemies.add(e);
			enemySpawnTime = 1000;
		}
		else{
			enemySpawnTime--;
		}
	}

	/**
	 * Detects collisions with leave areas and NPCs.
	 */
	private void collisionDetection(){
		collisionWithNPCs();
		if(!area.getLeaveAreas().isEmpty()){
			collisionWithLAreas();
		}
	}
	
	/**
	 * Checks collision between PC and Leave Areas, then teleports player into a new Area object.
	 */
	private void collisionWithLAreas() {
		LinkedList<Integer> moveDir = area.getMoveDir();
		LinkedList<Rectangle2D> leaveArea = area.getLeaveAreas(); 
		Rectangle2D PlayerCharacter = PC.getBoundbox();
		Rectangle2D LArea;
		for(int i = 0; i < leaveArea.size(); i++){
			LArea = leaveArea.get(i);
			if(!(i >= area.getLeaveAreaNames().size()))
				mapID = area.getLeaveAreaNames().get(i);
			if(PlayerCharacter.intersects(LArea)){
				area = new Area(mapID);
				splashScreenTime = 100;
				NPCs.clear();
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
			}
		}
	}
	
	/**
	 * Detects collision with NPCs and other items to trigger events
	 */
	private void collisionWithNPCs(){
		Rectangle2D PlayerCharacter = PC.getBoundbox();
		Rectangle2D NPCharacter;
		boolean stillTouching = false;
		if(area.getID() == "Frostgorge5" && !fbChest){
			if(PlayerCharacter.intersects(treasureChest)){
				PC.addQuestItem(new Item(0, 0, "Water Rune"));
				this.fbChest = true;
			}
		}
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

	/**
	 * Moves PC, objects, and grapples as well as updating time for important things.
	 */
	private void handleMovement(){
		handlePCCommands();
		movePC();
		moveEnemies();
		if(shooting){
			moveGrapple();
			pullEnemy();
		}
		updateTimes();
	}
	
	/**
	 * Moves enemies in area
	 */
	private void moveEnemies() {
		if(area.getID() != "Taverly"){
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).trackPC();
			}
		}
	}

	/**
	 * Detects if an Enemy has been pulled, and continues to pull them.
	 */
	private void pullEnemy() {
 		if(grapple != null){
 			Point p = new Point((int)grapple.getX2(), (int)grapple.getY2());
 			for(int i = 0; i < enemies.size(); i++){
 				if(enemies.get(i).getSmall().contains(p)){
 					enemyHooked(i);
 				}
 			}
 			if(magicSwitch.contains(p) && area.getID() == "Turandal3"){
 				SWTCH = true;
 			}
 		}
 	}
 	
 	private void enemyHooked(int i){
 		if(grapple != null){
 			//Damages enemy on first iteration of pulling.
			if(eCD == 801){
				if(!Update.PC.e2)
					enemies.get(i).damage(Update.PC.getDamage());
				else
					enemies.get(i).damage(3*(Update.PC.getDamage()));
				if(Update.PC.e1){
					System.out.print("STUNT");
					enemies.get(i).stun(300);
				}
	 			hasBeenHooked = true;
	 			eCD--;
	 		}
			//Also damages, but on a lower cooldown
			else if(eCD == 561 && Update.PC.e3){
				if(!Update.PC.e2)
					enemies.get(i).damage(Update.PC.getDamage());
				else
					enemies.get(i).damage(3*(Update.PC.getDamage()));
				if(Update.PC.e1){
					enemies.get(i).stun(300);
				}
	 			hasBeenHooked = true;
	 			eCD--;
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
 	}
	
 	/**
	 * Moves the grapple towards the Player Character, but out of Melee distance
	 */
	private void moveGrapple() {
		if(grapple != null){
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
				hasBeenHooked = false;
			}
		}
	}
	
	/**
	 * Creates a Grappling Hook to shoot out
	 */ 
	private void spawnGrapplingHook() {
		int X = MousekeyListener.getX();
		int Y = MousekeyListener.getY();
		Point p = new Point(PC.getX()+(PC.getWidth()/2), PC.getY() + (PC.getHeight()/2));
		Point p2 = new Point(X, Y);
		grapple = new Line2D.Double(p, p2);
	}

	/**
	 * Creates a Dart to shoot out
	 */ 
	private void spawnDart(){
		darts.add(new Dart(PC.getX(), PC.getY(), PC.getDir()));
	}

	/**
	 * Uses KeyListener to process commands and prompts by the User to control Player Character.
	 */
	private void handlePCCommands(){
		movementSpeed = 2;
		PC.setImage(0);
		if(KeyboardListener.up && !(PC.getAttacking())) {
			PC.setYvelocity(-movementSpeed);
			PC.setImage(1);
			PC.updateDir();
		}
		else if(KeyboardListener.down && !(PC.getAttacking())) {
			PC.setYvelocity(movementSpeed);
			PC.setImage(2);
			PC.updateDir();
		}
		else if(KeyboardListener.left && !(PC.getAttacking())) {
			PC.setXvelocity(-movementSpeed);
			PC.setImage(3);
			PC.updateDir();
		}
		else if(KeyboardListener.right && !(PC.getAttacking())) {
			PC.setXvelocity(movementSpeed);
			PC.setImage(4);
			PC.updateDir();
		}
		if(KeyboardListener.R){
			if(dialogueOptions > 0){
				commenceDialogue = speakingWith.getDialogueLines();
			}
		}
		if(KeyboardListener.Q){
			if(qCD == 0){
				spawnDart();
				if(!Update.PC.q3)
					qCD = 100;
				else
					qCD = 70;
			}	
		}
		if(KeyboardListener.W){
			if(wCD == 0){
				healing = true;
				healingTime = 100;
				if(Update.PC.w1){
					PC.setInvin(100);
				}
				if(!Update.PC.w3)
					wCD = 3000;
				else
					wCD = 2100;
			}
		}
		if(KeyboardListener.E){
			if(eCD == 0){
				spawnGrapplingHook();
				shooting = true;
				if(!Update.PC.e3)
					eCD = 801;
				else
					eCD = 561;
			}	
		}
		if(KeyboardListener.space){
			if(commenceDialogue > 0){
				nextDialogue = true;
				KeyboardListener.space = false;
			}
			else if(!PC.getAttacking()){
				PC.attacking(40);
				PC.doAttack();
				KeyboardListener.space = false;
			}
		}
		if(KeyboardListener.U){
			questScreen = true;
		}
		else
			questScreen = false;
		if(KeyboardListener.space){
			
		}
		//Cancels all UI
		if(KeyboardListener.escape){
			KeyboardListener.escape = false;
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
		if(KeyboardListener.M == true){
			KeyboardListener.M = false;
			map = !map;
		}
		if(Main.update.commenceDialogue == 1 && (speakingWith.getID() == "shop" || speakingWith.getID() == "blacksmith"|| speakingWith.getID() == "armorsmith")){
			Point p = new Point(MousekeyListener.getX(), MousekeyListener.getY());
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
								quickSort(0, PC.invItems.size()-1);
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

	/**
	 * Manages and uses/switches inventory items with equip items
	 */ 
	private void manageInventory() {
		boolean somethingsTrue = false;
		Point p = new Point(MousekeyListener.getX(), MousekeyListener.getY());
		//Gives information on where the mouse is - and displays info text based on that information
		for(int i = 0; i < Update.PC.invSwords.size(); i++){
			Rectangle2D boundBox = new Rectangle2D.Double(400+(65*i), 220, 50, 50);
			if(boundBox.contains(p)){
				if(MousekeyListener.mouseClicked){
					MousekeyListener.mouseClicked = false;
					PC.setWeapon(Update.PC.invSwords.get(i), i);
				}
				drawInvIndx = i;
				drawWhich = 2;
				somethingsTrue = true;
			}
		}
		for(int i = 0; i < Update.PC.invArmor.size(); i++){
			Rectangle2D boundBox = new Rectangle2D.Double(400+(65*i), 350, 50, 50);
			if(boundBox.contains(p)){
				if(MousekeyListener.mouseClicked){
					MousekeyListener.mouseClicked = false;
					PC.setArmor(Update.PC.invArmor.get(i), i);
				}
				drawInvIndx = i;
				drawWhich = 3;
				somethingsTrue = true;
			}
		}
		for(int i = 0; i < Update.PC.invItems.size(); i++){
			Rectangle2D boundBox = new Rectangle2D.Double(400+(65*i), 90, 50, 50);
			if(boundBox.contains(p)){
				if(MousekeyListener.mouseClicked){
					MousekeyListener.mouseClicked = false;
					//Activates the selected item, or rather uses it
					PC.activateItem(Update.PC.invItems.get(i), i);
					drawInvIndx = i-1;
				}
				else
					drawInvIndx = i;
				drawWhich = 1;
				somethingsTrue = true;
			}
		}
		for(int i = 0; i < Update.PC.qItems.size(); i++){
			Rectangle2D boundBox = new Rectangle2D.Double(400+(65*i), 480, 50, 50);
			if(boundBox.contains(p)){
				drawInvIndx = i;
				drawWhich = 4;
				somethingsTrue = true;
			}
		}
		//If nothing is hovered/selected, nothing is drawn in Render
		if(!somethingsTrue){
			drawInvIndx = -1;
			drawWhich = 0;
		}
		//If the last item is used, then the drawIndex decreases as to not cause a NPE.
		if(drawWhich == 0){
			if(drawInvIndx == PC.invItems.size()){
				drawInvIndx--;
			}
		}
	}

	/**
	 * Quicksort used to sort items based on value when purchased.
	 * @param low The lower part of the array
	 * @param high The higher part of the array
	 */
	public static void quickSort(int low, int high){
		if (low < high){
		    int p = partition(low, high);
		    quickSort(low, p - 1);
		    quickSort(p + 1, high);
		}
	}
	
	/**
	 * The partition method used in quicksort
	 * @param low
	 * @param high
	 * @return
	 */
	private static int partition(int low, int high) {
		 int pivotIndex = low;
	     int pivotValue = PC.invItems.get(pivotIndex).getValue();
	     swap(high, pivotIndex);
	     int storeIndex = low;
	     for(int i = low; i <= high-1; i++){
	    	 if(PC.invItems.get(i).getValue() <= pivotValue){
	    		 swap(i, storeIndex);
	    		 storeIndex += 1;
	    	 }
	     }
	     swap(high, storeIndex);
	     return storeIndex;
	}
	
	/**
	 * The swap method used in quicksort
	 * @param i
	 * @param j
	 */
	private static void swap(int i, int j){
		if(j == PC.invItems.size()-1){
			return;
		}
		Item temp = PC.invItems.get(i);
		PC.invItems.remove(i);
		PC.invItems.add(i, PC.invItems.get(j));
		PC.invItems.remove(j);
		PC.invItems.add(j, temp);
	}

	/** 
	 * Keeps track of important times such as cooldown, playtime, and etc.
	 */
	private void updateTimes(){
		if(PC.getInvin()>0){
			PC.setInvin(PC.getInvin() - 1);
		}
		if(healingTime == 1){
			if(!Update.PC.w2)
				PC.heal((int)(PC.getMaxHealth()*(0.3)));
			else
				PC.heal((int)(PC.getMaxHealth()*(0.5)));
			healingTime--;
		}
		if(healingTime == 0){
			healing = false;
		}
		if(qCD > 0)
			qCD--;
		if(wCD > 0 && !healing){
			wCD--;
		}
		if(eCD > 0 && !shooting){
			eCD--;
		}
		//PC.setEXP(1);
		currentTime = (System.currentTimeMillis() - startTime);
	}
	
	/**
	 * Checks collision and detects inability to move of the Player Character.
	 */
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
			//Checks for Enemy Collisionbox
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
			//Checks for Area's Collisionbox
			for(int i = 0; i < area.getCollisionRects().size(); i++){
				Rectangle2D rn = area.getCollisionRects().get(i);
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
			//Checks to see if the player is intersecting the Area's portal, if it exists
			if(area.hasPortal()){
				if(nb.intersects(area.getPortal().getBoundbox())){
					portalScreen = true;
				}
				else
					portalScreen = false;
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
			
			//Finite boundaries regarding screen area.
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
