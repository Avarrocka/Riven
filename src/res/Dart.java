package res;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.imageio.ImageIO;

import main.GraphicsMain;
import main.Main;
import main.Render;

/**
 * Class defines a dart object, a projectile weapon created by the Player Character. Has variables such as damage, location etc.
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
	public Dart(int x, int y, int direction) {
		if(Main.update.PC.q1) 
			v = 10;
		this.x = x;
		this.y = y;
		if(!Main.update.PC.q2)
			this.damage = (int)(Main.update.PC.getDamage()/2);
		else
			this.damage = (int)(Main.update.PC.getDamage());
		this.direction = direction;
		if(this.direction == NULL){
			this.hit = true;
		}
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
	
	public void draw(Graphics2D g) {
		g.drawImage(image, x, y, null);
	}
	
	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
	 return HEIGHT;
	}	
	public BufferedImage getImage() {
		return this.image;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void update(){
		this.y = y+vy;
		this.x = x+vx;
		if(this.direction == LEFT || this.direction == RIGHT)
			boundBox = new Rectangle2D.Double(this.x, this.y, 25, 7);
		else if(this.direction == UP || this.direction == DOWN){
			boundBox = new Rectangle2D.Double(this.x, this.y, 7, 25);
		}
	}
	public void updateHit(boolean hit){
		this.hit = hit;
	}
	public boolean returnHit(){
		return this.hit;
	}
	public int getDamage(){
		return this.damage;
	}
	public Rectangle2D getBoundbox(){
		return this.boundBox;
	}
}