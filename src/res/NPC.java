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
	public boolean gem = false;
	private Rectangle2D boundBox;
	private Rectangle2D smallBB;
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
			name = "Shopkeeper Anna";	
			dialogue[0] = "Grab the items you want. Point to learn more. Just make sure you have the coin.";
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
			dialogue[0] = "If you touch it, you buy it. Ask me about any item by pointing to it.";
			dialogue[1] = "Got a variety of sharp weapons and pointy sticks to jab your enemies with.";
			dialogue[2] = "Hey. You look like a weak little guy. Better arm yourself to stand a chance.";
		}
		if(ID == "armorsmith"){
			try {
				def = ImageIO.read(getClass().getClassLoader().getResource("Sprites/averaDef.png"));
				head = ImageIO.read(getClass().getClassLoader().getResource("Sprites/averaHead.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			speak = true;
			dialogueLines = 3;
			dialogue = new String[dialogueLines];
			name = "Armorsmith Avera";	
			dialogue[0] = "Don't wanna die? Give my wares a look. Click it to purchase.";
			dialogue[1] = "I make things.";
			dialogue[2] = "Hey. I mend things.";
		}
		if(ID == "stranger"){
			try {
				def = ImageIO.read(getClass().getClassLoader().getResource("Sprites/strangerDef.png"));
				head = ImageIO.read(getClass().getClassLoader().getResource("Sprites/strangerHead.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			speak = true;
			dialogueLines = 3;
			dialogue = new String[dialogueLines];
			name = "Shifty Stranger";	
			dialogue[0] = "Seriously - here take a soul gem. Tell nobody that I'm here. We both benefit, alright?";
			dialogue[1] = "Oh soul gems? I have a bunch of them. You... didn't tell anyone I'm here right?";
			dialogue[2] = "Uhhh - why are you here? I'm just... investigating and researching.";
		}
		this.setImage(0);
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		this.smallBB = new Rectangle2D.Double(this.x+13, this.y+8, WIDTH-30, HEIGHT-24);
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
	public Rectangle2D getSmall(){
		return this.smallBB;
	}
	public void updateBoundbox(){
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
	}
	public void updateSmall(){
		this.smallBB = new Rectangle2D.Double(this.x+13, this.y+8, WIDTH-30, HEIGHT-24);
	}
}