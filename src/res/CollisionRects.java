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
			rectangles.add(new Rectangle2D.Double(0, 0, 337, 212));
			rectangles.add(new Rectangle2D.Double(0, 212, 200, 150));
			rectangles.add(new Rectangle2D.Double(575, 0, 500, 75));
			rectangles.add(new Rectangle2D.Double(920, 75, 100, 100));
			rectangles.add(new Rectangle2D.Double(620, 400, 400, 300));
			rectangles.add(new Rectangle2D.Double(640, 300, 100, 100));
		}
		else if(s == "Frostgorge2"){
			rectangles.add(new Rectangle2D.Double(0, 0, 860, 120));
			rectangles.add(new Rectangle2D.Double(930, 0, 100, 120));
			rectangles.add(new Rectangle2D.Double(215, 280, 808, 420));
			rectangles.add(new Rectangle2D.Double(0, 350, 210, 343));
		}
		else if(s == "Frostgorge3"){
			rectangles.add(new Rectangle2D.Double(100, 180, 110, 180));
			rectangles.add(new Rectangle2D.Double(725, 190, 160, 130));
			rectangles.add(new Rectangle2D.Double(0, 0, 410, 65));
			rectangles.add(new Rectangle2D.Double(530, 0, 500, 65));
			rectangles.add(new Rectangle2D.Double(100, 630, 60, 50));
		}
		else if(s == "Frostgorge4"){
			rectangles.add(new Rectangle2D.Double(0, 710, 350, 50));
			rectangles.add(new Rectangle2D.Double(530, 710, 490, 55));
			rectangles.add(new Rectangle2D.Double(0, 0, 400, 170));
			rectangles.add(new Rectangle2D.Double(400, 0, 624, 110));
			rectangles.add(new Rectangle2D.Double(600, 110, 420, 90));
			rectangles.add(new Rectangle2D.Double(980, 200, 43, 512));
			rectangles.add(new Rectangle2D.Double(0, 170, 54, 539));
		}
		else if(s == "Frostgorge5"){
			rectangles.add(new Rectangle2D.Double(0, 0, 440, 160));
			rectangles.add(new Rectangle2D.Double(550, 0, 470, 160));
			rectangles.add(new Rectangle2D.Double(820, 160, 200, 600));
			rectangles.add(new Rectangle2D.Double(0, 160, 160, 607));
			rectangles.add(new Rectangle2D.Double(160, 680, 660, 95));
			rectangles.add(new Rectangle2D.Double(700, 215, 70, 50));
			rectangles.add(new Rectangle2D.Double(215, 535, 70, 50));
		}
		return rectangles;
	}
}