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
 * Class defines an enemy object
 * @author Brian Chen
 *
 */
public class Area implements Drawable{
	private String ID, name;
	private BufferedImage image, splash;
	private final int x=0, y=0;
	private final int WIDTH = GraphicsMain.WIDTH, HEIGHT = GraphicsMain.HEIGHT;
	private LinkedList<NPC> NPCs = new LinkedList<>();
	private LinkedList<Rectangle2D> colli = new LinkedList<>();
	private CollisionRects creator = new CollisionRects();
	public Area(String ID) {
		if(ID == "Taverly"){
			this.name = "Taverly";
			try {
				this.splash = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/TaverlySplash.png"));
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/TaverlyTown.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.NPCs.add(new NPC(640, 180, "shop"));
			this.NPCs.add(new NPC(244 , 180, "blacksmith"));
			this.NPCs.add(new NPC(365, 180, "armorsmith"));
			this.NPCs.add(new NPC(5, 400, "doomsayer"));
		}
		else if(ID == "Turandal1"){
			this.name = "Turandal Forest";
			try {
				this.splash = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/TurandalSplash.png"));
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/TurandalForest1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.NPCs.add(new NPC(770, 80, "guard"));
		}
		else if(ID == "Turandal2"){
			this.name = "Turandal Forest";
			try {
				this.splash = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/TurandalSplash.png"));
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/TurandalForest2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.NPCs.add(new NPC(0, 450, "stranger"));
		}
		else if(ID == "RuinsofLargos"){
			this.name = "Ruins of Largos";
			try {
				this.splash = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/ruinsSplash.png"));
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/RuinsofLargos.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.NPCs.add(new NPC(400, 400, "yenfay"));
		}
		spawnCollisionRects(ID);
	}
	public String getName(){
		return this.name;
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
	public String getID(){
		return this.ID;
	}
	public BufferedImage getImage() {
		return this.image;
	}
	public BufferedImage getSplash(){
		return this.splash;
	}
	public void addNPC(NPC npc){
		this.NPCs.add(npc);
	}
	public void spawnCollisionRects(String ID){
		this.colli = creator.createRectangles(ID);
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public LinkedList<Rectangle2D> getCollisionRects(){
		return this.colli;
	}
	public LinkedList<NPC> getNPCs(){
		return this.NPCs;
	}
}