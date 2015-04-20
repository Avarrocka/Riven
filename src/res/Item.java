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
		if(this.ID == "cake"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Items/cake.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.heal = 40;
			this.value = 5;
		}
		if(this.ID == "fish"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Items/fish.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.heal = 20;
			this.value = 2;
		}
		if(this.ID == "hpPot"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Items/hpPot.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.heal = 10000;
			this.value = 20;
		}
		if(this.ID == "pie"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Items/pie.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.heal = 60;
			this.value = 7;
		}
		if(this.ID == "tele"){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Items/tele.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.value = 20;
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