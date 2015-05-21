package res;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.imageio.ImageIO;

import listeners.MousekeyListener;
import main.GraphicsMain;
import main.Main;
import main.Render;
import res.Portal;

/**
 * Class defines an enemy object
 * @author Brian Chen
 *
 */
public class SkillTree implements Drawable{
	private BufferedImage image, obtained, lookingAt;
	private int points;
	private LinkedList<Rectangle2D> collision = new LinkedList<>();
	private LinkedList<String> name = new LinkedList<>();
	private LinkedList<String> desc = new LinkedList<>();
	private LinkedList<Boolean> got = new LinkedList<>();
	private static final int WIDTH = 500, HEIGHT = 550, x = 300, y = 25;
	public SkillTree() {
		try {
			this.image = ImageIO.read(getClass().getClassLoader().getResource("UI/skillTree.png"));
			this.obtained = ImageIO.read(getClass().getClassLoader().getResource("UI/obtained.png"));
			this.lookingAt = ImageIO.read(getClass().getClassLoader().getResource("UI/lookingAt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < 9; i++){
			got.add(false);
		}
		collision.add(new Rectangle2D.Double(332, 80, 71, 71));
		collision.add(new Rectangle2D.Double(444, 109, 71, 71));
		collision.add(new Rectangle2D.Double(369, 194, 71, 71));
		name.add("Hidden Blade Mastery");
		name.add("Flashing Blade");
		name.add("Piercing Strike");
		desc.add("Mastery of daggers allows you to reduce the cooldown by 30%");
		desc.add("Agile hands allows the daggers to travel 25% faster");
		desc.add("A more forceful throw allows daggers to pierce and deal 100% of weapon damage");
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.PLAIN, 15));
		g.drawString(points+"", 500, 500);
		int x = MousekeyListener.getX();
		int y = MousekeyListener.getY();
		for(int i = 0; i < collision.size(); i++){
			if(got.get(i)){
				g.drawImage(obtained, (int)collision.get(i).getX(), (int)collision.get(i).getY(), null);
			}
		}
		Point2D p = new Point2D.Double((int)x, (int)y);
		for(int i = 0; i < collision.size(); i++){
			if(collision.get(i).contains(p)){
				g.drawImage(lookingAt, (int)collision.get(i).getX(), (int)collision.get(i).getY(), null);
				g.drawString(name.get(i), 330, 660);
				g.drawString(desc.get(i), 330, 680);
				if(MousekeyListener.mouseClicked && !got.get(i) && this.points >= 1 && (i!= 0) && (i!= 3) && (i!=6)){
					got.remove(i);
					got.add(i, true);
					Main.update.PC.setPoints(Main.update.PC.getPoints()-1);
				}
				if(MousekeyListener.mouseClicked && !got.get(i) && this.points >= 2 && (i == 0 || i == 3 || i == 6)){
					if(i == 0){
						if(got.get(1) || got.get(2)){
							got.remove(i);
							got.add(i, true);
							Main.update.PC.setPoints(Main.update.PC.getPoints()-2);
						}
					}
					if(i == 3){
						if(got.get(4) || got.get(5)){
							got.remove(i);
							got.add(i, true);
							Main.update.PC.setPoints(Main.update.PC.getPoints()-2);
						}
					}
					if(i == 6){
						if(got.get(7) || got.get(8)){
							got.remove(i);
							got.add(i, true);
							Main.update.PC.setPoints(Main.update.PC.getPoints()-2);
						}
					}
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
	public BufferedImage getImage() {
		return this.image;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void updatePoints(){
		this.points = Main.update.PC.getPoints();
	}
}