package res;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
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
	private BufferedImage image, splash, entrance;
	private final int x=0, y=0;
	private final int WIDTH = GraphicsMain.WIDTH, HEIGHT = GraphicsMain.HEIGHT;
	private LinkedList<NPC> NPCs = new LinkedList<>();
	private LinkedList<String> availEnemy = new LinkedList<>();
	private LinkedList<Rectangle2D> colli = new LinkedList<>();
	private LinkedList<Rectangle2D> leaveArea = new LinkedList<>();
	private LinkedList<String> leaveAreaName = new LinkedList<>();
	private LinkedList<Integer> moveDir = new LinkedList<>();
	private LinkedList<Enemy> enemies = new LinkedList<>();
	private CollisionRects creator = new CollisionRects();
	private Portal portal;
	public Random RNG = new Random();
	private boolean hasPortal = false;
	private boolean hasBoss = false;
	private boolean bossSlain = false;
	private boolean spawnedMoreNPCs = false, spawnedAreas = false;
	public final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public Area(String ID) {
		leaveArea.clear();
		leaveAreaName.clear();
		moveDir.clear();
		colli.clear();
		Main.update.grapple = null;
		this.ID = ID;
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
			this.NPCs.add(new NPC(6, 700, "skiller"));
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
			this.availEnemy.add("slime");
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
			this.availEnemy.add("slime");
			this.availEnemy.add("gremlin");
			leaveArea.add(new Rectangle2D.Double(GraphicsMain.WIDTH-10, 350, 10, 70));
			leaveAreaName.add("Turandal1");
			moveDir.add(RIGHT);
			leaveArea.add(new Rectangle2D.Double(570, 20, 50, 9));
			leaveAreaName.add("RuinsofLargos");
			moveDir.add(UP);
			leaveArea.add(new Rectangle2D.Double(10, 350, 10, 300));
			leaveAreaName.add("Turandal3");
			moveDir.add(LEFT);
		}
		else if(ID == "Turandal3"){
			this.name = "Turandal Forest";
			try {
				this.splash = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/TurandalSplash.png"));
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/TurandalForest3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			leaveArea.add(new Rectangle2D.Double(GraphicsMain.WIDTH-10, 350, 10, 300));
			leaveAreaName.add("Turandal2");
			moveDir.add(RIGHT);
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
			this.availEnemy.add("snowman");
			this.availEnemy.add("aquaGoo");
			this.availEnemy.add("gremlin");
			portal = new Portal(50, 660);
			this.hasPortal = true;
			leaveArea.add(new Rectangle2D.Double(GraphicsMain.WIDTH-10, 215, 10, 152));
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
			leaveArea.add(new Rectangle2D.Double(865, 110, 60, 20));
			leaveAreaName.add("Frostgorge3");
			moveDir.add(UP);
		}
		else if(ID == "Frostgorge3"){
			this.name = "Frost Caverns";
			try {
				this.splash = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/frostSplash.png"));
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/Frostcave1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			leaveArea.add(new Rectangle2D.Double(442,753,110,5));
			leaveAreaName.add("Frostgorge2");
			moveDir.add(DOWN);
			leaveArea.add(new Rectangle2D.Double(425, 100, 80, 15));
			leaveAreaName.add("Frostgorge4");
			moveDir.add(UP);
		}
		else if(ID == "Frostgorge4"){
			this.name = "Frost Caverns";
			try {
				this.splash = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/frostSplash.png"));
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/Frostcave2.png"));
				this.entrance = ImageIO.read(getClass().getClassLoader().getResource("Icons/iceENT.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.hasBoss = true;
			leaveArea.add(new Rectangle2D.Double(380, 760, 100, 8));
			leaveAreaName.add("Frostgorge3");
			moveDir.add(DOWN);
		}
		else if(ID == "Frostgorge5"){
			this.name = "Frost Caverns";
			try {
				this.splash = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/frostSplash.png"));
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/Frostcave3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			leaveArea.add(new Rectangle2D.Double(0, 200, 10, 70));
			leaveAreaName.add("Frostgorge4");
			moveDir.add(UP);
		}
		else if(ID == "KaiDhong1"){
			this.name = "Kai Dhong";
			try {
				this.splash = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/earthRealmSplash.png"));
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/KaiDhong.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			portal = new Portal(50, 660);
			this.hasPortal = true;
			leaveArea.add(new Rectangle2D.Double(10, 200, 10, 70));
			leaveAreaName.add("Frostgorge1");
			moveDir.add(LEFT);
		}
		else if(ID == "Balthazar1"){
			this.name = "Balthazar Isle";
			try {
				this.splash = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/fireRealmSplash.png"));
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/Balthazar1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			portal = new Portal(50, 660);
			this.hasPortal = true;
			leaveArea.add(new Rectangle2D.Double(10, 200, 10, 70));
			leaveAreaName.add("Frostgorge1");
			moveDir.add(LEFT);
		}
		else if(ID == "Chaos1"){
			this.name = "Chaos Realm";
			try {
				this.splash = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/chaosRealmSplash.png"));
				this.image = ImageIO.read(getClass().getClassLoader().getResource("Backdrops/Chaos.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			portal = new Portal(50, 660);
			this.hasPortal = true;
			leaveArea.add(new Rectangle2D.Double(10, 200, 10, 70));
			leaveAreaName.add("Frostgorge1");
			moveDir.add(LEFT);
		}
		spawnCollisionRects(ID);
		if(this.hasBoss)
			spawnBoss();
	}
	public String getName(){
		return this.name;
	}
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
		if(this.ID == "Frostgorge4" && Main.update.frostBoss == true){
			g.drawImage(entrance, null, 450, 115);
			if(!spawnedAreas){
				leaveArea.add(new Rectangle2D.Double(450, 115, 100, 60));
				leaveAreaName.add("Frostgorge5");
				moveDir.add(DOWN);
				spawnedAreas = true;
			}
		}
		else if(this.ID == "Balthazar4" && Main.update.flameBoss == true){
			
		}
		else if(this.ID == "KaiDhong4" && Main.update.earthBoss == true){
			
		}
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
	public void spawnBoss(){	
		if(this.getID() == "Frostgorge4" && !Main.update.frostBoss){
			Main.update.enemies.add(new Enemy(500, 300, "squid"));
			System.out.print("SquidSpawnt");
			this.hasBoss = false;
		}
	}
	public void update(){
		if(this.hasBoss && this.bossSlain){
			if(this.ID == "Frostgorge4"){
				Main.update.frostBoss = true;
			}
			else if(this.ID == "Balthazar4"){
				Main.update.flameBoss = true;
			}
			else if(this.ID == "KaiDhong4"){
				Main.update.earthBoss = true;
			}
		}
		if(this.ID == "Turandal3" && Main.update.SWTCH && !spawnedMoreNPCs){
			this.NPCs.add(new NPC(560, 355, "stranger"));
			spawnedMoreNPCs = true;
		}
	}
	public Enemy spawnEnemy(){
		if(!(this.availEnemy.isEmpty())){
			int RNGINT = RNG.nextInt(this.availEnemy.size());
			String enemy = availEnemy.get(RNGINT);
			Enemy e = new Enemy(RNG.nextInt(1024), RNG.nextInt(700), enemy);
			Rectangle2D eBox = e.getBoundbox();
			boolean notInCorners = true;
			for(int i = 0; i < this.getCollisionRects().size(); i++){
				if(this.getCollisionRects().get(i).intersects(eBox)){
					notInCorners = false;
				}
			}
			if(notInCorners){
				return e;
			}
			else
				return this.spawnEnemy();
		}
		else
			return null;
	}
}