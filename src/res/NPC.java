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
	private int xBoundaryLow;
	private int xBoundaryHigh;
	private int yBoundaryLow;
	private int yBoundaryHigh;
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
		this.xBoundaryLow = x - 40;
		this.xBoundaryHigh = x + 40;
		this.yBoundaryLow = y - 40;
		this.yBoundaryHigh = y + 40;
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
		if(ID == "magister"){
			try {
				def = ImageIO.read(getClass().getClassLoader().getResource("Sprites/philiaDef.png"));
				head = ImageIO.read(getClass().getClassLoader().getResource("Sprites/philiaHead.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			speak = true;
			dialogueLines = 3;
			dialogue = new String[dialogueLines];
			name = "Magister Philia";	
			dialogue[0] = "The city of Varrock cannot risk being subject to the evil powers at work here.";
			dialogue[1] = "On order of Crown Prince Joffrey, all citizens of Taverly are in quarantine.";
			dialogue[2] = "Hail, traveler. I'm afraid you can't get past this point.";
		}
		if(ID == "stranger"){
			try {
				def = ImageIO.read(getClass().getClassLoader().getResource("Sprites/strangerDef.png"));
				head = ImageIO.read(getClass().getClassLoader().getResource("Sprites/strangerHead.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			speak = true;
			dialogueLines = 6;
			dialogue = new String[dialogueLines];
			name = "Shifty Stranger";	
			dialogue[0] = "Excuse me, I've said too much. Please, don't mind me.";
			dialogue[1] = "Ugh - here. Take a soul gem. Tell nobody that I'm here. We'll both benefit, alright?";
			dialogue[2] = "Soul gems? I've got - Wait, you're not with the Arcane Council are you?";
			dialogue[3] = "Me? Haha, I'm just a... researcher is all. What are you all the way out here for?";
			dialogue[4] = "Oh damn, who are you?";
			dialogue[5] = "To trap a soul inside a gem would require a recently deceased human - ";
			if(Main.update.gem){
				updateLines();
			}
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
	public void updateBoundbox(){
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
	}
	public void updateSmall(){
		this.smallBB = new Rectangle2D.Double(this.x+13, this.y+8, WIDTH-30, HEIGHT-24);
	}
	public void updateLines(){
		if(ID == "stranger"){
			dialogueLines = 5;
			dialogue = new String[dialogueLines];
			dialogue[0] = "Excuse me, I've said too much. Please, don't mind me.";
			dialogue[1] = "Its unfair really. I mean - you bring ONE body back from the dead...";
			dialogue[2] = "To practice magic, I've got to stay hidden, see - I'm on the run from the Council.";
			dialogue[3] = "The darned Arcane Council revoked my permit for practicing magic a while back.";
			dialogue[4] = "Wondering why I'm out here?";
		}
	}
}