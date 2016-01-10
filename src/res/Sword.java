package res;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class defines a Sword object
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
	 * Creates a Sword object with location values x and y, stats depend on ID
	 * @param x
	 * @param y
	 * @param ID
	 */
	public Sword(int x, int y, String ID) {
		this.setID(ID);
		this.setX(x);
		this.setY(y);
		//Defines stats, damage, images, and value based on ID
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
		if(this.ID == "Ceremonial Sabre"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordRitual.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 8;
			this.value = 0;
			this.desc = "A ceremonial sabre that is not meant for real combat.";
			this.info = "This is a starter weapon that does 8 damage per hit";
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
		if(this.ID == "Katana"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordKatana.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 15;
			this.value = 250;
			this.desc = "Tempered steel folded over 1000 times creates a weapon with a deadly edge.";
			this.info = "This is a basic weapon that does 15 damage per hit";
		}
		if(this.ID == "Steel Sword"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordSteel.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 20;
			this.value = 500;
			this.desc = "Tried and true, this weapon of refined steel slices through foes like butter.";
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
			this.desc = "Hundreds of thorns and edges adorn this darksteel blade to maximize gore.";
			this.info = "This is an advanced weapon that does 25 damage per hit, plus an effect.";
		}
		if(this.ID == "The Wanderer"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordBlessed.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 35;
			this.value = 1000;
			this.desc = "Caladbolg, blessed by the wind, causes each agile strike to deal devastating damage.";
			this.info = "This is a legendary weapon that does 35 damage per hit.";
		}
		if(this.ID == "The Soul Reaver"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordBlighted.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 35;
			this.value = 1000;
			this.desc = "Claíomh Saolus, a blade rumored to absorb the souls of those it has slain.";
			this.info = "This is a legendary weapon that does 35 damage per hit.";
		}
		if(this.ID == "Lunus Blade"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordLunus.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 45;
			this.value = 0;
			this.desc = "An blade of legend that once belonged to the Fallen God, Lunus.";
			this.info = "This is a mythical weapon that does 45 damage per hit, plus an effect.";
		}
		if(this.ID == "Inscribed Blade"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordImbued.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 16;
			this.value = 0;
			this.desc = "A weapon inscribed with the prayers of family members. Still well-balanced and usable.";
			this.info = "This is an advanced weapon that does 16 damage per hit.";
		}
		if(this.ID == "Frozen Breath"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordForgefire.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 21;
			this.value = 0;
			this.desc = "Cold to the touch, this blade delivers freezing arcing slices to opponents.";
			this.info = "This is an advanced weapon that does 21 damage per hit.";
		}
		if(this.ID == "Cobalt"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordCobalt.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 26;
			this.value = 0;
			this.desc = "Absurdly light, this off-colored blade still packs a large punch.";
			this.info = "This is an advanced weapon that does 26 damage per hit.";
		}
		if(this.ID == "Radiance"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Equip/swordDawn.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.damage = 31;
			this.value = 0;
			this.desc = "A shining blade that purges impurities from enemies and the wielder alike.";
			this.info = "This is an advanced weapon that does 31 damage per hit.";
		}
		this.boundBox = new Rectangle2D.Double(this.x, this.y, 64, 64);
	}
	
	/**
	 * Draws the Sword object to the screen using Render
	 * @param g
	 */
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
	}
	
	/**
	 * Returns the WIDTH of the image associated with Sword
	 * @return
	 */
	public int getWidth() {
		return WIDTH;
	}

	/**
	 * Returns the HEIGHT of the image associated with Sword
	 * @return
	 */
	public int getHeight() {
	 return HEIGHT;
	}	
	
	/**
	 * Returns the X location of the image associated with Sword
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * Sets the X location of the image associated with Sword
	 */
	public void setX(int x){
		this.x = x;
	}
	
	/**
	 * Returns the Y location of the image associated with Sword
	 */
	public int getY(){
		return this.y;
	}

	/** 
	 * Sets the Y location of the image associated with Sword
	 * @param y
	 */
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * Returns the damage value of the Sword
	 */
	public int getDamage(){
		return this.damage;
	}
	
	/**
	 * Returns the monetary value of the Sword
	 * @return
	 */
	public int getValue(){
		return this.value;
	}
	
	/**
	 * Returns the ID of Sword
	 * @return
	 */
	public String getID(){
		return this.ID;
	}
	
	/**
	 * Sets the ID of Sword
	 * @param ID
	 */
	public void setID(String ID){
		this.ID = ID;
	}
	
	/**
	 * Returns the description (flavor text) of Sword
	 * @return
	 */
	public String getDescription(){
		return this.desc;
	}
	
	/**
	 * Returns the numerical information of Sword
	 * @return
	 */
	public String getInfo(){
		return this.info;
	}
	
	/**
	 * Returns the image associated with Sword
	 */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**
	 * Sets the image associated with Sword
	 * @param image
	 */
	public void setImage(BufferedImage image){
		this.image = image;
	}
	
	/**
	 * Returns the boundbox of the image associated with Sword
	 * @return
	 */
	public Rectangle2D getBoundbox(){
		return this.boundBox;
	}
	
	/**
	 * Updates the boundbox of the image associated with Sword
	 */
	public void updateBoundbox(){
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
	}
}