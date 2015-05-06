package res;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GraphicsMain;
import main.Main;
import main.Render;

/**
 * Class defines an enemy object
 * @author Brian Chen
 *
 */
public class CollisionRects{
	private String ID;
	/**
	 * Constructor. Creates a player character.
	 */
	public CollisionRects() {
		
	}

	public LinkedList<Rectangle2D> createRectangles(String s){
		LinkedList<Rectangle2D> rectangles = new LinkedList<Rectangle2D>();
		if(s == "Turandal1"){
			rectangles.add(new Rectangle2D.Double(0, 0, 240, 216));
			rectangles.add(new Rectangle2D.Double(250, 0, 315, 70));
			rectangles.add(new Rectangle2D.Double(293, 75, 120, 110));
			rectangles.add(new Rectangle2D.Double(0, 510, 1024, 400));
		}
		else if(s == "Taverly"){
			rectangles.add(new Rectangle2D.Double(0, 25, 100, 100));
			rectangles.add(new Rectangle2D.Double(0, 195, 100, 70));
			rectangles.add(new Rectangle2D.Double(210, 25, 250, 170));
			rectangles.add(new Rectangle2D.Double(600, 105, 350, 85));
			rectangles.add(new Rectangle2D.Double(700, 380, 140, 165));
			rectangles.add(new Rectangle2D.Double(140, 355, 135, 145));
		}
		return rectangles;
	}
}