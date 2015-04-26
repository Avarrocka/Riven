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
	 * Constructor. Creates a player character.
	 */
	public Item(int x, int y, String ID) {
		this.setID(ID);
		this.setX(x);
		this.setY(y);
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
	public int getHeal(){
		return this.heal;
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