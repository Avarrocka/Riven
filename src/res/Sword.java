package res;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.imageio.ImageIO;

import main.GraphicsMain;
import main.Main;
import main.Render;

/**
 * Class defines an enemy object
 * @author Brian Chen
 *
 */
public class Sword implements Drawable{
	private String ID;
	private int x, y;
	private int damage;
	private int value;
	private String desc;
	private String info;
	private Rectangle2D boundBox;
	private static final int WIDTH = 100, HEIGHT = 100;
	private BufferedImage image;
	/**
	 * Constructor. Creates a player character.
	 */
	public Sword(int x, int y, String ID) {
		this.setID(ID);
		this.setX(x);
		this.setY(y);
		if(this.ID == "Rusted Sword"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordRust.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 5;
			this.value = 0;
			this.desc = "A rusted sword not capable of more than a blunt strike.";
			this.info = "This is a starter weapon that does 5 damage per hit";
		}
		if(this.ID == "Iron Sword"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordIron.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 10;
			this.value = 100;
			this.desc = "A simple, balanced sword made of cold iron.";
			this.info = "This is a basic weapon that does 10 damage per hit";
		}
		if(this.ID == "Scimitar"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordScimitar.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 14;
			this.value = 250;
			this.desc = "An exotic Scimitar brought in through the ports by traders. It looks sharp.";
			this.info = "This is a basic weapon that does 14 damage per hit";
		}
		if(this.ID == "Obsidian Sword"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordSteel.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 20;
			this.value = 500;
			this.desc = "Made of volcanic glass. This sword is brittle but undeniably dangerous.";
			this.info = "This an advanced weapon that does 20 damage per hit, plus an effect.";
		}
		if(this.ID == "Serrated Blade"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordSerrated.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 25;
			this.value = 1000;
			this.desc = "Tempered steel folded over 1000 times creates a weapon of unparalleled deadliness.";
			this.info = "This is an advanced weapon that does 25 damage per hit, plus an effect.";
		}
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		if(this.ID == "Lunus Blade"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordLunus.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 30;
			this.value = 0;
			this.desc = "An ancient hierloom that once belonged to the Fallen God, Lunus.";
			this.info = "This is a legendary weapon that does 30 damage per hit, plus an effect.";
		}
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
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
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	public int getDamage(){
		return this.damage;
	}
	public int getValue(){
		return this.value;
	}
	public void setID(String ID){
		this.ID = ID;
	}
	public String getID(){
		return this.ID;
	}
	public String getDescription(){
		return this.desc;
	}
	public String getInfo(){
		return this.info;
	}
	public BufferedImage getImage() {
		return this.image;
	}
	public void setImage(BufferedImage image){
		this.image = image;
	}
	public Rectangle2D getBoundbox(){
		return this.boundBox;
	}
	public void updateBoundbox(){
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
	}
}