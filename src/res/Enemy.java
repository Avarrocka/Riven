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
	private int damage;
	private int motionSpeed = 21;
	private static final int DEFAULT = 0, UP = 1, DOWN = 2, RIGHT = 3, LEFT = 4;
	private BufferedImage image;
	private BufferedImage movement[];
	public boolean revMov = false;
	/**
	 * Constructor. Creates a player character.
	 */
	public Enemy(int x, int y, String ID) {
		this.setX(x);
		this.setY(y);
		this.setID(ID);
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
			this.health = 30;
			this.damage = 4;
		}
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		this.smallBB = new Rectangle2D.Double(this.x+7, this.y+8, WIDTH-25, HEIGHT-24);
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
		motionSpeed--;
	}
}