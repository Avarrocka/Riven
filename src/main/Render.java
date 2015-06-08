package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import javax.imageio.ImageIO;
import listeners.MousekeyListener;
import main.Update;
import res.Armor;
import res.Enemy;
import res.Map;
import res.NPC;
import res.Sword;
import res.Item;
import res.Quest;

//import res.NPC AND STUFF

/**
 * Render class for Riven, runs on render thread, contains draw methods
 * @author Brian Chen
 */
public class Render implements Runnable {
	//graphics resources
	private Graphics2D g;
	private Queue<BufferedImage> dblBuffer = new LinkedList<BufferedImage>();
	//vast array of Buffered Images used for graphics
	Map map = new Map(200, 100);
	BufferedImage Taverly;
	BufferedImage talkBubble, dialogueBox, interactBox;
	BufferedImage shop, inventory, questScreen, bPortal, rPortal, hook, hook2, mapUI;
	BufferedImage meditateAura, levelUp, HPUI, skillsUI, here, switchOn, switchOff, chest;
	BufferedImage sword[] = new BufferedImage[7];
	BufferedImage TaverlySplash, TurandalSplash, RuinsSplash;
	BufferedImage TurandalForest1, TurandalForest2, TurandalForest3, RuinsofLargos;
	
	/**
	 * Constructs the render object
	 * @param g
	 */
	public Render(Graphics2D g) {
		this.g = g;
	}

