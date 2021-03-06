package res;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Main;

/**
 * Class defines an Non-Player-Character object
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
	private static final int WIDTH = 56, HEIGHT = 64;
	private BufferedImage image;
	private BufferedImage head;
	private BufferedImage def;
	
	/**
	 * Creates a Non-Player Character at X and Y, defined by ID
	 * @param x
	 * @param y
	 * @param ID
	 */
	public NPC(int x, int y, String ID) {
		this.setX(x);
		this.setY(y);
		this.setID(ID);
		//Defines dialogue and other variables based on ID
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
		if(ID == "guard"){
			try {
				def = ImageIO.read(getClass().getClassLoader().getResource("Sprites/priamDef.png"));
				head = ImageIO.read(getClass().getClassLoader().getResource("Sprites/priamHead.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			speak = true;
			dialogueLines = 6;
			dialogue = new String[dialogueLines];
			name = "Guard Priam";	
			dialogue[0] = "Anyway... Just rough up about five oozes and I suppose I'll teach you a few tactics with the sword.";
			dialogue[1] = "Rewards? Well I don't suppose you'd accept half my sandwhich as payment? Haha, I'd never offer.";
			dialogue[2] = "If only someone *cough* could tidy up the numbers a bit around here, I could go off to lunch break.";
			dialogue[3] = "I mean what are they going to do? Slide around? Perhaps even slobber all over the cobblestone?";
			dialogue[4] = "Stuck here guarding this stupid gate all day. Against what? Oozes? Slimes?";
			dialogue[5] = "Out vile jelly! Away! I kid, really, my job is boring as a sack of stones. I mean, really -";
			if(Main.update.priamDone){
				updateLines();
			}
		}
		if(ID == "doomsayer"){
			try {
				def = ImageIO.read(getClass().getClassLoader().getResource("Sprites/tsumiDef.png"));
				head = ImageIO.read(getClass().getClassLoader().getResource("Sprites/tsumiHead.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			speak = true;
			dialogueLines = 6;
			dialogue = new String[dialogueLines];
			name = "Arcanist Tsumi";	
			dialogue[0] = "I was going to check it out, but right now I need to send a message to the council.";
			dialogue[1] = "I've heard adventurers talk about a ruined portal near the center of Turandal Woods.";
			dialogue[2] = "Rumors say that there's strange magic afoot. It's my job to investigate it.";
			dialogue[3] = "Ugh. You haven't heard of all the people disappearing? All the screams late at night?";
			dialogue[4] = "What do you mean you don't know what's happening? Have you been under a rock?";
			dialogue[5] = "I'm an arcanist with the Arcane Council of Varrock. I've been sent here to help.";
		}
		if(ID == "yenfay"){
			try {
				def = ImageIO.read(getClass().getClassLoader().getResource("Sprites/yenfayDef.png"));
				head = ImageIO.read(getClass().getClassLoader().getResource("Sprites/yenfayHead.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			speak = true;
			dialogueLines = 6;
			dialogue = new String[dialogueLines];
			name = "Guardian Yenfay";	
			dialogue[0] = "I'm looking to fix the portal. Can you help? I need a Soul Stone, to power the portal.";
			dialogue[1] = "He had this crazed look in his eyes. He kept muttering something about darkness and chaos.";
			dialogue[2] = "Several years ago, a travelling disheveled mage decided to sabotage the portal.";
			dialogue[3] = "these ruins. I protect, with my life, the ruined portal you see before you.";
			dialogue[4] = "You see before you the ruins of my once great House. I am tasked to stand guard over";
			dialogue[5] = "Greetings adventurer. I am Yenfay Largos, descendant of the noble Largos house.";
			if(Main.update.gem){
				updateLines();
			}
		}
		if(ID == "hunter"){
			try {
				def = ImageIO.read(getClass().getClassLoader().getResource("Sprites/hunterDef.png"));
				head = ImageIO.read(getClass().getClassLoader().getResource("Sprites/hunterHead.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			speak = true;
			dialogueLines = 6;
			dialogue = new String[dialogueLines];
			name = "Aria";	
			dialogue[0] = "Sure, they don't do much damage, but they do keep the monsters away";
			dialogue[1] = "When I see monsters, I just throw knives at it until they leave. I use [Q] to throw them.";
			dialogue[2] = "Recently, I've discovered a new way to keep safe. I can teach you too... I guess.";
			dialogue[3] = "I... I think it's better to keep your distance from them.";
			dialogue[4] = "Really, you shouldn't be fighting monsters. They're really quite dangerous...";
			dialogue[5] = "H-hey there. A-are you an adventurer?";
			if(Main.update.gem){
				updateLines();
			}
		}
		if(ID == "skiller"){
			try {
				def = ImageIO.read(getClass().getClassLoader().getResource("Sprites/tikiDef.png"));
				head = ImageIO.read(getClass().getClassLoader().getResource("Sprites/tikiHead.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			speak = true;
			dialogueLines = 5;
			dialogue = new String[dialogueLines];
			name = "Tiki the Trainer";	
			dialogue[0] = "The three branches govern your three abilities. Take a look!";
			dialogue[1] = "Master level traits require one of the previous Adept traits and cost 2 points.";
			dialogue[2] = "Adept level traits grant a permanent bonus to a skill. These cost only one point.";
			dialogue[3] = "Levels reward you with skill points. These points can be used to improve skills.";
			dialogue[4] = "Greetings adventurer. I'm here to hone your skills and refine them into art.";
		}
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		this.smallBB = new Rectangle2D.Double(this.x+13, this.y+8, WIDTH-30, HEIGHT-24);
		this.image = def;
	}

	/**
	 * Returns the X position of the NPC
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Sets the X position of the NPC
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the Y position of the NPC
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the Y position of the NPC
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Draws the NPC to the screen through Render
	 * @param g
	 */
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
	}
	
	/**
	 * Returns the WIDTH of the NPC
	 * @return
	 */
	public int getWidth() {
		return WIDTH;
	}

	/**
	 * Returns the HEIGHT of the NPC
	 * @return
	 */
	public int getHeight() {
	 return HEIGHT;
	}
	
	/**
	 * Checks if the NPC has Dialogue to impart
	 * @return
	 */
	public boolean hasDialogue(){
		return speak;
	}
	
	/**
	 * Returns the ID of the NPC
	 * @return
	 */
	public String getID(){
		return this.ID;
	}
	
	/**
	 * Sets the ID of the NPC
	 * @param ID
	 */
	public void setID(String ID){
		this.ID = ID;
	}
	
	/**
	 * Returns the Name of the NPC
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Returns the number of dialogue lines NPC has 
	 * @return
	 */
	public int getDialogueLines(){
		return this.dialogueLines;
	}
	
	/**
	 * Returns the dialogue at index of index for NPC
	 * @param index
	 * @return
	 */
	public String getPhrase(int index){
		return dialogue[index];
	}

	/**
	 * Returns the avatar head for NPC
	 * @return
	 */
	public BufferedImage getHead(){
		return this.head;
	}
	
	/**
	 * Sets the avatar head for NPC
	 * @param head
	 */
	public void setHead(BufferedImage head){
		this.head = head;
	}
	
	/**
	 * Returns the sprite image for NPC
	 */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**
	 * Returns the boundary box for NPC
	 * @return
	 */
	public Rectangle2D getBoundbox(){
		return this.boundBox;
	}
	
	/**
	 * Updates the boundbox for NPC
	 */
	public void updateBoundbox(){
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
	}
	
	/**
	 * Returns the collision rectangle for NPC
	 * @return
	 */
	public Rectangle2D getSmall(){
		return this.smallBB;
	}
	
	/**
	 * Updates the collision rectangle for NPC
	 */
	public void updateSmall(){
		this.smallBB = new Rectangle2D.Double(this.x+13, this.y+8, WIDTH-30, HEIGHT-24);
	}
	
	/**
	 * Updates the NPC's lines if quests are completed or other things
	 */
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
		if(ID == "guard" && Main.update.priamDone){
			dialogueLines = 2;
			dialogue = new String[dialogueLines];
			dialogue[0] = "Oh, right. Yeah, I'll teach you a few moves. Learn from the best, y'know?";
			dialogue[1] = "Hey. Thanks for clearing out some of the slimes. I can go out for lunch early!";
		}
		if(ID == "yenfay" && !Main.update.portalOnline){
			dialogueLines = 5;
			dialogue = new String[dialogueLines];
			dialogue[0] = "For your labors, I grant you full access to our portal and our training grounds.";
			dialogue[1] = "Where does the portal lead? To our ancient training grounds - the elemental caverns.";
			dialogue[2] = "Ugh... Aha - there, it should be done. That soul gem was potent indeed.";
			dialogue[3] = "Yes, of course it'll do. I'll get to fixing the portal immediately.";
			dialogue[4] = "Could it be...? A soul gem? I've never actually seen one with my own eyes!";
		}if(ID == "yenfay" && Main.update.portalOnline){
			dialogueLines = 2;
			dialogue = new String[dialogueLines];
			dialogue[0] = "For your labors, I grant you full access to our portal and our training grounds.";
			dialogue[1] = "Where does the portal lead? To our ancient training grounds - the elemental caverns.";
		}
		if(ID == "hunter"){
			dialogueLines = 1;
			dialogue = new String[dialogueLines];
			dialogue[0] = "Sure, they don't do much damage, but they do keep the monsters away.";
		}
	}
	/**
	 * Updates the NPC when map is changed
	 */
	public void onMapUpdate(){
		if(ID == "guard"){
			if(Main.update.priamDone){
				dialogueLines = 2;
				dialogue = new String[dialogueLines];
				dialogue[0] = "Oh, right. Yeah, I'll teach you a few moves. Learn from the best, y'know?";
				dialogue[1] = "Hey. Thanks for clearing out some of the slimes. I can go out for lunch early!";
			}
		}
	}
}