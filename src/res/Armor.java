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
public class Armor implements Drawable{
	private String ID;
	private int x, y;
	private int armor;
	private int value;
	private String desc;
	private String info;
	private Rectangle2D boundBox;
	private static final int WIDTH = 100, HEIGHT = 100;
	private BufferedImage image;
	/**
	 * Constructor. Creates a player character.
	 */
	public Armor(int x, int y, String ID) {
		this.setID(ID);
		this.setX(x);
		this.setY(y);
		if(this.ID == "Leather Armor"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/armorLeather.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.armor = 5;
			this.value = 0;
			this.desc = "A well-fitting leather cassock that sometimes causes hits to glance.";
			this.info = "This is basic armor that grants 5 Armor.";
		}
		if(this.ID == "Plated Armor"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/armorPlated.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.armor = 10;
			this.value = 100;
			this.desc = "Thin metal plates are added onto a leather breastplate to provide adequate protection.";
			this.info = "This is basic armor that grants 10 Armor.";
		}
		if(this.ID == "Tempered Armor"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/armorTempered.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.armor = 15;
			this.value = 250;
			this.desc = "Cold iron cast into a sturdy breastplate that looks as intimidating as it is heavy.";
			this.info = "This is basic armor that grants 15 Armor.";
		}
		if(this.ID == "Steel Armor"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/armorSteel.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.armor = 20;
			this.value = 500;
			this.desc = "Refined steel plates interlock to create a work of high craftsmanship.";
			this.info = "This is advanced armor that grants 20 Armor.";
		}
		if(this.ID == "Darksteel Armor"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/armorDarksteel.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.armor = 25;
			this.value = 1000;
			this.desc = "A heirloom armor passed through generations. It is said to bear an ancient curse of protection.";
			this.info = "This is advanced armor that grants 25 Armor, plus an effect.";
		}
		if(this.ID == "Blessed Armor"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/armorBlessed.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.armor = 35;
			this.value = 0;
			this.desc = "Once worn by Paladin Uther the Lightbringer, this plate's legacy is renown all throughout the lands.";
			this.info = "This is legendary armor that grants 35 Armor.";
		}
		if(this.ID == "Blighted Armor"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/armorBlighted.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.armor = 35;
			this.value = 0;
			this.desc = "Forged for the Lich King Arthas, this plate is said to have a life and will of it's own.";
			this.info = "This is legendary armor that grants 35 Armor.";
		}
		if(this.ID == "Solus Armor"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/armorSolus.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.armor = 45;
			this.value = 0;
			this.desc = "A divine plate created by the Gods. A swirling inferno protects the user and incinerates foes.";
			this.info = "This is mythical armor that grants 45 Armor, plus an effect.";
		}
		this.boundBox = new Rectangle2D.Double(this.x, this.y, 64, 64);
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
	public int getArmor(){
		return this.armor;
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