	/**
	 * Run method for render thread, triggers the draw list, keeps its own FPS
	 */
	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		double nanoPerUpdate = 1000000000D/50D;
		double delta = 0D;
		while(Update.running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nanoPerUpdate;
			lastTime = now;
			
			while(delta >= 1) {
				draw();
				delta--;
			}
		}
		if(Update.running = false) {
			return;
		}
	}
	//Loads all image objects into game from Assets folder
	private void init() { 
		try { //HUDs and UIs
			talkBubble = ImageIO.read(getClass().getClassLoader().getResource("Icons/talkBubble.png"));
			dialogueBox = ImageIO.read(getClass().getClassLoader().getResource("Icons/dialogueBox.png"));
			shop = ImageIO.read(getClass().getClassLoader().getResource("Equip/shop.png"));
			interactBox = ImageIO.read(getClass().getClassLoader().getResource("Icons/interactBubble.png"));
			questScreen = ImageIO.read(getClass().getClassLoader().getResource("Icons/quests.png"));
			mapUI = ImageIO.read(getClass().getClassLoader().getResource("Icons/mapBot.png"));
			chest = ImageIO.read(getClass().getClassLoader().getResource("Icons/treasureChest.png"));
			here = ImageIO.read(getClass().getClassLoader().getResource("Icons/hereArrow.png"));
			inventory = ImageIO.read(getClass().getClassLoader().getResource("Icons/inventory.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try { //skills and onscreen assets
			hook = ImageIO.read(getClass().getClassLoader().getResource("Icons/hook.png"));
			hook2 = ImageIO.read(getClass().getClassLoader().getResource("Icons/hook2.png"));
			skillsUI = ImageIO.read(getClass().getClassLoader().getResource("UI/SkillsUI.png"));
			switchOff = ImageIO.read(getClass().getClassLoader().getResource("Icons/switchOff.png"));
			switchOn = ImageIO.read(getClass().getClassLoader().getResource("Icons/switchOn.png"));
			HPUI = ImageIO.read(getClass().getClassLoader().getResource("UI/HPUI.png"));
			meditateAura = ImageIO.read(getClass().getClassLoader().getResource("Icons/meditateAura.png"));
			levelUp = ImageIO.read(getClass().getClassLoader().getResource("Icons/levelUp.png"));
			bPortal = ImageIO.read(getClass().getClassLoader().getResource("Icons/brokenPortal.png"));
			rPortal = ImageIO.read(getClass().getClassLoader().getResource("Icons/repairedPortal.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main command that calls functions to draw different aspects of the game
	 */
	private void draw() { 
		BufferedImage screen = new BufferedImage(GraphicsMain.WIDTH, GraphicsMain.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) screen.getGraphics();
		//Things are Drawn to the Screen here.
		if(Main.update.splashScreenTime == 0){
			drawBackground(g);
			drawNPC(g);
			drawEnemies(g);
			drawHUD(g);
			drawPrompts(g);
			drawPlayer(g);
			drawPortal(g);
			drawBounds(g);
			drawDialogue(g);
			drawUIs(g);
			drawMap(g);
		}
		else{
			drawSplashscreen(g);
		}
		dblBuffer.add(screen);
		if(dblBuffer.size() == 2) {
			this.g.drawImage(dblBuffer.poll(), 0, 0, GraphicsMain.WIDTH, GraphicsMain.HEIGHT, null);
		}
	}
	
	/**
	 * Draw Method for all Enemies encountered on Main.update.area
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawEnemies(Graphics2D g) {
		LinkedList<Enemy> enemies = Main.update.enemies;
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.draw(g);
		}
	}
	
	/**
	 * Draws the map UI and image from Render.map
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawMap(Graphics2D g){
		map.updateCurrentLocation();
		if(Main.update.map){
			map.draw(g);
			g.drawImage(mapUI, 295, 657, 420, 100, null);
			Point2D p = new Point2D.Double(MousekeyListener.getX(), MousekeyListener.getY());
			String name = map.poiName(p);
			String desc = map.poiDesc(p);
			if(name != null && desc != null){
				g.setFont(new Font("Rockwell", Font.BOLD, 16));
				g.setColor(Color.BLACK);
				g.drawString("Location: " + name, 325, 680);
				g.setFont(new Font("Rockwell", Font.BOLD, 12));
				g.drawString(desc, 325, 710);
			}
		}
	}
	
	/**
	 * Draws the loading image for Main.update.area
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawSplashscreen(Graphics2D g) {
		g.drawImage(Main.update.area.getSplash(), 0, 0, 1024, 768, null);
		g.setFont(new Font("Rockwell", Font.BOLD, 52));
		g.drawString(Main.update.area.getName(), 350, 720);
		Main.update.splashScreenTime--;
	}
	
	/**
	 * Draws the HUD (Heads up Display) which draws skills, menu accessors, and cooldowns
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawHUD(Graphics2D g) {
		g.drawImage(HPUI, GraphicsMain.WIDTH - 186, 25, 186, 46, null);
		//HP and EXP Bars. Set to represent a % of total HP or EXP until next level
		double hp = ((((double)Update.PC.getHealth() / (double)Update.PC.getMaxHealth()) * 174));
		double exp = ((((double)Update.PC.getEXP() / (double)Update.PC.getReqLvl()) * 98));
		Rectangle2D HP = new Rectangle2D.Double(GraphicsMain.WIDTH-176+(174-hp), 35, hp, 10);
		Rectangle2D EXP = new Rectangle2D.Double(GraphicsMain.WIDTH-100+(98-exp), 55, exp, 6);
		g.setColor(Color.red);
		g.fill(HP);
		g.draw(HP);
		g.setColor(Color.yellow);
		g.fill(EXP);
		g.draw(EXP);
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.setColor(Color.white);
		g.drawImage(skillsUI, 3, 25, 203, 50, null);
		//Cooldowns of abilities
		g.drawString(""+Main.update.qCD, 7, 70);
		g.drawString(""+Main.update.wCD, 38, 70);
		g.drawString(""+Main.update.eCD, 68, 70);
		Line2D grapple = Main.update.grapple;
		//Draws the grapple onto the screen, if it exists
		if(grapple != null){
			g.draw(grapple);
			if(grapple.getY2() <= Update.PC.getY())
				g.drawImage(hook, (int)grapple.getX2(), (int)grapple.getY2()-20, 30, 30, null);
			else
				g.drawImage(hook2, (int)grapple.getX2()-20, (int)grapple.getY2()-10, 30, 30, null);
		}
		//Draws the darts in motion onto the screen, if they exist
		for(int i = 0; i < Main.update.darts.size(); i++){
			Main.update.darts.get(i).draw(g);
		}
		if(Main.update.healingTime > 0){
			g.drawImage(meditateAura, Update.PC.getX()-2, Update.PC.getY()+20, Update.PC.getWidth()+10, Update.PC.getHeight(), null);
			Main.update.healingTime--;
		}
	}

	/**
	 * Draws the portal if Main.update.area contains a Portal object
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawPortal(Graphics2D g) {
		if(Main.update.area.hasPortal()){
			Main.update.area.getPortal().draw(g);
			if(Main.update.portalScreen && Main.update.portalOnline){
				Main.update.area.getPortal().drawTeleport(g);
			}
		}
	}

	/**
	 * Draws the User Interfaces, including inventory, quests, and associated items/quests
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawUIs(Graphics2D g) {
		//Draws the inventory pane onto the menu, including all stats, items, and information
		if(Main.update.invScreen){
			g.drawImage(inventory, 0, 30, 1024, 700, null);
			g.setFont(new Font("Rockwell", Font.BOLD, 22));
			//Drawing Gold count
			g.setColor(Color.yellow);
			g.drawString("" + Update.PC.getGold(), 250, 670);
			//Drawing Attack count
			g.setColor(Color.black);
			g.drawString("" + Update.PC.getDamage(), 90, 206);
			//Drawing Armor count
			g.drawString("" + Update.PC.getDefense(), 90, 253);
			//Draws the HP of the player character (PC) and Location
			g.setFont(new Font("Rockwell", Font.BOLD, 18));
			g.setColor(Color.red);
			g.drawString("HP               " + Update.PC.getHealth() + "/" + Update.PC.getMaxHealth(), 70, 80);
			g.setColor(Color.black);
			g.drawString("EXP:            " + Update.PC.getEXP() + "/" + Update.PC.getReqLvl() + " to level", 70, 100);
			g.drawString("Location     " + Main.update.area.getName(), 70, 120);
			g.setColor(Color.blue);
			g.setFont(new Font("Rockwell", Font.BOLD, 25));
			g.drawString("Level " + Update.PC.getLevel(), 235, 200);
			g.setColor(Color.black);
			g.setFont(new Font("Rockwell", Font.BOLD, 18));
			//Time Elapsed counter
			int timeElapsed = (int)(Main.update.currentTime / 1000);
			String time = "";
			if(timeElapsed < 10){
				time = "00:0" + timeElapsed;
			}
			else if(timeElapsed < 60){
				time = "00:" + timeElapsed;
			}
			else if(timeElapsed >= 60){
				int mins = (int)(timeElapsed / 60);
				int secs = timeElapsed - (mins)*60;
				if(mins < 10){
					time = "0";
				}
				if(secs < 10){
					time += mins + ":0" + secs;
				}
				else
					time += mins + ":" + secs;
			}
			g.drawString("Playtime     " + time, 70, 140);
			//Draws the equipped weapon
			g.drawImage(Update.PC.getWeapon().getImage(), 252, 257, 91, 91, null);
			//Draws the equipped armor
			g.drawImage(Update.PC.getArmor().getImage(), 252, 421, 91, 91, null);
			//Draws all the items in inventory
			g.drawString("Items -", 400, 80);
			for(int i = 0; i < Update.PC.invItems.size(); i++){
				if(i < 9)
					g.drawImage(Update.PC.invItems.get(i).getImage(), 400+(65 * i), 90, 50, 50, null);
			}
			//Draws all the swords in inventory
			g.drawString("Weapons -", 400, 210);
			for(int i = 0; i < Update.PC.invSwords.size(); i++){
				if(i < 9)
					g.drawImage(Update.PC.invSwords.get(i).getImage(), 400+(65 * i), 220, 50, 50, null);
			}
			//Draws all the armor in inventory
			g.drawString("Armor -", 400, 340);
			for(int i = 0; i < Update.PC.invArmor.size(); i++){
				if(i < 9)
					g.drawImage(Update.PC.invArmor.get(i).getImage(), 400+(65 * i), 350, 50, 50, null);
			}
			//Draws all the Quest Items in inventory
			g.drawString("Quest -", 400, 470);
			for(int i = 0; i < Update.PC.qItems.size(); i++){
				if(i < 9)
					g.drawImage(Update.PC.qItems.get(i).getImage(), 400+(65 * i), 480, 50, 50, null);
			}
			if(Main.update.drawInvIndx >= 0 && Main.update.drawWhich > 0){
				//Drawing descriptions for Inventory Items objects
				if(Main.update.drawWhich == 1 && Main.update.drawInvIndx >= 0){
					if(!Update.PC.invItems.isEmpty() && Main.update.drawInvIndx >= 0 && Main.update.drawInvIndx < Update.PC.invItems.size()){
						g.setFont(new Font("Rockwell", Font.BOLD, 20));
						g.drawString(Update.PC.invItems.get(Main.update.drawInvIndx).getID(), 400, 590);
						g.setFont(new Font("Rockwell", Font.PLAIN, 13));
						g.drawString(Update.PC.invItems.get(Main.update.drawInvIndx).getInfo(), 400, 630);
						g.setFont(new Font("Rockwell", Font.PLAIN, 12));
						g.drawString(Update.PC.invItems.get(Main.update.drawInvIndx).getDescription(), 430, 670);
					}
				}
				//Drawing descriptions for Inventory Swords objects
				else if (Main.update.drawWhich == 2){
					if(!Update.PC.invSwords.isEmpty() && Main.update.drawInvIndx >= 0){
						g.setFont(new Font("Rockwell", Font.BOLD, 20));
						g.drawString(Update.PC.invSwords.get(Main.update.drawInvIndx).getID(), 400, 590);
						g.setFont(new Font("Rockwell", Font.PLAIN, 13));
						g.drawString(Update.PC.invSwords.get(Main.update.drawInvIndx).getInfo(), 400, 630);
						g.setFont(new Font("Rockwell", Font.PLAIN, 12));
						g.drawString(Update.PC.invSwords.get(Main.update.drawInvIndx).getDescription(), 430, 670);
					}
				}
				//Drawing descriptions for Inventory Armor objects
				else if(Main.update.drawWhich == 3){
					if(!Update.PC.invArmor.isEmpty() && Main.update.drawInvIndx >= 0){
						g.setFont(new Font("Rockwell", Font.BOLD, 20));
						g.drawString(Update.PC.invArmor.get(Main.update.drawInvIndx).getID(), 400, 590);
						g.setFont(new Font("Rockwell", Font.PLAIN, 13));
						g.drawString(Update.PC.invArmor.get(Main.update.drawInvIndx).getInfo(), 400, 630);
						g.setFont(new Font("Rockwell", Font.PLAIN, 12));
						g.drawString(Update.PC.invArmor.get(Main.update.drawInvIndx).getDescription(), 430, 670);
					}
				}
				//Drawing descriptions for Inventory Quest Item objects
				else if(Main.update.drawWhich == 4){
					if(!Update.PC.qItems.isEmpty() && Main.update.drawInvIndx >= 0){
						g.setFont(new Font("Rockwell", Font.BOLD, 20));
						g.drawString(Update.PC.qItems.get(Main.update.drawInvIndx).getID(), 400, 590);
						g.setFont(new Font("Rockwell", Font.PLAIN, 13));
						g.drawString(Update.PC.qItems.get(Main.update.drawInvIndx).getInfo(), 400, 630);
						g.setFont(new Font("Rockwell", Font.PLAIN, 12));
						g.drawString(Update.PC.qItems.get(Main.update.drawInvIndx).getDescription(), 430, 670);
					}
				}
			}
		}
		//Draws the quest pane onto the Screen, including all Quest objects and descriptions
		if(Main.update.questScreen){
			g.setColor(Color.black);
			g.drawImage(questScreen, 0, 30, 1024, 700, null);
			if(Main.update.quests != null){
				for(int i = 0; i < Main.update.quests.size(); i++){
					if(i < 6){
						g.setFont(new Font("Rockwell", Font.BOLD, 20));
						g.drawString(Main.update.quests.get(i).getName(), 70, 80*i + 160);
						g.setFont(new Font("Rockwell", Font.BOLD, 14));
						g.drawString(Main.update.quests.get(i).getDesc(), 100, 80*i + 180);
						g.drawString(Main.update.quests.get(i).getTask(), 125, 80*i + 200);
					}
					else{
						g.setFont(new Font("Rockwell", Font.BOLD, 20));
						g.drawString("More quests to display. Finish some first!", 70, 660);
					}
				}
			}
		}
	}

	/**
	 * Draws the dialogue including dialogue box onto the screen
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawDialogue(Graphics2D g){
		if(Main.update.commenceDialogue > 0){
			g.drawImage(dialogueBox, 0, GraphicsMain.HEIGHT - 200, 1024, 200, null);
			NPC speak = Main.update.speakingWith;
			g.drawImage(speak.getHead(), 65, GraphicsMain.HEIGHT - 139, 114, 114, null);
			g.setFont(new Font("Rockwell", Font.BOLD, 16));
			g.drawString(speak.getName(), 135, GraphicsMain.HEIGHT - 158);
			g.setColor(Color.black);
			//If on the last dialogue, open up whatever special thing the NPC does (Shop, Quest, etc.)
			if(Main.update.commenceDialogue >= 1){
				g.setFont(new Font("Arial", Font.PLAIN, 13));
				g.drawString(speak.getPhrase(Main.update.commenceDialogue - 1), 200, GraphicsMain.HEIGHT - 100);
				if(Main.update.nextDialogue){
					Main.update.commenceDialogue--;
					Main.update.nextDialogue = false;
				}
			}
			if(Main.update.commenceDialogue == 1 && (speak.getID() == "shop" || speak.getID() == "blacksmith" || speak.getID() == "armorsmith")){
				drawShop(g);
			}
			if(Main.update.commenceDialogue == 1 && (speak.getID() == "skiller")){
				drawSKT(g);
			}
			if(Main.update.commenceDialogue == 1 && (speak.getID() == "stranger")){
				Item soulGem = new Item(0, 0, "Soul Gem");
				if(!Main.update.gem){
					Update.PC.addQuestItem(soulGem);
					Main.update.gem = true;
					Main.update.speakingWith.updateLines();
				}
			}
			if(Main.update.commenceDialogue == 1 && (speak.getID() == "yenfay")){
				if(!Main.update.fixingPortal){
					Main.update.fixingPortal = true;
					Main.update.quests.add(new Quest("Fixing the Portal"));
				}
				if(Main.update.gem){
					for(int i = 0; i < Update.PC.qItems.size(); i++){
						if(Update.PC.qItems.get(i).getID() == "Soul Gem"){
							Update.PC.qItems.remove(i);
						}
					}
					Main.update.portalOnline = true;
					Main.update.speakingWith.updateLines();
				}
			}
			if(Main.update.commenceDialogue == 1 && (speak.getID() == "guard")){
				if(!Main.update.priamTask){
					Main.update.priamTask = true;
					Main.update.quests.add(new Quest("Priam's Task"));
					Main.update.priamIndex = Main.update.quests.size()-1;
				}
				if(Main.update.priamDone){
					Main.update.quests.get(Main.update.priamIndex).claimReward();
					Main.update.priamDone = false;
				}
			}
		}
	}
	
	/**
	 * Draws the Shop onto the screen including things for purchase from Shopkeep, Armorsmith, and Weaponsmith
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawShop(Graphics2D g){
		g.drawImage(shop,  100, 28, 845, 550, null);
		Main.update.shopping = true;
		LinkedList<Sword> swordShop = Main.update.shopSwords;
		LinkedList<Item> itemShop = Main.update.shopItems;
		LinkedList<Armor> armorShop = Main.update.shopArmor;
		if(Main.update.speakingWith.getID() == "blacksmith"){
			//Draws all Swords from Blacksmith's inventory.
			for(int i = 0; i < swordShop.size(); i++){
				g.drawImage(swordShop.get(i).getImage(), swordShop.get(i).getX(), swordShop.get(i).getY(), 64, 64, null);
				if(Main.update.drawInfo){
					g.setColor(Color.white);
					//Price
					g.setFont(new Font("Rockwell", Font.BOLD, 20));
					g.drawString(swordShop.get(Main.update.drawInfoIndx).getID() + "     Price: " + swordShop.get(Main.update.drawInfoIndx).getValue(),swordShop.get(Main.update.drawInfoIndx).getX() + 150, swordShop.get(Main.update.drawInfoIndx).getY() + 20);
					//Info
					g.setFont(new Font("Georgia", Font.PLAIN, 18));
					g.drawString(swordShop.get(Main.update.drawInfoIndx).getInfo(), swordShop.get(Main.update.drawInfoIndx).getX() + 150, swordShop.get(Main.update.drawInfoIndx).getY() + 45);
					//Flavor Text
					g.setFont(new Font("Georgia", Font.PLAIN, 14));
					g.drawString(swordShop.get(Main.update.drawInfoIndx).getDescription(), swordShop.get(Main.update.drawInfoIndx).getX() + 150, swordShop.get(Main.update.drawInfoIndx).getY() + 65);
				}
			}
		}
		else if(Main.update.speakingWith.getID() == "shop"){
			//Draws all Items from Shopkeep's inventory.
			for(int i = 0; i < itemShop.size(); i++){
				g.drawImage(itemShop.get(i).getImage(), itemShop.get(i).getX(), itemShop.get(i).getY(), 64, 64, null);
				if(Main.update.drawInfo){
					g.setColor(Color.white);
					//Price
					g.setFont(new Font("Rockwell", Font.BOLD, 20));
					g.drawString(itemShop.get(Main.update.drawInfoIndx).getID() + "     Price: " + itemShop.get(Main.update.drawInfoIndx).getValue(),itemShop.get(Main.update.drawInfoIndx).getX() + 150, itemShop.get(Main.update.drawInfoIndx).getY() + 25);
					//Info
					g.setFont(new Font("Georgia", Font.PLAIN, 18));
					g.drawString(itemShop.get(Main.update.drawInfoIndx).getInfo(), itemShop.get(Main.update.drawInfoIndx).getX() + 150, itemShop.get(Main.update.drawInfoIndx).getY() + 45);
					//Flavor Text
					g.setFont(new Font("Georgia", Font.PLAIN, 14));
					g.drawString(itemShop.get(Main.update.drawInfoIndx).getDescription(), itemShop.get(Main.update.drawInfoIndx).getX() + 150, itemShop.get(Main.update.drawInfoIndx).getY() + 65);
				}
			}
		}
		else if(Main.update.speakingWith.getID() == "armorsmith"){
			//Draws all Armor from Armorsmith's inventory.
			for(int i = 0; i < armorShop.size(); i++){
				g.drawImage(armorShop.get(i).getImage(), armorShop.get(i).getX(), armorShop.get(i).getY(), 64, 64, null);
				if(Main.update.drawInfo){
					g.setColor(Color.white);
					//Price
					g.setFont(new Font("Rockwell", Font.BOLD, 20));
					g.drawString(armorShop.get(Main.update.drawInfoIndx).getID() + "     Price: " + armorShop.get(Main.update.drawInfoIndx).getValue(),armorShop.get(Main.update.drawInfoIndx).getX() + 150, armorShop.get(Main.update.drawInfoIndx).getY() + 25);
					//Info
					g.setFont(new Font("Georgia", Font.PLAIN, 18));
					g.drawString(armorShop.get(Main.update.drawInfoIndx).getInfo(), armorShop.get(Main.update.drawInfoIndx).getX() + 150, armorShop.get(Main.update.drawInfoIndx).getY() + 45);
					//Flavor Text
					g.setFont(new Font("Georgia", Font.PLAIN, 14));
					g.drawString(armorShop.get(Main.update.drawInfoIndx).getDescription(), armorShop.get(Main.update.drawInfoIndx).getX() + 150, armorShop.get(Main.update.drawInfoIndx).getY() + 65);
				}
			}
		}
		//Error message - Item too expensive
		if(Main.update.insufficientGold > 0){
			g.setColor(Color.red);
			g.drawString("Insufficient funds for item.",  420, 60);
			Main.update.insufficientGold--;
			if(Main.update.purchased > Main.update.insufficientGold){
				Main.update.purchased = 0;
			}
		}
		//Error message - User already has item in inventory
		if(Main.update.alreadyHave > 0){
			g.setColor(Color.red);
			g.drawString("You already have this item.",  420, 60);
			Main.update.alreadyHave--;
			if(Main.update.purchased > Main.update.alreadyHave){
				Main.update.purchased = 0;
			}
		}
		//Message - Item purchased
		else if(Main.update.purchased > 0 && Main.update.insufficientGold == 0){
			g.setColor(Color.green);
			g.drawString("Item Purchased!", 420, 60);
			Main.update.purchased--;
		}
		g.setFont(new Font("Rockwell", Font.BOLD, 16));
		g.setColor(Color.yellow);
		g.drawString("Gold: " + Update.PC.getGold(), GraphicsMain.WIDTH - 260, 60);
		//Item description
	}
	
	/**
	 * Draws the debugging boundaries for Collision for NPCs, Enemies, LeaveAreas, CollisionAreas, and Main.update.PC
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawBounds(Graphics2D g){
		Rectangle2D PlayerCharacter = Update.PC.getBoundbox();
		g.setColor(Color.blue);
		g.draw(PlayerCharacter);
		g.setColor(Color.white);
		Rectangle2D nb = Main.update.nb;
		g.draw(nb);
		for(int i = 0; i < Main.update.NPCs.size(); i++){
			g.draw(Main.update.NPCs.get(i).getSmall());
		}
		for(int i = 0; i < Main.update.area.getLeaveAreas().size(); i++){
			g.draw(Main.update.area.getLeaveAreas().get(i));
		}
		for(int i = 0; i < Main.update.enemies.size(); i++){
			g.draw(Main.update.enemies.get(i).getSmall());
		}
		for(int i = 0; i < Main.update.area.getCollisionRects().size(); i++){
			g.draw(Main.update.area.getCollisionRects().get(i));
		}
		if(Main.update.attackBox != null)
			g.draw(Main.update.attackBox);
		for(int i = 0; i < Main.update.darts.size(); i++){
			g.draw(Main.update.darts.get(i).getBoundbox());
		}
	}
	
	/**
	 * Draws the prompts and notifications (e.g. Chat, LevelUP)
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawPrompts(Graphics2D g){
		if(Main.update.dialogueOptions > 0){
			g.setFont(new Font("Georgia", Font.PLAIN, 18));
			g.setColor(Color.black);
			g.drawImage(interactBox, 413, 90, 200, 75, null);
			g.drawString("Press R to Interact", 440, 130);
			Main.update.dialogueOptions--;
		}
		if(Main.update.levelUp > 0){
			g.drawImage(levelUp, Update.PC.getX()-20, Update.PC.getY()-30 -((60-Main.update.levelUp)/4), null);
			Main.update.levelUp--;
		}
	}
	
	/**
	 * Draws the NPCs in Main.update.area to the screen
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawNPC(Graphics2D g) {
		LinkedList<NPC> NPCs = Main.update.NPCs;
		for(int i = 0; i < NPCs.size(); i++) {
			NPC e = NPCs.get(i);
			e.draw(g);
			if(e.hasDialogue()){
				g.drawImage(talkBubble, (e.getX()), (e.getY() - 4), 18, 14, null);
			}
		}
	}

	/**
	 * Draws the background as well as bonus items onto the screen.
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawBackground(Graphics2D g){
		Main.update.area.draw(g);
		if(Main.update.area.getID() == "Turandal3" && Main.update.fixingPortal){
			if(Main.update.SWTCH == false){
				g.drawImage(switchOff, (int)Main.update.magicSwitch.getX(), (int)Main.update.magicSwitch.getY(), 23, 18, null);
			}
			else
				g.drawImage(switchOn, (int)Main.update.magicSwitch.getX(), (int)Main.update.magicSwitch.getY(), 23, 18, null);
		}
		if(Main.update.area.getID() == "Frostgorge5" && !Main.update.fbChest){
			g.drawImage(chest, (int)Main.update.treasureChest.getX(), (int)Main.update.treasureChest.getY(), 38, 32, null);
		}
	}
	
	/**
	 * Draws the Player Character from Main.update.PC onto the screen
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawPlayer(Graphics2D g){
		Update.PC.draw(g);
	}
	
	/**
	 * Draws the Skill Tree onto the screen
	 * @param g Graphics2D Object to draw with.
	 */
	private void drawSKT(Graphics2D g){
		Update.PC.getSkillTree().draw(g);
	}
}
