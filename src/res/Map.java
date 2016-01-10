package res;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import main.Main;

/**
 * Class defines an Map interface
 * @author Brian Chen
 *
 */
public class Map implements Drawable{
	private int x, y;
	private int curX, curY;
	private String desc;
	private static final int WIDTH = 100, HEIGHT = 100;
	private BufferedImage image, poi, curLoc;
	private LinkedList<Rectangle2D> POI = new LinkedList<>();
	private LinkedList<String> POINames = new LinkedList<>();
	private LinkedList<String> POIDesc = new LinkedList<>();
	
	/**
	 * Creates a Map object that directs the PC around the World. Spawns at X and Y location
	 * @param x
	 * @param y
	 */
	public Map(int x, int y) {
		try {
			this.image = ImageIO.read(getClass().getClassLoader().getResource("Icons/map.png"));
			this.poi = ImageIO.read(getClass().getClassLoader().getResource("Icons/POI.png"));
			this.curLoc = ImageIO.read(getClass().getClassLoader().getResource("Icons/curLoc.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
		//Adds locations and descriptions of these locations
		POI.add(new Rectangle2D.Double(310, 175, 20, 20));
		POINames.add("Frostgorge");
		POIDesc.add("A frozen isle dominated by ice elementals.");
		POI.add(new Rectangle2D.Double(555, 135, 20, 20));
		POINames.add("Balthazar");
		POIDesc.add("A lava isle that sputters with fiery wrath.");
		POI.add(new Rectangle2D.Double(740, 154, 20, 20));
		POINames.add("Kai Dhong");
		POIDesc.add("A massive temple unvieled by a giant fissure.");
		POI.add(new Rectangle2D.Double(630, 525, 20, 20));
		POINames.add("Taverly");
		POIDesc.add("A peaceful village - your hometown.");
		POI.add(new Rectangle2D.Double(455, 374, 20, 20));
		POINames.add("Ruins of Largos");
		POIDesc.add("A ruined temple that once housed an ancient artifact.");
		POI.add(new Rectangle2D.Double(470, 520, 20, 20));
		POINames.add("Turandal Forest");
		POIDesc.add("A lush forest that adorns the outskirts of Taverly.");
		POI.add(new Rectangle2D.Double(250, 360, 20, 20));
		POINames.add("Unknown");
		POIDesc.add("???");
		POI.add(new Rectangle2D.Double(705, 590, 20, 20));
		POINames.add("Road to Varrock");
		POIDesc.add("Road between Varrock and Taverly. Blocked.");
	}
	
	/**
	 * Draws the Map onto the screen using Render
	 * @param g
	 */
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
		for(int i = 0; i < POI.size(); i++){
			g.drawImage(poi, (int)POI.get(i).getX(), (int)POI.get(i).getY(), 20, 20, null);
			g.drawImage(curLoc, curX, curY, 20, 20, null);
		}
	}
	
	/**
	 * Returns the WIDTH of the Map object
	 * @return
	 */
	public int getWidth() {
		return WIDTH;
	}

	/**
	 * Returns the HEIGHT of the Map object
	 * @return
	 */
	public int getHeight() {
	 return HEIGHT;
	}	
	
	/**
	 * Returns the description of the latest hovered POI in Map
	 * @param i
	 * @return
	 */
	public String getDescription(){
		return this.desc;
	}
	
	/**
	 * Returns the Image of Map
	 */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**
	 * Returns the X location of Map
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Returns the Y location of Map
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Updates where the PC is on the Map, marked with a blue Star
	 */
	public void updateCurrentLocation(){
		for(int i = 0; i < POINames.size(); i++){
			if(Main.update.area.getName() == POINames.get(i)){
				curX = (int)POI.get(i).getX();
				curY = (int)POI.get(i).getY();
			}
		}
	}
	
	/**
	 * Checks for intersection of Mouse and POIs, and provides the Name of the Hovered Point of Interest
	 * @param p
	 * @return
	 */
	public String poiName(Point2D p){
		for(int i = 0; i < POI.size(); i++){
			if(POI.get(i).contains(p)){
				return POINames.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Checks for intersection of Mouse and POIs, and provides the Decription of the Hovered Point of Interest
	 * @param p
	 * @return
	 */
	public String poiDesc(Point2D p){
		for(int i = 0; i < POI.size(); i++){
			if(POI.get(i).contains(p)){
				return POIDesc.get(i);
			}
		}
		return null;
	}
}