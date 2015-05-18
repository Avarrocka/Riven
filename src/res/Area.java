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
import res.Portal;

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
	private LinkedList<Rectangle2D> leaveArea = new LinkedList<>();
	private LinkedList<String> leaveAreaName = new LinkedList<>();
	private LinkedList<Integer> moveDir = new LinkedList<>();
	private CollisionRects creator = new CollisionRects();
	private Portal portal;
	private boolean hasPortal = false;
	public final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public Area(String ID) {
		leaveArea.clear();
		leaveAreaName.clear();
		moveDir.clear();
		colli.clear();
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
			this.NPCs.add(new NPC(6, 700, "hunter"));
			leaveArea.add(new Rectangle2D.Double(0, 125, 10, 40));
			leaveAreaName.add("Turandal1");
			moveDir.add(LEFT);
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
			leaveArea.add(new Rectangle2D.Double(GraphicsMain.WIDTH-10, 125, 10, 40));
			leaveAreaName.add("Taverly");
			moveDir.add(RIGHT);
			leaveArea.add(new Rectangle2D.Double(0, 350, 10, 70));
			leaveAreaName.add("Turandal2");
			moveDir.add(LEFT);
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
			leaveArea.add(new Rectangle2D.Double(GraphicsMain.WIDTH-10, 350, 10, 70));
			leaveAreaName.add("Turandal1");
			moveDir.add(RIGHT);
			leaveArea.add(new Rectangle2D.Double(570, 20, 50, 9));
			leaveAreaName.add("RuinsofLargos");
			moveDir.add(UP);
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
			portal = new Portal(535, 220);
			this.hasPortal = true;
			leaveArea.add(new Rectangle2D.Double(570, GraphicsMain.HEIGHT-10, 50, 9));
			leaveAreaName.add("Turandal2");
			moveDir.add(DOWN);
		}
		else if(ID == "Frostgorge1"){
			this.name = "Frostgorge Sound";
			try {
				this.splash = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/iceRealmSplash.png"));
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/Frostgorge1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			portal = new Portal(50, 660);
			this.hasPortal = true;
			leaveArea.add(new Rectangle2D.Double(GraphicsMain.WIDTH-10, 200, 10, 70));
			leaveAreaName.add("Frostgorge2");
			moveDir.add(RIGHT);
		}
		else if(ID == "Frostgorge2"){
			this.name = "Frostgorge Sound";
			try {
				this.splash = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/iceRealmSplash.png"));
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/Frostgorge2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			leaveArea.add(new Rectangle2D.Double(10, 200, 10, 70));
			leaveAreaName.add("Frostgorge1");
			moveDir.add(LEFT);
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
	public LinkedList<Rectangle2D> getLeaveAreas(){
		return this.leaveArea;
	}
	public LinkedList<String> getLeaveAreaNames(){
		return this.leaveAreaName;
	}
	public LinkedList<Integer> getMoveDir(){
		return this.moveDir;
	}
	public LinkedList<NPC> getNPCs(){
		return this.NPCs;
	}
	public boolean hasPortal(){
		return this.hasPortal;
	}
	public Portal getPortal(){
		return portal;
	}
}