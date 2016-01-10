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
import main.Update;

/**
 * Class defines an Enemy object
 * @author Brian Chen
 */
public class Enemy implements Drawable{
	private String ID;
	private Rectangle2D boundBox;
	private Rectangle2D smallBB;
	private int x, y;
	private int WIDTH = 56, HEIGHT = 64;
	private int health;
	private int maxHealth;
	private int damage;
	private int motionSpeed = 21; //Animation counter
	private int EXP;
	private int trackTimer = 0;
	private int attackSpeed = 20;
	private int damaged = 0;
	private int scroll = 30;
	private BufferedImage image, immob, hitSplat, hpbar;
	private BufferedImage movement[];
	public boolean revMov;
	private boolean dead, isBoss = false;
	private int stun = 0;
	private LinkedList<Armor> aDrops = new LinkedList<>();
	private LinkedList<Sword> sDrops = new LinkedList<>();
	Random RNG = new Random();

	/**
	 * Creates an Enemy Object, a hostile NPC that damages the Player.
	 * @param x	Location
	 * @param y	Location
	 * @param ID Type of Monster
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
			hpbar = ImageIO.read(getClass().getClassLoader().getResource("UI/hpb.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			aDrops.add(new Armor(0, 0, "Heavy Overcoat"));
			sDrops.add(new Sword(0, 0, "Ceremonial Sabre"));
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
			this.WIDTH = 45;
			this.HEIGHT = 49;
			this.image = movement[0];
			this.health = 50;
			this.damage = 14;
			this.EXP = 10;
			aDrops.add(new Armor(0, 0, "Heavy Overcoat"));
			sDrops.add(new Sword(0, 0, "Ceremonial Sabre"));
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
			this.WIDTH = 43;
			this.HEIGHT = 50;
			this.image = movement[0];
			this.health = 80;
			this.damage = 15;
			this.EXP = 15;
			aDrops.add(new Armor(0, 0, "Heavy Overcoat"));
			sDrops.add(new Sword(0, 0, "Ceremonial Sabre"));
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
			aDrops.add(new Armor(0, 0, "Field Commander's Armor"));
			sDrops.add(new Sword(0, 0, "Inscribed Blade"));
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
			aDrops.add(new Armor(0, 0, "Field Commander's Armor"));
			sDrops.add(new Sword(0, 0, "Inscribed Blade"));
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
			this.WIDTH = 50;
			this.HEIGHT = 60;
			this.image = movement[0];
			this.health = 120;
			this.damage = 25;
			this.EXP = 30;
			aDrops.add(new Armor(0, 0, "Field Commander's Armor"));
			sDrops.add(new Sword(0, 0, "Inscribed Blade"));
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

	/**
	 * Returns the X location
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the X position
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the Y position
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y position
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Draws the enemy onto the screen in the Render method
	 * @param g
	 */
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
		double hp = (((double)this.getHP() / (double)this.getMaxHealth()) * 50);
		Rectangle2D maxhp = new Rectangle2D.Double((int)(this.x) - 5, (int)(this.y) - 10, 50, 5);
		Rectangle2D curhp = new Rectangle2D.Double((int)(this.x) - 5, (int)(this.y)-10, hp, 5);
		g.drawImage(hpbar, (int)(this.x-9), (int)(this.y-11), 60, 9, null);
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
	
	/**
	 * Draws the damage sustained by the Enemy in Render
	 * @param g
	 */
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
	
	/**
	 * Gets the width of the enemy
	 * @return
	 */
	public int getWidth() {
		return WIDTH;
	}

	/**
	 * Gets the height of the enemy
	 * @return
	 */
	public int getHeight() {
	 return HEIGHT;
	}
	
	/**
	 * Gets the ID of the enemy
	 * @return
	 */
	public String getID(){
		return this.ID;
	}
	
	/**
	 * Sets the ID of the enemy
	 * @param ID
	 */
	public void setID(String ID){
		this.ID = ID;
	}
	
	/**
	 * Gets the HP of the enemy
	 * @return
	 */
	public int getHP(){
		return this.health;
	}
	
	/**
	 * Sets the HP of the enemy
	 * @param HP
	 */
	public void setHP(int HP){
		this.health = HP;
	}
	
	/**
	 * Returns the maximum health possible of Enemy
	 * @return
	 */
	public int getMaxHealth(){
		return this.maxHealth;
	}
	
	/**
	 * Gets the image of the enemy
	 */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**
	 * Gets the boundbox of the Enemy
	 * @return
	 */
	public Rectangle2D getBoundbox(){
		return this.boundBox;
	}
	
	/**
	 * Gets the collision rectangle of the Enemy
	 * @return
	 */
	public Rectangle2D getSmall(){
		return this.smallBB;
	}

