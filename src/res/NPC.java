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
public class NPC implements Drawable{
	private String ID;
	private String name;
	private String[] dialogue;
	private boolean speak;
	private Rectangle2D boundBox;
	private int dialogueLines;
	private int x, y;
	private int Vx, Vy;
	private static final int WIDTH = 56, HEIGHT = 64;
	private static final int DEFAULT = 0, UP = 1, DOWN = 2, RIGHT = 3, LEFT = 4;
	private BufferedImage image;
	private BufferedImage head;
	private BufferedImage def, up, down, right, left;
	/**
	 * Constructor. Creates a player character.
	 */
	public NPC(int x, int y, String ID) {
		this.setX(x);
		this.setY(y);
		this.setXvelocity(0);
		this.setYvelocity(0);
		this.setID(ID);
		if(ID == "shop"){
			try {
				def = ImageIO.read(getClass().getClassLoader().getResource("Sprites/annaDef.png"));
				head = ImageIO.read(getClass().getClassLoader().getResource("Sprites/annaHead.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			speak = true;
			dialogueLines = 3;
			dialogue = new String[dialogueLines];
			name = "Shopkeep Anna";	
			dialogue[0] = "Click to buy an item. Just make sure you have the coin.";
			dialogue[1] = "If you need anything to help with your journey - potions, food, I have it all.";
			dialogue[2] = "Hiya! I'm Anna - the local shopkeep around these parts.";
		}
		if(ID == "blacksmith"){
			try {
				def = ImageIO.read(getClass().getClassLoader().getResource("Sprites/uldredDef.png"));
				head = ImageIO.read(getClass().getClassLoader().getResource("Sprites/uldredHead.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			speak = true;
			dialogueLines = 3;
			dialogue = new String[dialogueLines];
			name = "Blacksmith Uldred";	
			dialogue[0] = "Don't wanna die? Give my wares a look. Click it to purchase.";
			dialogue[1] = "I make things.";
			dialogue[2] = "Hey. I mend things.";
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
	
	public int getXvelocity() {
		return this.Vx;
	}
	
	public int getYvelocity() {
		return this.Vy;
	}
	
	public void setXvelocity(int Vx) {
		this.Vx = Vx;
	}

	public void setYvelocity(int Vy) {
		this.Vy = Vy;
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
	
	public boolean hasDialogue(){
		return speak;
	}
	
	public void setID(String ID){
		this.ID = ID;
	}
	
	public String getID(){
		return this.ID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getDialogueLines(){
		return this.dialogueLines;
	}
	
	public String getPhrase(int index){
		return dialogue[index];
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