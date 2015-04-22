package res;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.imageio.ImageIO;

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
	public volatile LinkedList<Armor> invArmor = new LinkedList<Armor>(); 
	private static final int WIDTH = 56, HEIGHT = 64;
	private static final int DEFAULT = 0, UP = 1, DOWN = 2, RIGHT = 3, LEFT = 4;
	private BufferedImage image;
	private Rectangle2D boundBox;
	private BufferedImage def, up, down, right, left;
	/**
	 * Constructor. Creates a player character.
	 */
	public Player(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setHealth(100);
		this.setXvelocity(0);
		this.setYvelocity(0);
		this.setGold(15000);
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
			this.invSwords.add(this.getWeapon());
		}
		this.weapon = weapon;
	}
	public Sword getWeapon(){
		return this.weapon;
	}
	public void setArmor(Armor armor, int index){
		if(index >= 0){
			this.invArmor.remove(index);
			this.invArmor.add(this.getArmor());
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
	public void setImage(int face){
		if(face == DEFAULT){
			this.image = def;
		}
		else if(face == UP){
			this.image = up;
		}
		else if(face == DOWN){
			this.image = down;
		}
		else if(face == RIGHT){
			this.image = right;
		}
		else if(face == LEFT){
			this.image = left;
		}
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
}