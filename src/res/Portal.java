package res;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.imageio.ImageIO;

import listeners.MousekeyListener;
import main.GraphicsMain;
import main.Main;
import main.Render;

/**
 * Class defines an enemy object
 * @author Brian Chen
 *
 */
public class Portal implements Drawable{
	private int x, y;
	private static final int WIDTH = 100, HEIGHT = 100;
	private static final int DEFAULT = 0, UP = 1, DOWN = 2, RIGHT = 3, LEFT = 4;
	private BufferedImage image;
	private BufferedImage fixed, broken, teleport, nochaos;
	private Rectangle2D boundBox;
	private LinkedList<Rectangle2D> places = new LinkedList<Rectangle2D>();
	private LinkedList<String> placeName = new LinkedList<String>();
	private Point2D p;
	/**
	 * Constructor. Creates a player character.
	 */
	public Portal(int x, int y) {
		this.setX(x);
		this.setY(y);
		try {
			nochaos = ImageIO.read(getClass().getClassLoader().getResource("Icons/nochaos.png"));
			teleport = ImageIO.read(getClass().getClassLoader().getResource("Icons/teleport.png"));
			broken = ImageIO.read(getClass().getClassLoader().getResource("Icons/brokenPortal.png"));
			fixed = ImageIO.read(getClass().getClassLoader().getResource("Icons/repairedPortal.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(Main.update.portalOnline){
			image = fixed;
		}
		else
			image = broken;
		this.boundBox = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		places.add(new Rectangle2D.Double(232, 132, 100, 100));
		placeName.add("Ruins of Largos");
		places.add(new Rectangle2D.Double(232, 232, 100, 100));
		placeName.add("Kai Dhong");
		places.add(new Rectangle2D.Double(232, 332, 100, 100));
		placeName.add("Jotunheim");
		places.add(new Rectangle2D.Double(232, 432, 100, 100));
		placeName.add("Muspelheim");
		places.add(new Rectangle2D.Double(232, 532, 100, 100));
		placeName.add("???");
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
	
	public void drawTeleport(Graphics2D g){
		g.drawImage(teleport, 200,  100, 620, 570, null);
		if(!(Main.update.ouroboros == 3)){
			g.drawImage(nochaos, 231, 532, 104, 95, null);
		}
		for(int i = 0; i < places.size(); i++){
			if(places.get(i).contains(p)){
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
				else if(placeName.get(i).equals("Muspelheim")){
					g.drawString("An isle of lava created by the eruption of an underwater volcano, Muspelheim", 350, 350);
					g.drawString("sputters with lava and flame constantly. Despite being challenged by the icy", 350, 370);
					g.drawString("waters around it, this isle still flows with fresh magma.", 350, 390);
					g.drawString("Encrusted by obsidian, Muspelheim is a sharp, jagged, and unforgiving", 350, 410);
					g.drawString("landscape. Recently, scouts have reported that the obsidian and lava seem to", 350, 430);
					g.drawString("have developed a will of their own. Shifting obsidian seems to have formed", 350, 450);
					g.drawString("a cavern in the heart of the isle. The island shifts as if inviting", 350, 470);
					g.drawString("adventurers in. ", 350, 490);
					g.drawString("RECOMMENDED LEVEL: 10", 380, 530);
				}
				else if(placeName.get(i).equals("Jotunheim")){
					g.drawString("An isle of lava created by the eruption of an underwater volcano, Muspelheim", 350, 350);
					g.drawString("sputters with lava and flame constantly. Despite being challenged by the icy", 350, 370);
					g.drawString("waters around it, this isle still flows with fresh magma.", 350, 390);
					g.drawString("Encrusted by obsidian, Muspelheim is a sharp, jagged, and unforgiving", 350, 410);
					g.drawString("landscape. Recently, scouts have reported that the obsidian and lava seem to", 350, 430);
					g.drawString("have developed a will of their own. Shifting obsidian seems to have formed", 350, 450);
					g.drawString("a cavern in the heart of the isle. The island shifts as if inviting", 350, 470);
					g.drawString("adventurers in. ", 350, 490);
					g.drawString("RECOMMENDED LEVEL: 15", 380, 530);
				}
				else if(placeName.get(i).equals("???")){
					g.drawString("This location does not seem to appear on the Codex of locations. Strange.", 350, 350);
					g.drawString("RECOMMENDED LEVEL: 20", 380, 530);
				}
			}
		}
	}
	
	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
	 return HEIGHT;
	}
	
	public void fix(){
		this.image = fixed;
	}
	
	public void teleport(){
		
	}
	
	public void setImage(BufferedImage face){
		this.image = face;
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
	
	public void updatePortal(){
		if(Main.update.portalOnline){
			image = fixed;
		}
		else
			image = broken;
		p = new Point2D.Double((int)MousekeyListener.getX(), (int)MousekeyListener.getY());
		System.out.println(p.getX() + ", " + p.getY());
	}
}