package res;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GraphicsMain;
import main.Main;
import res.Portal;

/**
 * Class defines an area, with graphics, location, and events. 
 * @author Brian Chen
 *
 */
public class Area implements Drawable{
	private String ID, name;
	private BufferedImage image, splash, entrance;
	private final int x=0, y=0;
	private LinkedList<NPC> NPCs = new LinkedList<>();
	private LinkedList<String> availEnemy = new LinkedList<>();
	private LinkedList<Rectangle2D> colli = new LinkedList<>();
	private LinkedList<Rectangle2D> leaveArea = new LinkedList<>();	//Leave Area collision box
	private LinkedList<String> leaveAreaName = new LinkedList<>();	//Leave Area ID
	private LinkedList<Integer> moveDir = new LinkedList<>();		//Leave Area direction (where PC appears afterwards)
	private CollisionRects creator = new CollisionRects();
	private Portal portal;
	public Random RNG = new Random();
	private boolean hasPortal = false;
	private boolean hasBoss = false;
	private boolean bossSlain = false;
	private boolean spawnedMoreNPCs = false, spawnedAreas = false;
	public final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	
	/**
	 * Creates an Area using the ID given, with Images, Potential Enemies, and other Assets
	 * @param ID Area ID, where the PC is.
	 */
	public Area(String ID) {
		//Clears all leftover information from previous Area
		leaveArea.clear();
		leaveAreaName.clear();
		moveDir.clear();
		colli.clear();
		Main.update.grapple = null;
		Main.update.enemies.clear();
		this.ID = ID;
		//Finds what assets to define based on Map name, as well as other variables and triggers
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
			leaveArea.add(new Rectangle2D.Double(10, 320, 10, 100));
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
			leaveArea.add(new Rectangle2D.Double(GraphicsMain.WIDTH-10, 350, 10, 50));
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
			this.availEnemy.add("snowman");
			this.availEnemy.add("aquaGoo");
			this.availEnemy.add("gremlin");
			leaveArea.add(new Rectangle2D.Double(10, 200, 10, 70));
			leaveAreaName.add("Frostgorge1");
			moveDir.add(LEFT);
			leaveArea.add(new Rectangle2D.Double(865, 30, 60, 10));
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
			this.availEnemy.add("snowman");
			this.availEnemy.add("aquaGoo");
			this.availEnemy.add("zombie");
			leaveArea.add(new Rectangle2D.Double(872,753,80,5));
			leaveAreaName.add("Frostgorge2");
			moveDir.add(DOWN);
			leaveArea.add(new Rectangle2D.Double(425, 20, 80, 15));
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
			leaveArea.add(new Rectangle2D.Double(475, 230, 70, 80));
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
		//Spawns Collision Rectangles dependent on the ID of this.Area
		spawnCollisionRects(ID); 
		//Spawns Boss of Area
		if(this.hasBoss)
			spawnBoss();
	}
	
	/**
	 * Returns the name of the Area. Name is the colloquial name, not ID
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Returns the ID of the Area. ID is the professional name, not Name
	 * @return
	 */
	public String getID(){
		return this.ID;
	}
	
	/**
	 * Draws the Area including any hidden entrances/assets
	 * @param g
	 */
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
	
	/**
	 * Returns the Image of this Area
	 */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**
	 * Returns the Splash Art of this Area
	 * @return
	 */
	public BufferedImage getSplash(){
		return this.splash;
	}
	
	/**
	 * Adds an NPC to the NPCs present in this Area
	 * @param npc
	 */
	public void addNPC(NPC npc){
		this.NPCs.add(npc);
	}
	
	/**
	 * Spawns the collision rectangles associated with the ID of this Area
	 * @param ID
	 */
	public void spawnCollisionRects(String ID){
		this.colli = creator.createRectangles(ID);
	}
	
	/**
	 * Returns the X value of this Area
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Returns the Y value of this Area
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Returns the collision rectangles of this Area
	 * @return
	 */
	public LinkedList<Rectangle2D> getCollisionRects(){
		return this.colli;
	}
	
	/**
	 * Returns the areas where PC can leave this Area
	 * @return
	 */
	public LinkedList<Rectangle2D> getLeaveAreas(){
		return this.leaveArea;
	}
	
	/**
	 * Returns the destinations of PC if they choose to leave Area
	 * @return
	 */
	public LinkedList<String> getLeaveAreaNames(){
		return this.leaveAreaName;
	}
	
	/**
	 * Returns which way the PC is traveling to leave Area
	 * @return
	 */
	public LinkedList<Integer> getMoveDir(){
		return this.moveDir;
	}
	
	/**
	 * Returns the NPCs in Area
	 * @return
	 */
	public LinkedList<NPC> getNPCs(){
		return this.NPCs;
	}
	
	/**
	 * Returns if Area contains a Portal Object
	 * @return
	 */
	public boolean hasPortal(){
		return this.hasPortal;
	}
	
	/**
	 * Returns the Portal Object
	 * @return
	 */
	public Portal getPortal(){
		return portal;
	}
	
	/**
	 * Adds a Boss Enemy to the Area
	 */
	public void spawnBoss(){	
		if(this.ID == "Frostgorge4" && Main.update.frostBoss == false){
			Main.update.enemies.add(new Enemy(500, 300, "squid"));
		}
	}
	
	/**
	 * Updates values in Area including boss status, NPCs, Enemies, etc.
	 */
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
	
	/**
	 * Spawns an Enemy based on available Enemies in this Area. Adds enemy to 
	 * @return
	 */
	public Enemy spawnEnemy(){
		if(!(this.availEnemy.isEmpty())){
			//Randomly generates ID of Enemy
			int RNGINT = RNG.nextInt(this.availEnemy.size());
			String enemy = availEnemy.get(RNGINT);
			Enemy e = new Enemy(RNG.nextInt(1024), RNG.nextInt(700), enemy);
			Rectangle2D eBox = e.getBoundbox();
			boolean notInCorners = true;
			//Checks to see Enemy doesn't spawn in an unreachable zone
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