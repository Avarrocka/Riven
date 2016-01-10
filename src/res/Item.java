package res;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class defines an Item object
 * @author Brian Chen
 *
 */
public class Item implements Drawable{
	private String ID;
	private int x, y;
	private int heal;
	private int value;
	private String desc;
	private String info;
	private Rectangle2D boundBox;
	private static final int WIDTH = 100, HEIGHT = 100;
	private BufferedImage image;
	
	/**
	 * Creates an Item with the X and Y location values, with effects being based on ID of Item
	 * @param x
	 * @param y
	 * @param ID
	 */
	public Item(int x, int y, String ID) {
		this.setID(ID);
		this.setX(x);
		this.setY(y);
		//Defines characteristics of Item based on ID
		if(this.ID == "Chocolate Raspberry Cake"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Items/cake.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.heal = 40;
			this.value = 25;
			this.desc = "A cake. You can have it AND eat it too."; 
			this.info = "This is a food item that heals the user for 40HP after being consumed.";
		}
		if(this.ID == "Fish Steak"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Items/fish.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.heal = 20;
			this.value = 12;
			this.desc = "A freshly caught fish grilled to perfection and stuffed with sage and herbs.";
			this.info = "This is a food item that heals the user for 20HP after being consumed.";
		}
		if(this.ID == "Healing Salve"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Items/hpPot.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.heal = 10000;
			this.value = 30;
			this.desc = "A travelling apothecary said it has magical healing properties. Smells bad.";
			this.info = "This food item heals the user fully. However, it puts all skills on cooldown.";
		}
		if(this.ID == "Cinnamon Pumpkin Pie"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Items/pie.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.heal = 30;
			this.value = 15;
			this.desc = "Let them eat pie. Them being us, of course.";
			this.info = "This is a food item that heals the user for 30HP after being consumed.";
		}
		if(this.ID == "Teleport to Town"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Items/tele.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.value = 55;
			this.desc = "A magical tablet with a spell sealed inside. You can make out faint scratches resembling \"Taverly\"";
			this.info = "This utility item can be activated to teleport the user to Taverly.";
		}
		if(this.ID == "Soul Gem"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Items/soulGem.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.value = 0;
			this.desc = "A magical gem.";
			this.info = "This is a quest item that can be brought to the Portal Guardian to repair the portal.";
		}
		if(this.ID == "Water Rune"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Items/waterRune.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.value = 0;
			this.desc = "The tablet appears to be made out of the same material as the Portal. Perhaps..?";
			this.info = "A battered and old-looking tablet with runic sketchings of Water on it.";
		}
		this.boundBox = new Rectangle2D.Double(this.x, this.y, 64, 64);
	}
	
	/**
	 * Draws the item onto the screen using Render
	 * @param g
	 */
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
	}
	
	/**
	 * Returns the WIDTH of the Item
	 * @return
	 */
	public int getWidth() {
		return WIDTH;
	}

	/**
	 * Returns the HEIGHT of the Item
	 * @return
	 */
	public int getHeight() {
	 return HEIGHT;
	}	
	
	/**
	 * Returns the X location of the Item
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * Sets the X location of the Item
	 */
	public void setX(int x){
		this.x = x;
	}
	
	/**
	 * Returns the Y location of the Item
	 */
	public int getY(){
		return this.y;
	}
	
	/**
	 * Sets the Y value of the Item
	 * @param y
	 */
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * Returns the Healing value of the Item
	 * @return
	 */
	public int getHeal(){
		return this.heal;
	}
	
	/**
	 * Returns the monetary value of the Item
	 * @return
	 */
	public int getValue(){
		return this.value;
	}
	
	/**
	 * Returns the Item's ID
	 * @return
	 */
	public String getID(){
		return this.ID;
	}
	
	/**
	 * Sets the Item's ID
	 * @param ID
	 */
	public void setID(String ID){
		this.ID = ID;
	}
	
	/**
	 * Returns the Item's flavor text
	 * @return
	 */
	public String getDescription(){
		return this.desc;
	}
	
	/**
	 * Returns the Item's numerical description
	 * @return
	 */
	public String getInfo(){
		return this.info;
	}
	
	/**
	 * Returns the Image associated with Item
	 */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**
	 * Sets the Image associated with Item
	 * @param image
	 */
	public void setImage(BufferedImage image){
		this.image = image;
	}
	
	/**
	 * Returns the boundary rectangle of Item
	 * @return
	 */
	public Rectangle2D getBoundbox(){
		return this.boundBox;
	}
	
	/**
	 * Updates the boundary rectangle of Item
	 */
	public void updateBoundbox(){
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
	}
}