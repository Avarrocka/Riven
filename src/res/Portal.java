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
public class Portal implements Drawable{
	private String ID;
	private int x, y;
	private static final int WIDTH = 100, HEIGHT = 100;
	private static final int DEFAULT = 0, UP = 1, DOWN = 2, RIGHT = 3, LEFT = 4;
	private BufferedImage image;
	private BufferedImage head;
	private BufferedImage def, up, down, right, left;
	private Rectangle2D boundBox;
	/**
	 * Constructor. Creates a player character.
	 */
	public Portal(int x, int y, String ID) {
		this.setX(x);
		this.setY(y);
		try {
			def = ImageIO.read(getClass().getClassLoader().getResource("Sprites/annaDef.png"));
			head = ImageIO.read(getClass().getClassLoader().getResource("Sprites/annaHead.png"));
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
	
	public String getID(){
		return this.ID;
	}
	
	public void setImage(int face){
		this.image = def;
	}
	public void setHead(BufferedImage head){
		this.head = head;
	}
	public BufferedImage getHead(){
		return this.head;
	}
	public BufferedImage getImage() {
		return this.image;
	}
	public Rectangle2D getBoundbox(){
		return this.boundBox;
	}
	public void updateBoundbox(){
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
	}
}