package res;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
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
	private static final int WIDTH = 56, HEIGHT = 64;
	private int health;
	private int maxHealth;
	private int damage;
	private int motionSpeed = 21;
	private int EXP;
	private int face;
	private int moveCounter;
	private int vX;
	private int vY;
	private static final int DEFAULT = 0, UP = 1, DOWN = 2, RIGHT = 3, LEFT = 4;
	private BufferedImage image;
	private BufferedImage movement[];
	public boolean revMov;
	private boolean dead;
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
		this.moveCounter = 0;
		this.xBoundaryLow = x - 40;
		this.xBoundaryHigh = x + 40;
		this.yBoundaryLow = y - 40;
		this.yBoundaryHigh = y + 40;
		if(ID == "slime"){
			movement = new BufferedImage[3];
			try {
				movement[0] = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/slime1.png"));
				movement[1]  = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/slime2.png"));
				movement[2] =  ImageIO.read(getClass().getClassLoader().getResource("Sprites/Enemies/slime3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.image = movement[0];
			this.health = 2;
			this.damage = 10;
			this.EXP = 5;
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
		if(this.health <= 0){
			this.health = 0;
			this.dead = true;
		}
	}
	public boolean getDead(){
		return this.dead;
	}
	public void update(){
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
		if(s == "slime"){
			return RNG.nextInt(10) + 1;
		}
		else
			return 0;
	}
	public int getEXP(){
		return this.EXP;
	}
	public void rollLoot() {
		
	}
	public void move(int face){
		moveCounter = 60;
		if(face == LEFT){
			vX = -1;
		}
		else if(face == RIGHT){
			vX = 1;
		}
		else if(face == UP){
			vY = -1;
		}
		else if(face == DOWN){
			vY = 1;
		}
	}
	public boolean moving(){
		if(moveCounter > 0){
			return true;
		}
		return false;
	}
	public void retaliate() {
		Main.update.PC.damage(this.damage);
	}
}