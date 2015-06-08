package res;

import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

/**
 * Class helps to create the necessary collision detection rectangles given an Area.
 * @author Brian Chen
 *
 */
public class CollisionRects{
	/**
	 * Creates a CollisionRectangle Creator Object that creates Collision Rectangles dependent on Area ID
	 */
	public CollisionRects() {
		
	}
	
	/**
	 * Creates collision rectangles (Boundaries) based on Area ID
	 * @param s Area ID
	 * @return
	 */
	public LinkedList<Rectangle2D> createRectangles(String s){
		LinkedList<Rectangle2D> rectangles = new LinkedList<Rectangle2D>();
		if(s == "Taverly"){
			rectangles.add(new Rectangle2D.Double(0, 25, 100, 100));
			rectangles.add(new Rectangle2D.Double(0, 195, 100, 70));
			rectangles.add(new Rectangle2D.Double(210, 25, 250, 170));
			rectangles.add(new Rectangle2D.Double(600, 105, 350, 85));
			rectangles.add(new Rectangle2D.Double(700, 380, 140, 165));
			rectangles.add(new Rectangle2D.Double(140, 355, 135, 145));
		}
		else if(s == "Turandal1"){
			rectangles.add(new Rectangle2D.Double(0, 0, 240, 216));
			rectangles.add(new Rectangle2D.Double(250, 0, 315, 70));
			rectangles.add(new Rectangle2D.Double(293, 75, 120, 110));
			rectangles.add(new Rectangle2D.Double(0, 510, 1024, 400));
		}
		else if(s == "Turandal2"){
			rectangles.add(new Rectangle2D.Double(0, 0, 280, 250));
			rectangles.add(new Rectangle2D.Double(280, 0, 260, 80));
			rectangles.add(new Rectangle2D.Double(640, 0, 250, 80));
			rectangles.add(new Rectangle2D.Double(900, 0, 100, 100));
			rectangles.add(new Rectangle2D.Double(0, 535, 610, 232));
		}
		else if(s == "Turandal3"){
			rectangles.add(new Rectangle2D.Double(0, 0, 1024, 160));
			rectangles.add(new Rectangle2D.Double(0, 530, 1024, 230));
			rectangles.add(new Rectangle2D.Double(420, 180, 100, 320));
		}
		else if(s == "RuinsofLargos"){
			rectangles.add(new Rectangle2D.Double(0, 0, 700, 280));
			rectangles.add(new Rectangle2D.Double(0, 270, 300, 290));
			rectangles.add(new Rectangle2D.Double(0, 560, 530, 200));
			rectangles.add(new Rectangle2D.Double(622, 517, 100, 100));
			rectangles.add(new Rectangle2D.Double(650, 640, 380, 125));
			rectangles.add(new Rectangle2D.Double(850, 0, 165, 768));
		}
		else if(s == "Frostgorge1"){
			
		}
		else if(s == "Frostgorge2"){
			
		}
		else if(s == "Frostgorge3"){
			
		}
		else if(s == "Frostgorge4"){
			
		}
		else if(s == "Frostgorge5"){
			
		}
		return rectangles;
	}
}