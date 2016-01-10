package res;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Update;

/**
 * Class defines a Dart object
 * @author Brian Chen
 *
 */
public class Dart implements Drawable{
	private int x, y, damage;
	private int direction;
	BufferedImage image;
	Rectangle2D boundBox;
	private boolean hit = false;
	private int vx, vy;
	private final int WIDTH = 0, HEIGHT = 0;
	private final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3, NULL = 4;
	public int v = 7;
	
	/**
	 * Creates a dart at location of X and Y, traveling at direction defined by where PC is facing
	 * @param x
	 * @param y
	 * @param direction
	 */
	public Dart(int x, int y, int direction) {
		if(Update.PC.q1) 
			v = 10;
		this.x = x;
		this.y = y;
		//Checks to see if damage buff upgrade has been obtained
		if(!Update.PC.q2)
			this.damage = (int)(Update.PC.getDamage()/2);
		else
			this.damage = (int)(Update.PC.getDamage());
		this.direction = direction;
		if(this.direction == NULL){
			this.hit = true;
		}
		//Direction, velocity, boundbox, and image defined for Dart
		if(this.direction == LEFT){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/ALE.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.y +=28;
			this.x -= 5;
			vx = -v;
			boundBox = new Rectangle2D.Double(this.x, this.y, 22, 9);
		}
		else if(this.direction == RIGHT){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/ARI.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.y +=28;
			this.x += 5;
			vx = v;
			boundBox = new Rectangle2D.Double(this.x, this.y, 22, 9);
		}
		else if(this.direction == UP){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/AUP.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.x += 21;
			this.y -= 5;
			vy = -v;
			boundBox = new Rectangle2D.Double(this.x, this.y, 9, 22);
		}
		else if(this.direction == DOWN){
			try {
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Sprites/Chrom/ADN.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.x += 21;
			this.y += 5;
			vy = v;
			boundBox = new Rectangle2D.Double(this.x, this.y, 9, 22);
		}
	}
	
	/**
	 * Draws the Dart to the screen using Render
	 * @param g
	 */
	public void draw(Graphics2D g) {
		g.drawImage(image, x, y, null);
	}
	
	/**
	 * Returns the WIDTH of the Dart
	 * @return
	 */
	public int getWidth() {
		return WIDTH;
	}
	
	/**
	 * Returns the HEIGHT of the Dart
	 * @return
	 */
	public int getHeight() {
	 return HEIGHT;
	}	
	
	/**
	 * Returns the Image of the Dart, dependent on the direction it is traveling
	 */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**
	 * Returns the X location of the Dart
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Returns the Y location of the dart
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Updates the X and Y locations and boundbox of the Dart
	 */
	public void update(){
		this.y = y+vy;
		this.x = x+vx;
		if(this.direction == LEFT || this.direction == RIGHT)
			boundBox = new Rectangle2D.Double(this.x, this.y, 25, 7);
		else if(this.direction == UP || this.direction == DOWN){
			boundBox = new Rectangle2D.Double(this.x, this.y, 7, 25);
		}
	}
	
	/**
	 * Updates if the Dart has hit an Enemy
	 * @param hit
	 */
	public void updateHit(boolean hit){
		this.hit = hit;
	}
	
	/**
	 * Returns if the Dart has hit an Enemy
	 * @return
	 */
	public boolean returnHit(){
		return this.hit;
	}
	
	/**
	 * Returns the Damage value of this Dart
	 * @return
	 */
	public int getDamage(){
		return this.damage;
	}
	
	/**
	 * Returns the boundary rectangle of this Dart
	 * @return
	 */
	public Rectangle2D getBoundbox(){
		return this.boundBox;
	}
}