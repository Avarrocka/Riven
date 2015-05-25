package res;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GraphicsMain;
import main.Main;
import main.Render;

/**
 * Class defines an enemy object
 * @author Brian Chen
 *
 */
public class Enemy implements Drawable{
	private String ID;
	private Rectangle2D boundBox;
	private Rectangle2D smallBB;
	private int x, y;
	private int xBoundaryLow;
	private int xBoundaryHigh;
	private int yBoundaryLow;
	private int yBoundaryHigh;
	private int WIDTH = 56, HEIGHT = 64;
	private int health;
	private int maxHealth;
	private int damage;
	private int motionSpeed = 21;
	private int EXP;
	private int face;
	private int moveCounter;
	private int vX;
	private int vY;
	private int trackTimer = 0;
	private int attackSpeed = 20;
	private int damaged = 0;
	private int scroll = 30;
	private static final int DEFAULT = 0, UP = 1, DOWN = 2, RIGHT = 3, LEFT = 4;
	private BufferedImage image, immob, hitSplat;
	private BufferedImage movement[];
	public boolean revMov;
	private boolean dead, isBoss = false;
	private int stun = 0;
	private LinkedList<Armor> aDrops = new LinkedList<>();
	private LinkedList<Sword> sDrops = new LinkedList<>();
	Random RNG = new Random();
	/**
	 * Constructor. Creates a player character.
	 */
	public Enemy(int x, int y, String ID) {
		this.setX(x);
		this.setY(y);
		this.setID(ID);
		revMov = false;
		dead = false;
		try {
			immob = ImageIO.read(getClass().getClassLoader().getResource("UI/immob.png"));
			hitSplat = ImageIO.read(getClass().getClassLoader().getResource("UI/hitSplat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.moveCounter = 0;
		if(ID == "slime"){
			movement = new BufferedImage[3];
			try {
				movement[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/slime1.png"));
				movement[1]  = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/slime2.png"));
				movement[2] =  ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/slime3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.WIDTH = 56;
			this.HEIGHT = 64;
			this.image = movement[0];
			this.health = 30;
			this.damage = 10;
			this.EXP = 5;
			aDrops.add(new Armor(0, 0, "Field Commander's Armor"));
			sDrops.add(new Sword(0, 0, "Inscribed Blade"));
		}
		else if(ID == "gremlin"){
			movement = new BufferedImage[3];
			try {
				movement[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/gremlin1.png"));
				movement[1]  = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/gremlin2.png"));
				movement[2] =  ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/gremlin3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.WIDTH = 35;
			this.HEIGHT = 49;
			this.image = movement[0];
			this.health = 50;
			this.damage = 14;
			this.EXP = 10;
		}
		else if(ID == "snowman"){
			movement = new BufferedImage[3];
			try {
				movement[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/snowman1.png"));
				movement[1]  = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/snowman2.png"));
				movement[2] =  ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/snowman3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.WIDTH = 40;
			this.HEIGHT = 50;
			this.image = movement[0];
			this.health = 80;
			this.damage = 15;
			this.EXP = 15;
		}
		else if(ID == "aquaGoo"){
			movement = new BufferedImage[3];
			try {
				movement[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/aquaGoo1.png"));
				movement[1]  = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/aquaGoo2.png"));
				movement[2] =  ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/aquaGoo3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.WIDTH = 45;
			this.HEIGHT = 55;
			this.image = movement[0];
			this.health = 100;
			this.damage = 20;
			this.EXP = 22;
		}
		else if(ID == "flameSpirit"){
			movement = new BufferedImage[3];
			try {
				movement[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/fspirit1.png"));
				movement[1]  = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/fspirit2.png"));
				movement[2] =  ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/fspirit3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.WIDTH = 35;
			this.HEIGHT = 49;
			this.image = movement[0];
			this.health = 100;
			this.damage = 20;
			this.EXP = 22;
		}
		else if(ID == "zombie"){
			movement = new BufferedImage[3];
			try {
				movement[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/zombie1.png"));
				movement[1]  = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/zombie2.png"));
				movement[2] =  ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/zombie3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.WIDTH = 30;
			this.HEIGHT = 40;
			this.image = movement[0];
			this.health = 120;
			this.damage = 25;
			this.EXP = 30;
		}
		else if(ID == "squid"){
			this.isBoss = true;
			movement = new BufferedImage[3];
			try {
				movement[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/squid1.png"));
				movement[1]  = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/squid2.png"));
				movement[2] =  ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/squid3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.aDrops.add(new Armor(0, 0, "Gemmed Armor"));
			this.sDrops.add(new Sword(0, 0, "Frozen Breath"));
			this.WIDTH = 65;
			this.HEIGHT = 90;
			this.image = movement[0];
			this.health = 300;
			this.damage = 40;
			this.EXP = 100;
		}
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		this.smallBB = new Rectangle2D.Double(this.x+7, this.y+8, WIDTH-25, HEIGHT-24);
		this.maxHealth = health;
	}

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
	
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
		double hp = (((double)this.getHP() / (double)this.getMaxHealth()) * 50);
		Rectangle2D maxhp = new Rectangle2D.Double((int)(this.x) - 5, (int)(this.y) - 10, 50, 5);
		Rectangle2D curhp = new Rectangle2D.Double((int)(this.x) - 5, (int)(this.y)-10, hp, 5);
		g.setColor(Color.red);
		g.fill(maxhp);
		g.draw(maxhp);
		g.setColor(Color.green);
		g.fill(curhp);
		g.draw(curhp);
		if(stun>0){
			g.drawImage(immob, x-25, y-10, 20, 20, null);
		}
		drawDamage(g);
	}
	
	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
	 return HEIGHT;
	}
	
	public void setID(String ID){
		this.ID = ID;
	}
	public void setHP(int HP){
		this.health = HP;
	}
	public int getHP(){
		return this.health;
	}
	public String getID(){
		return this.ID;
	}
	public BufferedImage getImage() {
		return this.image;
	}
	public Rectangle2D getBoundbox(){
		return this.boundBox;
	}
	public Rectangle2D getSmall(){
		return this.smallBB;
	}
	public int getLowBoundX(){
		return this.xBoundaryLow;
	}
	public int getLowBoundY(){
		return this.yBoundaryLow;
	}
	public int getHighBoundX(){
		return this.xBoundaryHigh;
	}
	public int getHighBoundY(){
		return this.yBoundaryHigh;
	}
	public int getMaxHealth(){
		return this.maxHealth;
	}
	public void damage(int damage){
		this.health -= damage;
		this.damaged = damage;
		this.scroll = 30;
		if(this.health <= 0){
			this.health = 0;
			this.dead = true;
		}
	}
	public void drawDamage(Graphics2D g){
		if(this.damaged > 0){
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
	public boolean getDead(){
		return this.dead;
	}
	public void trackPC(){
		if(!(this.boundBox.intersects(Main.update.PC.getBoundbox()))){
			if(trackTimer <= 0){
				if(Main.update.PC.getX() > this.getX()){
					this.x++;
				}
				else if(Main.update.PC.getX() < this.getX())
					this.x--;
				if(Main.update.PC.getY() > this.getY()){
					this.y++;
				}
				else if(Main.update.PC.getY() < this.getY())
					this.y--;
				if(!isBoss)
					trackTimer = 2;
				else
					trackTimer = 1;
			}
			else
				trackTimer--;
		}
		else
			retaliate();
	}
	public void update(){
		if(stun>0)
			stun--;
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		this.smallBB = new Rectangle2D.Double(this.x+7, this.y+8, WIDTH-25, HEIGHT-24);
		if(motionSpeed == 0){
			motionSpeed = 21;
			revMov = !revMov;
		}
		if(!revMov){
			if(21 >= motionSpeed && 14 < motionSpeed){
				this.image = movement[2];
			}
			else if(14 >= motionSpeed && 7 < motionSpeed){
				this.image = movement[1];
			}
			else if(7 >= motionSpeed && 0 < motionSpeed){
				this.image = movement[0];
			}
		}
		else{
			if(21 >= motionSpeed && 14 < motionSpeed){
				this.image = movement[0];
			}
			else if(14 >= motionSpeed && 7 < motionSpeed){
				this.image = movement[1];
			}
			else if(7 >= motionSpeed && 0 < motionSpeed){
				this.image = movement[2];
			}
		}
		if(moveCounter > 0){
			if(x + vX <= xBoundaryHigh && x - vX >= xBoundaryLow){
				this.setX(this.getX() + vX);
			}
			if(y + vY <= yBoundaryHigh && y - vY >= yBoundaryLow){
				this.setY(this.getY() + vY);
			}
			moveCounter--;
		}
		motionSpeed--;
	}
	public int rollMoney(String s){
		return RNG.nextInt(this.EXP) + 10;
	}
	public int getEXP(){
		return this.EXP;
	}
	public void rollLoot() {
		int chanceForLoot = RNG.nextInt(7);
		if(chanceForLoot > 0 && chanceForLoot < 7){
			int armorOrSword = RNG.nextInt(2);
			if(armorOrSword == 0){
				int armorDrop = RNG.nextInt(aDrops.size());
				Main.update.PC.addItem(aDrops.get(armorDrop));
			}
			if(armorOrSword == 1){
				int swordDrop = RNG.nextInt(aDrops.size());
				Main.update.PC.addItem(sDrops.get(swordDrop));
			}
		}
	}
	public void retaliate() {
		if(stun <= 0){
			if(attackSpeed <= 0){
				int dmg = RNG.nextInt(this.damage/2)+(this.damage/2);
				Main.update.PC.damage(dmg);
				attackSpeed = 60;
			}
			else
				attackSpeed--;
		}
	}
	public void stun(int time){
		stun = time;
	}
}