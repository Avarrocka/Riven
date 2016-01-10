package res;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import javax.imageio.ImageIO;
import listeners.KeyboardListener;
import main.GraphicsMain;
import main.Main;
import res.Sword;
import res.Item;

/**
 * Class defines an Player object
 * @author Brian Chen
 *
 */
public class Player implements Drawable{
	private int x, y, hp, mhp;
	private int Vx, Vy;
	private int EXP;
	private int gold;
	private int skillPoints = 1;
	private Sword weapon;
	private Armor armor;
	private SkillTree skt = new SkillTree();
	public volatile LinkedList<Sword> invSwords = new LinkedList<Sword>(); 
	public volatile LinkedList<Item> invItems = new LinkedList<Item>(); 
	public volatile LinkedList<Item> qItems = new LinkedList<Item>();
	public volatile LinkedList<Armor> invArmor = new LinkedList<Armor>();
	private int motionSpeed = 60;
	private int attackSpeed = 40;
	private int reqLvl = 12;
	private int level;
	private int face;
	private int dir;
	private boolean r = false;
	private int invin = 0;
	BufferedImage Left[] = new BufferedImage[4];
	BufferedImage Right[] = new BufferedImage[4];
	BufferedImage Up[] = new BufferedImage[4];
	BufferedImage Down[] = new BufferedImage[4];
	BufferedImage LAttack[] = new BufferedImage[4];
	BufferedImage RAttack[] = new BufferedImage[4];
	BufferedImage DAttack[] = new BufferedImage[4];
	BufferedImage UAttack[] = new BufferedImage[4];
	public boolean q1, q2, q3, w1, w2, w3, e1, e2, e3;
 	private boolean revMov = false;
	private boolean hpBuff = false;
	private int baseAttack, baseDefense, damaged, scroll=30, scroll2=60;
	private static final int WIDTH = 56, HEIGHT = 64;
	private static final int DEFAULT = 0, UP = 1, DOWN = 2, RIGHT = 4, LEFT = 3;
	private BufferedImage image, hitSplat, lootScreen;
	private Rectangle2D boundBox;
	private BufferedImage defL, defR;
	private Object o;
	private BufferedImage drop;
	private String dropName;
	public boolean oozeQuest;
	private Random RNG = new Random();
	
