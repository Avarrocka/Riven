package res;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.imageio.ImageIO;

import listeners.KeyboardListener;
import main.GraphicsMain;
import main.Main;
import main.Render;
import res.Sword;
import res.Item;

/**
 * Class defines an enemy object
 * @author Brian Chen
 *
 */
public class Player implements Drawable{
	private int x, y, hp, mhp;
	private int Vx, Vy;
	private int EXP;
	private int gold;
	private Sword weapon;
	private Armor armor;
	public volatile LinkedList<Sword> invSwords = new LinkedList<Sword>(); 
	public volatile LinkedList<Item> invItems = new LinkedList<Item>(); 
	public volatile LinkedList<Item> qItems = new LinkedList<Item>();
	public volatile LinkedList<Armor> invArmor = new LinkedList<Armor>();
	private int motionSpeed = 60;
	private int attackSpeed = 40;
	private int reqLvl = 12;
	private int level;
	private int face;
	private boolean r = false;
	BufferedImage Left[] = new BufferedImage[4];
	BufferedImage Right[] = new BufferedImage[4];
	BufferedImage Up[] = new BufferedImage[4];
	BufferedImage Down[] = new BufferedImage[4];
	BufferedImage LAttack[] = new BufferedImage[4];
	BufferedImage RAttack[] = new BufferedImage[4];
	BufferedImage DAttack[] = new BufferedImage[4];
	BufferedImage UAttack[] = new BufferedImage[4];
 	private boolean revMov = false;
	private boolean hpBuff = false;
	private int baseAttack, baseDefense;
	private static final int WIDTH = 56, HEIGHT = 64;
	private static final int DEFAULT = 0, UP = 1, DOWN = 2, RIGHT = 4, LEFT = 3;
	private BufferedImage image;
	private Rectangle2D boundBox;
	private BufferedImage defL, defR;
	public boolean oozeQuest;
	/**
	 * Constructor. Creates a player character.
	 */
	public Player(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setXvelocity(0);
		this.setYvelocity(0);
		this.setGold(12000);
		this.mhp = 100;
		this.setHealth(mhp);
		this.baseAttack = 2;
		this.baseDefense = 2;
		this.EXP = 0;
		this.level = 1;
		this.setWeapon(new Sword(0, 0, "Rusted Sword"), -1);
		this.setArmor(new Armor(0, 0, "Leather Armor"), -1);
		try {
			defL = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CDL.png"));
			defR = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/CDR.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	 * Translates typeCode into waste type.
	 * @return
	 */

	@Override
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getXvelocity() {
		return this.Vx;
	}
	
	public int getYvelocity() {
		return this.Vy;
	}
	
	public void setXvelocity(int Vx) {
		this.Vx = Vx;
	}

	public void setYvelocity(int Vy) {
		this.Vy = Vy;
	}

	public int getHealth() {
		return hp;
	}

	public void setHealth(int hp) {
		this.hp = hp;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
	}
	
	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
	 return HEIGHT;
	}
	
	public void setGold(int gold){
		this.gold = gold;
	}
	
	public int getGold(){
		return this.gold;
	}
	
	public int getFace(){
		return this.face;
	}
	public void setWeapon(Sword weapon, int index){
		if(index >= 0){
			this.invSwords.remove(index);
			this.invSwords.add(index, this.getWeapon());
		}
		this.weapon = weapon;
	}
	public Sword getWeapon(){
		return this.weapon;
	}
	public void setArmor(Armor armor, int index){
		if(index >= 0){
			this.invArmor.remove(index);
			this.invArmor.add(index, this.getArmor());
		}
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
	public Armor getArmor(){
		return this.armor;
	}
	public void addItem(Item item){
		invItems.add(item);
	}
	public void addItem(Armor armor){
		invArmor.add(armor);
	}
	public void addItem(Sword sword){
		invSwords.add(sword);
	}
	public void addQuestItem(Item item){
		qItems.add(item);
	}
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
	public BufferedImage getImage() {
		return image;
	}
	public Rectangle2D getBoundbox(){
		return this.boundBox;
	}
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
	public int getEXP(){
		return this.EXP;
	}
	public void setEXP(int EXP){
		this.EXP += EXP;
		if(this.EXP >= reqLvl){
			this.EXP = this.EXP - reqLvl;
			this.levelUp();
			this.updateLevelCurve();
		}
	}
	public int getLevel(){
		return this.level;
	}
	private void levelUp() {
		level++;
		Main.update.levelUp = 60;
		this.baseAttack = 2*level;
		this.baseDefense = 2*level;
		this.mhp += 10;
	}
	public int getReqLvl(){
		return this.reqLvl;
	}
	private void updateLevelCurve(){
		reqLvl = ((int) (reqLvl*1.5));
	}
	public void heal(int heal){
		if(this.hp + heal <= mhp)
			this.hp = this.hp+heal;
		else
			this.hp = mhp;	
	}
	public void activateItem(Item item, int i) {
		invItems.remove(i);
		Item using = item;
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
		else if(using.getID() == "Healing Salve"){
			Main.update.qCD = 801;
			Main.update.wCD = 3001;
			this.setHealth(mhp);
		}
		else if(using.getID() == "Teleport to Town"){
			Main.update.mapID = "Taverly";
			Main.update.splashScreenTime = 250;
			KeyboardListener.escape = true;
			this.setX(GraphicsMain.WIDTH/2 - 96);
			this.setY(GraphicsMain.HEIGHT - GraphicsMain.HEIGHT/16 - 96);
		}
	}

	public int getDamage() {
		return baseAttack + this.getWeapon().getDamage();
	}
	public int getDefense(){
		return baseDefense + this.getArmor().getArmor();
	}

	public int getMaxHealth() {
		return mhp;
	}
	public void attacking(int i){
		attackSpeed = i;
	}
	public boolean getAttacking(){
		if(attackSpeed > 0){
			return true;
		}
		else
			return false;
	}
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

	public void damage(int damage) {
		int inflictedDamage = damage - (this.armor.getArmor()/2);
		if(inflictedDamage <= 0){
			inflictedDamage = 2;
		}
		this.hp -= inflictedDamage;
	}
}