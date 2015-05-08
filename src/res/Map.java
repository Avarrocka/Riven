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
public class Map implements Drawable{
	private String ID;
	private int x, y;
	private int poix, poiy;
	private String desc;
	private static final int WIDTH = 100, HEIGHT = 100;
	private BufferedImage image, poi;
	private LinkedList<Rectangle2D> POI = new LinkedList<>();
	private LinkedList<String> POINames = new LinkedList<>();
	private LinkedList<String> POIDesc = new LinkedList<>();
	public Map(int x, int y) {
		try {
			this.image = ImageIO.read(getClass().getClassLoader().getResource("Icons/map.png"));
			this.poi = ImageIO.read(getClass().getClassLoader().getResource("Icons/POI.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
		POI.add(new Rectangle2D.Double(350, 175, 20, 20));
		POINames.add("Frozen Heath");
		POIDesc.add("A frozen isle dominated by ice elementals.");
		POI.add(new Rectangle2D.Double(625, 160, 20, 20));
		POINames.add("Taverly");
		POIDesc.add("Home sweet home.");
		POI.add(new Rectangle2D.Double(626, 285, 20, 20));
		POINames.add("Isle of Flame");
		POIDesc.add("This isle is constantly on the verge of exploding.");
		POI.add(new Rectangle2D.Double(660, 450, 20, 20));
		POINames.add("Rugged Fissure");
		POIDesc.add("A huge fissure which echoes with screams.");
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
		for(int i = 0; i < POI.size(); i++){
			g.drawImage(poi, (int)POI.get(i).getX(), (int)POI.get(i).getY(), 20, 20, null);
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
	public String getDescription(int i){
		return this.desc;
	}
	public BufferedImage getImage() {
		return this.image;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getPOIX(){
		return this.poix;
	}
	public int getPOIY(){
		return this.poiy;
	}
	public String poiName(Point2D p){
		for(int i = 0; i < POI.size(); i++){
			if(POI.get(i).contains(p)){
				this.poix = (int)POI.get(i).getX() + 25;
				this.poiy = (int)POI.get(i).getY();
				return POINames.get(i);
			}
		}
		return null;
	}
	public String poiDesc(Point2D p){
		for(int i = 0; i < POI.size(); i++){
			if(POI.get(i).contains(p)){
				return POIDesc.get(i);
			}
		}
		return null;
	}
}