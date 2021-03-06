package res;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import listeners.MousekeyListener;
import main.Main;
import main.Update;

/**
 * Class defines an Portal object
 * @author Brian Chen
 *
 */
public class Portal implements Drawable{
	private int x, y;
	private static final int WIDTH = 100, HEIGHT = 100;
	private BufferedImage image;
	private BufferedImage fixed, broken, teleport, nochaos;
	private Rectangle2D boundBox;
	private LinkedList<Rectangle2D> places = new LinkedList<Rectangle2D>();	//Destinations
	private LinkedList<String> placeName = new LinkedList<String>();		//Destination Names
	private LinkedList<String> placeNames = new LinkedList<String>();		//Destination ID
	private Point2D p;
	/**
	 * Constructor. Creates a player character.
	 */
	public Portal(int x, int y) {
		this.setX(x);
		this.setY(y);
		//Images for Portal
		try {
			nochaos = ImageIO.read(getClass().getClassLoader().getResource("Icons/nochaos.png"));
			teleport = ImageIO.read(getClass().getClassLoader().getResource("Icons/teleport.png"));
			broken = ImageIO.read(getClass().getClassLoader().getResource("Icons/brokenPortal.png"));
			fixed = ImageIO.read(getClass().getClassLoader().getResource("Icons/repairedPortal.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Checks to see if prerequisite Quest is complete
		if(Main.update.portalOnline){
			image = fixed;
		}
		else
			image = broken;
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		places.add(new Rectangle2D.Double(232, 132, 100, 100));
		placeName.add("Ruins of Largos");
		placeNames.add("RuinsofLargos");
		places.add(new Rectangle2D.Double(232, 232, 100, 100));
		placeName.add("Kai Dhong");
		placeNames.add("KaiDhong1");
		places.add(new Rectangle2D.Double(232, 332, 100, 100));
		placeName.add("Frostgorge");
		placeNames.add("Frostgorge1");
		places.add(new Rectangle2D.Double(232, 432, 100, 100));
		placeName.add("Balthazar");
		placeNames.add("Balthazar1");
		places.add(new Rectangle2D.Double(232, 532, 100, 100));
		placeName.add("???");
		placeNames.add("Chaos1");
	}

	/**
	 * Returns the X location of Portal object
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets X location of Portal object
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the Y location of Portal object
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets Y location of Portal object
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Draws portal object to the screen, using Render
	 * @param g
	 */
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
	}
	
	/**
	 * Draws the teleport menu to the screen, using Render
	 * @param g
	 */
	public void drawTeleport(Graphics2D g){
		//Code that is yet to be implemented regarding final stage
		g.drawImage(teleport, 200,  100, 620, 570, null);
		if(!(Main.update.ouroboros == 3)){
			g.drawImage(nochaos, 231, 532, 104, 95, null);
		}
		//Checks mouse collision with Destination list, and displays flavor text/info
		for(int i = 0; i < places.size(); i++){
			if(!(places.isEmpty()) && !(p == null)){
				if(places.get(i).contains(p)){
					if(MousekeyListener.mouseClicked){
						if(Main.update.area.getID() != placeNames.get(i)){
							Main.update.area = new Area(placeNames.get(i));
							Update.PC.setX(Main.update.area.getPortal().getX());
							Update.PC.setY(Main.update.area.getPortal().getY());
							Main.update.splashScreenTime = 100;
						}
					}
					g.setFont(new Font("Arial", Font.BOLD, 32));
					g.drawString(placeName.get(i), 350, 300);
					g.setFont(new Font("Arial", Font.PLAIN, 13));
					if(placeName.get(i).equals("Ruins of Largos")){
						g.drawString("Timeless architecture build long ago by the Mursaat, the noble temple", 350, 350);
						g.drawString("of Largos was created to house a powerful ancient artifact - the Gate.", 350, 370);
						g.drawString("The Gate, powered by a single pure Soul Gem, focuses unimaginable", 350, 390);
						g.drawString("amounts of power into the user, effectively teleporting their essence ", 350, 410);
						g.drawString("to different locations in the world.", 350, 430);
						g.drawString("During the Avarrockian revolution, however, the temple was ransacked and", 350, 450);
						g.drawString("the portal partially destroyed in the process. Now, a hero has restored the", 350, 470);
						g.drawString("portal to functionality. It functions as a teleporter to specific locations.", 350, 490);
						g.drawString("RECOMMENDED LEVEL: --", 380, 530);
					}
					else if(placeName.get(i).equals("Kai Dhong")){
						g.drawString("The desert landscape surrounding the oasis city of Dhong was arid and", 350, 350);
						g.drawString("lifeless. Shifting sands streched for as far as the eye could see, with the", 350, 370);
						g.drawString("only visible structures for miles being the dunes of red sand.", 350, 390);
						g.drawString("Several shamans, resolving to finally bring fertility to the lands of Dhong,", 350, 410);
						g.drawString("performed an unknown ritual deep in the heart of Dhong. The ritual went", 350, 430);
						g.drawString("awry, and a massive fissure instantly swalled the entire city of Dhong.", 350, 450);
						g.drawString("Legends tell of the forsaken spirits that haunt the fissure, constantly", 350, 470);
						g.drawString("looking to extract their vengence on any unsuspecting adventurers.", 350, 490);
						g.drawString("RECOMMENDED LEVEL: 05", 380, 530);
					}
					else if(placeName.get(i).equals("Frostgorge")){
						g.drawString("Carved from the heart of an ancient glacier, and enchanted by a mystical", 350, 350);
						g.drawString("warlock, Frostgorge is a labyrinth of ice and frost.", 350, 370);
						g.drawString("The isle, floating on water, glides in and out of sight periodically,", 350, 390);
						g.drawString("giving it the illuision of a mirage isle. It has been rumored that many", 350, 410);
						g.drawString("pirates have used this isle to stash their treasure, being so obscure and", 350, 430);
						g.drawString("out of the way. However, sources fail to mention that no pirate has ever", 350, 450);
						g.drawString("been able to claim their treasure again. Whatever lurks in the depths of", 350, 470);
						g.drawString("this glacier is ancient and powerful.", 350, 490);
						g.drawString("RECOMMENDED LEVEL: 10", 380, 530);
					}
					else if(placeName.get(i).equals("Balthazar")){
						g.drawString("An isle of molten fire created by the eruption of an underwater volcano, it", 350, 350);
						g.drawString("sputters constantly with lava and flame. Despite being challenged by the icy", 350, 370);
						g.drawString("waters around it, this isle still flows with fresh magma.", 350, 390);
						g.drawString("Encrusted by obsidian, Balthazar isle is a sharp, jagged, and unforgiving", 350, 410);
						g.drawString("landscape. Recently, scouts have reported that the obsidian and lava seem", 350, 430);
						g.drawString("to have developed a will of their own. Shifting obsidian seems to have", 350, 450);
						g.drawString("formed a cavern in the heart of the isle. The island itself moves as if ", 350, 470);
						g.drawString("inviting adventurers in. ", 350, 490);
						g.drawString("RECOMMENDED LEVEL: 15", 380, 530);
					}
					else if(placeName.get(i).equals("???")){
						g.drawString("This location does not seem to appear on the Codex of locations. Strange.", 350, 350);
						g.drawString("RECOMMENDED LEVEL: 20", 380, 530);
					}
				}
			}
		}
	}
	
	/**
	 * Returns WIDTH of Portal object
	 * @return
	 */
	public int getWidth() {
		return WIDTH;
	}

	/**
	 * Returns HEIGHT of Portal object
	 * @return
	 */
	public int getHeight() {
	 return HEIGHT;
	}
	
	/**
	 * Sets image of Portal to parameter
	 * @param face
	 */
	public void setImage(BufferedImage face){
		this.image = face;
	}
	
	/**
	 * Returns the image of Portal
	 */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**
	 * Returns the boundbox of Portal
	 * @return
	 */
	public Rectangle2D getBoundbox(){
		return this.boundBox;
	}
	
	/**
	 * Updates the boundbox of Portal
	 */
	public void updateBoundbox(){
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
	}
	
	/**
	 * Checks the quest completion to determine image of portal
	 */
	public void updatePortal(){
		if(Main.update.portalOnline){
			image = fixed;
		}
		else
			image = broken;
		p = new Point2D.Double((int)MousekeyListener.getX(), (int)MousekeyListener.getY());
	}
}