	/**
	 * Applies damage to the Enemy and checks if death
	 * @param damage
	 */
	public void damage(int damage){
		this.health -= damage;
		this.damaged = damage;
		this.scroll = 30;
		if(this.health <= 0){
			this.health = 0;
			this.dead = true;
		}
	}
	
	/**
	 * Returns if the Enemy is dead
	 * @return
	 */
	public boolean getDead(){
		return this.dead;
	}
	
	/**
	 * Makes the Enemy track down and hunt the Player Character to attack them
	 */
	public void trackPC(){
		if(!(this.boundBox.intersects(Update.PC.getBoundbox()))){
			if(trackTimer <= 0){
				if(Update.PC.getX() > this.getX()){
					this.x++;
				}
				else if(Update.PC.getX() < this.getX())
					this.x--;
				if(Update.PC.getY() > this.getY()){
					this.y++;
				}
				else if(Update.PC.getY() < this.getY())
					this.y--;
				if(!isBoss)
					trackTimer = 2;
				else
					trackTimer = 0;
			}
			else
				trackTimer--;
		}
		else
			retaliate();
	}
	
	/**
	 * Updates the status of the Enemy character
	 */
	public void update(){
		if(stun>0)
			stun--;
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		this.smallBB = new Rectangle2D.Double(this.x+7, this.y+8, WIDTH-25, HEIGHT-24);
		if(motionSpeed == 0){ //Reverses movement animation if it has already looped
			motionSpeed = 21;
			revMov = !revMov;
		}
		//Movement animation
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
		motionSpeed--;
	}
	
	/**
	 * Randomly generates the money obtained by PC dependent on the EXP Enemy gives
	 * @return
	 */
	public int rollMoney(){
		return RNG.nextInt(this.EXP) + 10;
	}
	
	/**
	 * Returns how much EXP this Enemy gives
	 * @return
	 */
	public int getEXP(){
		return this.EXP;
	}
	
	/**
	 * Checks the potential loot table and if Random is within the ChanceForLoot, then PC recieves an item
	 */
	public void rollLoot() {
		if(!aDrops.isEmpty() && !sDrops.isEmpty()){
			if(this.isBoss){
				int armorOrSword = RNG.nextInt(2);
				if(armorOrSword == 0){
					int armorDrop = RNG.nextInt(aDrops.size());
					Armor dropped = aDrops.get(armorDrop);
					boolean have = false;
					for(int i = 0; i < Update.PC.invArmor.size(); i++){
						if(Update.PC.invArmor.get(i).getID() == dropped.getID()){
							have = true;
							break;
						}
					}
					if(!have)
						Update.PC.addItem(dropped);
				}
				if(armorOrSword == 1){
					int swordDrop = RNG.nextInt(sDrops.size());
					Sword dropped = sDrops.get(swordDrop);
					boolean have = false;
					for(int i = 0; i < Update.PC.invSwords.size(); i++){
						if(Update.PC.invSwords.get(i).getID() == dropped.getID()){
							have = true;
							break;
						}
					}
					if(!have)
						Update.PC.addItem(dropped);
				}
			}
			else{
				int chanceForLoot = RNG.nextInt(100);
				if(chanceForLoot > 0 && chanceForLoot < 15){
					int armorOrSword = RNG.nextInt(2);
					if(armorOrSword == 0){
						int armorDrop = RNG.nextInt(aDrops.size());
						Armor dropped = aDrops.get(armorDrop);
						boolean have = false;
						for(int i = 0; i < Update.PC.invArmor.size(); i++){
							if(Update.PC.invArmor.get(i).getID() == dropped.getID()){
								have = true;
								break;
							}
						}
						if(!have)
							Update.PC.addItem(dropped);
					}
					if(armorOrSword == 1){
						int swordDrop = RNG.nextInt(sDrops.size());
						Sword dropped = sDrops.get(swordDrop);
						boolean have = false;
						for(int i = 0; i < Update.PC.invSwords.size(); i++){
							if(Update.PC.invSwords.get(i).getID() == dropped.getID()){
								have = true;
								break;
							}
						}
						if(!have)
							Update.PC.addItem(dropped);
					}
				}
			}
		}
	}
	
	/**
	 * Basically an attack method for Enemy towards PC
	 */
	public void retaliate() {
		if(stun <= 0){
			if(attackSpeed <= 0){
				int dmg = RNG.nextInt(this.damage/2)+(this.damage/2);
				Update.PC.damage(dmg);
				attackSpeed = 60;
			}
			else
				attackSpeed--;
		}
	}
	
	/** 
	 * Stuns the Enemy making them unable to attack for duration of time
	 * @param time Duration the enemy is unable to attack
	 */
	public void stun(int time){
		stun = time;
	}
}