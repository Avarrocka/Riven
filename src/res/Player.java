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
	private int x, y, hp;
	private int Vx, Vy;
	private int gold;
	private Sword weapon;
	private Armor armor;
	public volatile LinkedList<Sword> invSwords = new LinkedList<Sword>(); 
	public volatile LinkedList<Item> invItems = new LinkedList<Item>(); 
	public volatile LinkedList<Item> qItems = new LinkedList<Item>();
	public volatile LinkedList<Armor> invArmor = new LinkedList<Armor>();
	int motionSpeed = 60;
	BufferedImage Left[] = new BufferedImage[4];
	BufferedImage Right[] = new BufferedImage[4];
	BufferedImage Up[] = new BufferedImage[4];
	BufferedImage Down[] = new BufferedImage[4];
	private boolean revMov = false;
	private boolean hpBuff = false;
	private static final int WIDTH = 56, HEIGHT = 64;
	private static final int DEFAULT = 0, UP = 1, DOWN = 2, RIGHT = 4, LEFT = 3;
	private BufferedImage image;
	private Rectangle2D boundBox;
	private BufferedImage def, up, down, right, left;
	/**
	 * Constructor. Creates a player character.
	 */
	public Player(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setHealth(1);
		this.setXvelocity(0);
		this.setYvelocity(0);
		this.setGold(12000);
		this.setWeapon(new Sword(0, 0, "Rusted Sword"), -1);
		this.setArmor(new Armor(0, 0, "Cape"), -1);
		try {
			def = ImageIO.read(getClass().getClassLoader().getResource("Sprites/chromDefault.png"));
			up = ImageIO.read(getClass().getClassLoader().getResource("Sprites/chromUp.png"));
			down = ImageIO.read(getClass().getClassLoader().getResource("Sprites/chromDown.png"));
			right = ImageIO.read(getClass().getClassLoader().getResource("Sprites/chromLeft.png"));
			left = ImageIO.read(getClass().getClassLoader().getResource("Sprites/chromRight.png"));
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
			this.setHealth(this.getHealth() + 20);
			hpBuff = true;
		}
		else if(hpBuff && armor.getID() != "Darksteel Armor"){
			this.setHealth(this.getHealth() - 20);
			hpBuff = !hpBuff;
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
		if(!revMov){
			if(face == DEFAULT){
				this.image = def;
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
				this.image = def;
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
	}
	public void heal(int heal){
		if(hpBuff){
			if(this.hp + heal <= 120)
				this.hp = this.hp+heal;
			else
				this.hp = 120;
		}
		else{
			if(this.hp + heal <= 100)
				this.hp = this.hp+heal;
			else
				this.hp = 100;
		}		
	}
	public void activateItem(Item item, int i) {
		invItems.remove(i);
		Item using = item;
		if(using.getID() == "Fish"){
			if(this.getHealth() + using.getHeal() <= 100)
				this.setHealth(this.getHealth() + using.getHeal());
		}
		else if(using.getID() == "Cake"){
			if(this.getHealth() + using.getHeal() <= 100)
				this.setHealth(this.getHealth() + using.getHeal());
		}
		else if(using.getID() == "Pie"){
			if(this.getHealth() + using.getHeal() <= 100)
				this.setHealth(this.getHealth() + using.getHeal());
		}
		else if(using.getID() == "Healing Salve"){
			this.setHealth(100);
			if(hpBuff){
				this.setHealth(120);
			}
		}
		else if(using.getID() == "Teleport to Town"){
			Main.update.mapID = "Taverly";
			Main.update.splashScreenTime = 250;
			KeyboardListener.escape = true;
			this.setX(GraphicsMain.WIDTH/2 - 96);
			this.setY(GraphicsMain.HEIGHT - GraphicsMain.HEIGHT/16 - 96);
		}
	}
}