	/**
	 * Creates Player class at X and Y co-ordinates
	 * @param x
	 * @param y
	 */
	public Player(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setXvelocity(0);
		this.setYvelocity(0);
		this.setGold(150);
		this.mhp = 100;
		this.setHealth(mhp);
		this.baseAttack = 2;
		this.baseDefense = 2;
		this.EXP = 0;
		this.level = 1;
		this.setWeapon(new Sword(0, 0, "Rusted Sword"), -1);
		this.setArmor(new Armor(0, 0, "Leather Armor"), -1);
		//Loads all associated sprites and images of Player
		try {
			defL = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CDL.png"));
			defR = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CDR.png"));
			lootScreen = ImageIO.read(getClass().getClassLoader().getResource("UI/lootScreen.png"));
			hitSplat = ImageIO.read(getClass().getClassLoader().getResource("UI/hitSplat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Movement animation images
		try {
			Left[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CL1.png"));
			Left[1] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CL2.png"));
			Left[2] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CL3.png"));
			Left[3] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CL4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Right[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CR1.png"));
			Right[1] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CR2.png"));
			Right[2] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CR3.png"));
			Right[3] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CR4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Up[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CU1.png"));
			Up[1] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CU2.png"));
			Up[2] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CU3.png"));
			Up[3] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CU4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Down[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CD1.png"));
			Down[1] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CD2.png"));
			Down[2] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CD3.png"));
			Down[3] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CD4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Attack animation images
		try {
			RAttack[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/RA1.png"));
			RAttack[1] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/RA2.png"));
			RAttack[2] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/RA3.png"));
			RAttack[3] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/RA4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			LAttack[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/LA1.png"));
			LAttack[1] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/LA2.png"));
			LAttack[2] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/LA3.png"));
			LAttack[3] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/LA4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			DAttack[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/DA1.png"));
			DAttack[1] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/DA2.png"));
			DAttack[2] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/DA3.png"));
			DAttack[3] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/DA4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			UAttack[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/UA1.png"));
			UAttack[1] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/UA2.png"));
			UAttack[2] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/UA3.png"));
			UAttack[3] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/UA4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setImage(0);
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
	}

	/**
	 * Returns X location value of Player
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets X location value of Player
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns Y location value of Player
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets Y location value of Player
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Returns X velocity
	 * @return
	 */
	public int getXvelocity() {
		return this.Vx;
	}
	
	/**
	 * Sets X velocity
	 * @param Vx
	 */
	public void setXvelocity(int Vx) {
		this.Vx = Vx;
	}
	
	/**
	 * Returns Y velocity
	 * @return
	 */
	public int getYvelocity() {
		return this.Vy;
	}
	
	/**
	 * Sets Y velocity
	 * @param Vy
	 */
	public void setYvelocity(int Vy) {
		this.Vy = Vy;
	}

	/**
	 * Returns Player's current Health
	 * @return
	 */
	public int getHealth() {
		return hp;
	}

	/**
	 * Sets Player's current Health
	 * @param hp
	 */
	public void setHealth(int hp) {
		this.hp = hp;
	}
	
	/** 
	 * Return's Player's Maximum Health
	 */
	public int getMaxHealth() {
		return mhp;
	}
	
	/**
	 * Draws Player to the screen, with any information necessary, using Render
	 * @param g
	 */
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
		drawDamage(g);
		drawDrop(g);
	}
	
	/**
	 * Draws the sustained damage that PC just took
	 * @param g
	 */
	public void drawDamage(Graphics2D g){
		//Integer time remaining to draw Damage
		if(this.damaged > 0){
			// Scrolls damage Hit Splat upwards
			if(this.scroll > 0){
				g.setFont(new Font("Arial", Font.BOLD, 12));
				g.setColor(Color.white);
				g.drawImage(hitSplat, this.x+1, this.y+(20)-(40-this.scroll)/2, null);
				g.drawString(""+this.damaged, this.x+13, this.y+(35)-((40-this.scroll)/2));
				this.scroll--;
			}
			else
				this.damaged = 0;
		}
	}
	
	/**
	 * Draws a Item Obtained notification for the Player
	 * @param g
	 */
	public void drawDrop(Graphics2D g){
		//Checks if object exists
		if(o != null){
			//Scrolls message upwards
			if(this.scroll2 > 0){
				g.setFont(new Font("Arial", Font.BOLD, 14));
				g.setColor(Color.white);
				g.drawImage(lootScreen, 3, 100+(20)-(40-this.scroll2/2), 150, 110, null);
				g.drawImage(drop, 44, 130+(25)-(40-this.scroll2/2), 64, 64, null);
				g.drawString("ITEM OBTAINED:", 25, 134-((40-this.scroll2/2)));
				g.setFont(new Font("Arial", Font.BOLD, 10));
				g.drawString(dropName, 12, 150-((40-this.scroll2/2)));
				this.scroll2--;
			}
			else
				o = null;
		}
	}
	
	/**
	 * Returns WIDTH of Player
	 * @return
	 */
	public int getWidth() {
		return WIDTH;
	}

	/**
	 * Returns HEIGHT of Player
	 * @return
	 */
	public int getHeight() {
	 return HEIGHT;
	}
	
	/**
	 * Returns the Gold of Player
	 * @return
	 */
	public int getGold(){
		return this.gold;
	}
	
	/**
	 * Sets the Gold of Player
	 * @param gold
	 */
	public void setGold(int gold){
		this.gold = gold;
	}
	
	/**
	 * Returns the way Player is facing
	 * @return
	 */
	public int getFace(){
		return this.face;
	}
	
	/**
	 * Updates where the Player is facing
	 */
	public void updateDir(){
		if(this.Vx > 0){
			dir = 1;
		}
		if(this.Vx < 0){
			dir = 0;
		}
		else if(this.Vy > 0){
			dir = 3;
		}
		else if(this.Vy < 0){
			dir = 2;
		}
	}
	
	/**
	 * Updates what direction the player is in
	 * @return
	 */
	public int getDir(){
		return dir;
	}
	
	/**
	 * Returns the current Sword Player is using
	 * @return
	 */
	public Sword getWeapon(){
		return this.weapon;
	}
	
	/**
	 * Sets the player's current Sword to new Sword
	 * @param weapon
	 * @param index
	 */
	public void setWeapon(Sword weapon, int index){
		if(index >= 0){
			//Removes sword from inventory, adds current sword to inventory
			this.invSwords.remove(index);
			this.invSwords.add(index, this.getWeapon());
		}
		//Uses new sword
		this.weapon = weapon;
	}
	
	/**
	 * Returns the current Armor Player is using
	 * @return
	 */
	public Armor getArmor(){
		return this.armor;
	}
	
	/**
	 * Sets the current Armor to new Armor
	 * @param armor
	 * @param index
	 */
	public void setArmor(Armor armor, int index){
		if(index >= 0){
			this.invArmor.remove(index);
			this.invArmor.add(index, this.getArmor());
		}
		//Checks special effects of Darksteel Armor (HP Boon)
		if(armor.getID() == "Darksteel Armor" && !hpBuff){
			mhp = mhp+20;
			hpBuff = true;
		}
		else if(hpBuff && armor.getID() != "Darksteel Armor"){
			mhp = mhp-20;
			hpBuff = false;
		}
		this.armor = armor;
	}
	
	/**
	 * Adds an Item to the Items slot of Player
	 * @param item
	 */
	public void addItem(Item item){
		invItems.add(item);
		scroll2 = 60;
		o = item;
		drop = item.getImage();
		dropName = item.getID();
	}
	
	/**
	 * Adds an Armor object to the Armor slot of Player
	 * @param armor
	 */
	public void addItem(Armor armor){
		invArmor.add(armor);
		scroll2 = 60;
		o = armor;
		drop = armor.getImage();
		dropName = armor.getID();
	}
	
	/**
	 * Adds a Sword object to the Sword slot of Player
	 * @param sword
	 */
	public void addItem(Sword sword){
		invSwords.add(sword);
		scroll2 = 60;
		o = sword;
		drop = sword.getImage();
		dropName = sword.getID();
	}
	
	/**
	 * Adds an Item to the Quest Items slot of Player
	 * @param item
	 */
	public void addQuestItem(Item item){
		qItems.add(item);
		scroll2 = 60;
		o = item;
		drop = item.getImage();
		dropName = item.getID();
	}
	
	/**
	 * Returns the current sprite image of Player
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * Sets the sprite of Player depending on how far along they are in their movement sequence as well as facing direction
	 * @param face
	 */
	public void setImage(int face){
		if(motionSpeed == 0){
			motionSpeed = 60;
			revMov = !revMov;
		}
		if(face == RIGHT){
			r = true;
		}
		if(face == LEFT){
			r = false;
		}
		if(face != DEFAULT)
			this.face = face;
		if(!revMov){
			if(face == DEFAULT){
				if(r)
					this.image = defR;
				else
					this.image = defL;
			}
			else if(face == UP){
				if(60 >= motionSpeed && 45 < motionSpeed){
					this.image = Up[0];
				}
				else if(45 >= motionSpeed && 30 < motionSpeed){
					this.image = Up[1];
				}
				else if(30 >= motionSpeed && 15 < motionSpeed){
					this.image = Up[2];
				}
				else if(15 >= motionSpeed && 0 < motionSpeed){
					this.image = Up[3];
				}
			}
			else if(face == DOWN){
				if(60 >= motionSpeed && 45 < motionSpeed){
					this.image = Down[0];
				}
				else if(45 >= motionSpeed && 30 < motionSpeed){
					this.image = Down[1];
				}
				else if(30 >= motionSpeed && 15 < motionSpeed){
					this.image = Down[2];
				}
				else if(15 >= motionSpeed && 0 < motionSpeed){
					this.image = Down[3];
				}
			}
			else if(face == RIGHT){
				if(60 >= motionSpeed && 45 < motionSpeed){
					this.image = Right[0];
				}
				else if(45 >= motionSpeed && 30 < motionSpeed){
					this.image = Right[1];
				}
				else if(30 >= motionSpeed && 15 < motionSpeed){
					this.image = Right[2];
				}
				else if(15 >= motionSpeed && 0 < motionSpeed){
					this.image = Right[3];
				}
			}
			else if(face == LEFT){
				if(60 >= motionSpeed && 45 < motionSpeed){
					this.image = Left[0];
				}
				else if(45 >= motionSpeed && 30 < motionSpeed){
					this.image = Left[1];
				}
				else if(30 >= motionSpeed && 15 < motionSpeed){
					this.image = Left[2];
				}
				else if(15 >= motionSpeed && 0 < motionSpeed){
					this.image = Left[3];
				}
			}
		}
		else if(revMov){
			if(face == DEFAULT){
				if(r)
					this.image = defR;
				else
					this.image = defL;
			}
			else if(face == UP){
				if(60 >= motionSpeed && 45 < motionSpeed){
					this.image = Up[3];
				}
				else if(45 >= motionSpeed && 30 < motionSpeed){
					this.image = Up[2];
				}
				else if(30 >= motionSpeed && 15 < motionSpeed){
					this.image = Up[1];
				}
				else if(15 >= motionSpeed && 0 < motionSpeed){
					this.image = Up[0];
				}
			}
			else if(face == DOWN){
				if(60 >= motionSpeed && 45 < motionSpeed){
					this.image = Down[3];
				}
				else if(45 >= motionSpeed && 30 < motionSpeed){
					this.image = Down[2];
				}
				else if(30 >= motionSpeed && 15 < motionSpeed){
					this.image = Down[1];
				}
				else if(15 >= motionSpeed && 0 < motionSpeed){
					this.image = Down[0];
				}
			}
			else if(face == RIGHT){
				if(60 >= motionSpeed && 45 < motionSpeed){
					this.image = Right[3];
				}
				else if(45 >= motionSpeed && 30 < motionSpeed){
					this.image = Right[2];
				}
				else if(30 >= motionSpeed && 15 < motionSpeed){
					this.image = Right[1];
				}
				else if(15 >= motionSpeed && 0 < motionSpeed){
					this.image = Right[0];
				}
			}
			else if(face == LEFT){
				if(60 >= motionSpeed && 45 < motionSpeed){
					this.image = Left[3];
				}
				else if(45 >= motionSpeed && 30 < motionSpeed){
					this.image = Left[2];
				}
				else if(30 >= motionSpeed && 15 < motionSpeed){
					this.image = Left[1];
				}
				else if(15 >= motionSpeed && 0 < motionSpeed){
					this.image = Left[0];
				}
			}
		}
		motionSpeed--;
	}
	
	/**
	 * Returns the boundbox for Player
	 * @return
	 */
	public Rectangle2D getBoundbox(){
		return this.boundBox;
	}
	
	/**
	 * Updates the boundbox for Player
	 */
	public void updateBoundbox(){
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		if(attackSpeed > 0){
			this.attack();
		}
		else if(attackSpeed == 0){
			if(r)
				this.image = defR;
			else
				this.image = defL;
			attackSpeed = -1;
		}
	}
	
	/**
	 * Returns current EXP of Player
	 * @return
	 */
	public int getEXP(){
		return this.EXP;
	}
	
	/**
	 * Sets more EXP for player (additive to current EXP)
	 * @param EXP
	 */
	public void setEXP(int EXP){
		int exp = EXP;
		this.EXP += exp;
		if(this.EXP >= reqLvl){
			this.levelUp();
		}
	}
	
	/**
	 * Returns the current level of Player
	 * @return
	 */
	public int getLevel(){
		return this.level;
	}
	
	/**
	 * Levels player up by One level, increasing base stats and ramping up level curve
	 */
	private void levelUp() {
		this.EXP = this.EXP - reqLvl;
		this.updateLevelCurve();
		level++;
		skillPoints++;
		Main.update.levelUp = 60;
		this.baseAttack = 2*level;
		this.baseDefense = 2*level;
		this.mhp += 15;
		if(this.EXP >= reqLvl){
			levelUp();
		}
	}
	
	/**
	 * Returns EXP required for next level
	 * @return
	 */
	public int getReqLvl(){
		return this.reqLvl;
	}
	
	/**
	 * Updates EXP required for next level per level, incrementally higher by 1.5x
	 */
	private void updateLevelCurve(){
		reqLvl = ((int) (reqLvl*1.5));
	}
	
	/**
	 * Heals the Player for an amount of Health
	 * @param heal
	 */
	public void heal(int heal){
		if(this.hp + heal <= mhp)
			this.hp = this.hp+heal;
		else
			this.hp = mhp;	
	}
	
	/**
	 * Uses an item. Effect depends on item type
	 * @param item
	 * @param i
	 */
	public void activateItem(Item item, int i) {
		invItems.remove(i);
		Item using = item;
		//Basic healing items
		if(using.getID() == "Fish Steak"){
			if(this.getHealth() + using.getHeal() <= mhp)
				this.setHealth(this.getHealth() + using.getHeal());
			else{
				this.setHealth(mhp);
			}
		}
		else if(using.getID() == "Chocolate Raspberry Cake"){
			if(this.getHealth() + using.getHeal() <= mhp)
				this.setHealth(this.getHealth() + using.getHeal());
			else{
				this.setHealth(mhp);
			}
		}
		else if(using.getID() == "Cinnamon Pumpkin Pie"){
			if(this.getHealth() + using.getHeal() <= mhp)
				this.setHealth(this.getHealth() + using.getHeal());
			else{
				this.setHealth(mhp);
			}
		}
		//Full heal, Cooldown inducing item
		else if(using.getID() == "Healing Salve"){
			Main.update.qCD = 100;
			Main.update.wCD = 3001;
			Main.update.eCD = 801;
			this.setHealth(mhp);
		}
		//Teleports PC to Taverly, default neutral Town
		else if(using.getID() == "Teleport to Town"){
			Main.update.area = new Area("Taverly");
			Main.update.splashScreenTime = 50;
			KeyboardListener.escape = true;
			this.setX(GraphicsMain.WIDTH/2 - 96);
			this.setY(GraphicsMain.HEIGHT - GraphicsMain.HEIGHT/16 - 96);
		}
	}
	
	/**
	 * Returns the current Damage value that Player is capable of dealing
	 * @return
	 */
	public int getDamage() {
		return baseAttack + this.getWeapon().getDamage();
	}
	
	/**
	 * Returns the current Defense value that Player has
	 * @return
	 */
	public int getDefense(){
		return baseDefense + this.getArmor().getArmor();
	}

	/**
	 * Sets the attacking timer wherein Player cannot attack again
	 * @param i
	 */
	public void attacking(int i){
		attackSpeed = i;
	}
	
	/**
	 * Returns if Player is currently in an attacking motion
	 * @return
	 */
	public boolean getAttacking(){
		if(attackSpeed > 0){
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Commences the Attack Animation
	 */
	public void attack() {
		if(face == UP){
			if(40 >= attackSpeed && 30 < attackSpeed){
				this.image = UAttack[0];
			}
			else if(30 >= attackSpeed && 20 < attackSpeed){
				this.image = UAttack[1];
			}
			else if(20 >= attackSpeed && 10 < attackSpeed){
				this.image = UAttack[2];
			}
			else if(10 >= attackSpeed && 0 < attackSpeed){
				this.image = UAttack[3];
			}
		}
		else if(face == DOWN){
			if(40 >= attackSpeed && 35 < attackSpeed){
				this.image = DAttack[0];
			}
			else if(35 >= attackSpeed && 30 < attackSpeed){
				this.image = DAttack[1];
			}
			else if(30 >= attackSpeed && 25 < attackSpeed){
				this.image = DAttack[2];
			}
			else if(25 >= attackSpeed && 20 < attackSpeed){
				this.image = DAttack[3];
			}
			else if(20 >= attackSpeed && 15 < attackSpeed){
				this.image = DAttack[2];
			}
			else if(15 >= attackSpeed && 10 < attackSpeed){
				this.image = DAttack[1];
			}
			else if(10 >= attackSpeed && 0 < attackSpeed){
				this.image = DAttack[0];
			}
		}
		else if(face == RIGHT){
			if(40 >= attackSpeed && 30 < attackSpeed){
				this.image = RAttack[0];
			}
			else if(30 >= attackSpeed && 20 < attackSpeed){
				this.image = RAttack[1];
			}
			else if(20 >= attackSpeed && 10 < attackSpeed){
				this.image = RAttack[2];
			}
			else if(10 >= attackSpeed && 0 < attackSpeed){
				this.image = RAttack[3];
			}
		}
		else if(face == LEFT){
			if(40 >= attackSpeed && 30 < attackSpeed){
				this.image = LAttack[0];
			}
			else if(30 >= attackSpeed && 20 < attackSpeed){
				this.image = LAttack[1];
			}
			else if(20 >= attackSpeed && 10 < attackSpeed){
				this.image = LAttack[2];
			}
			else if(10 >= attackSpeed && 0 < attackSpeed){
				this.image = LAttack[3];
			}
		}
		attackSpeed--;
	}
	
	/**
	 * Sets the Player invincible to damage for a duration of time
	 * @param duration
	 */
	public void setInvin(int duration){
		invin = duration;
	}
	
	/**
	 * Checks if the Player is invincible
	 * @return
	 */
	public int getInvin(){
		return this.invin;
	}

	/**
	 * Damages the Player a certain amount of damage, taking into account Armor/Defense values
	 * @param damage
	 */
	public void damage(int damage) {
		if(invin == 0){
			int inflictedDamage = damage - (this.armor.getArmor()/2);
			if(inflictedDamage <= 0){
				inflictedDamage = 2;
			}
			this.hp -= inflictedDamage;
			this.damaged = inflictedDamage;
			this.scroll = 30;
		}
	}
	
	/**
	 * Returns avaliable Skill Points of Player
	 * @return
	 */
	public int getPoints(){
		return this.skillPoints;
	}
	
	/**
	 * Sets the amount of avaliable Skill Points for Player
	 * @param points
	 */
	public void setPoints(int points){
		this.skillPoints = points;
	}
	
	/**
	 * Returns the Skill Tree that Player has
	 * @return
	 */
	public SkillTree getSkillTree(){
		skt.updatePoints();
		return skt;
	}

	/**
	 * Lets the Player commence an Attack in the direction he is facing
	 */
	public void doAttack() {
		Rectangle2D attackBox = new Rectangle2D.Double();
		this.updateBoundbox();
		int face = this.getFace();
		if(face == 1){
			attackBox = new Rectangle2D.Double(this.getX()-10, this.getY()-20, this.getWidth()+20, 20);
		}
		else if(face == 2){
			attackBox = new Rectangle2D.Double(this.getX()-10, this.getY()+this.getHeight(), this.getWidth()+20, 20);
		}
		else if(face == 3){
			attackBox = new Rectangle2D.Double(this.getX()-20, this.getY()-10, 20, this.getHeight()+20);
		}
		else if(face == 4){
			attackBox = new Rectangle2D.Double(this.getX() + this.getWidth(), this.getY()-10, 20, this.getHeight()+20);
		}
		for(int i = 0; i < Main.update.enemies.size(); i++){
			if(Main.update.enemies.get(i).getBoundbox().intersects(attackBox)){
				int dmg = RNG.nextInt(this.getDamage()/2) + this.getDamage()/2;
				Main.update.enemies.get(i).damage(dmg);
			}
		}
		attackBox = null;
	}
}