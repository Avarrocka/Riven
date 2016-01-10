package res;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import listeners.MousekeyListener;
import main.Update;

/**
 * Class defines an skill tree interface
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
		//Offense Tree
		collision.add(new Rectangle2D.Double(332, 80, 71, 71));
		collision.add(new Rectangle2D.Double(444, 109, 71, 71));
		collision.add(new Rectangle2D.Double(369, 194, 71, 71));
		name.add("[Master] Mastery of Agility");
		name.add("[Adept] Flashing Blade");
		name.add("[Adept] Piercing Strike");
		desc.add("Dexterous movements reduce the cooldown of a dagger throw by 30%.");
		desc.add("A well balanced throw allows the dagger to travel 25% faster.");
		desc.add("A more forceful throw allows daggers to deal 100% weapon damage instead of 50%.");
		//Utility Tree
		collision.add(new Rectangle2D.Double(696, 79, 71, 71));
		collision.add(new Rectangle2D.Double(585, 110, 71, 71));
		collision.add(new Rectangle2D.Double(670, 200, 71, 71));
		name.add("[Master] Mastery of Strength");
		name.add("[Adept] Reinforced Chains");
		name.add("[Adept] Sharpened Hook");
		desc.add("Strength training allows the user to throw out another hook 30% faster.");
		desc.add("Improved chains constrain monsters, rendering them unable to attack for a while.");
		desc.add("Sharpened hooks slice monsters, dealing 300% weapon damage instead of 100%.");
		//Defense Tree
		collision.add(new Rectangle2D.Double(517, 420, 71, 71));
		collision.add(new Rectangle2D.Double(440, 326, 71, 71));
		collision.add(new Rectangle2D.Double(597, 325, 71, 71));
		name.add("[Master] Mastery of Resolve");
		name.add("[Adept] Pacifist");
		name.add("[Adept] Renewed Focus");
		desc.add("A sharpened mind allows the user to meditate up to 30% more often.");
		desc.add("Transcending into an ethereal plane, the user is invulnerable during meditation.");
		desc.add("Deep focus during meditation allows the user to heal 50% of Max HP up from 30%.");
	}
	
	/**
	 * Draws the interface for Skill Tree
	 * @param g
	 */
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString(points+"", 545, 292);
		int mx = MousekeyListener.getX();
		int my = MousekeyListener.getY();
		//Checks to see if skill perk is obtained, and highlights it
		for(int i = 0; i < collision.size(); i++){
			if(got.get(i)){
				g.drawImage(obtained, (int)collision.get(i).getX(), (int)collision.get(i).getY(), null);
			}
		}
		//Hovering over skill perks draws an aura and writes description of skill perk
		Point2D p = new Point2D.Double((int)mx, (int)my);
		for(int i = 0; i < collision.size(); i++){
			if(collision.get(i).contains(p)){
				g.drawImage(lookingAt, (int)collision.get(i).getX(), (int)collision.get(i).getY(), null);
				g.setFont(new Font("Arial", Font.PLAIN, 16));
				g.drawString(name.get(i), 320, 540);
				g.setFont(new Font("Arial", Font.PLAIN, 12));
				g.drawString(desc.get(i), 320, 555);
				if(MousekeyListener.mouseClicked && !got.get(i) && this.points >= 1 && (i!= 0) && (i!= 3) && (i!=6)){
					got.remove(i);
					got.add(i, true);
					Update.PC.setPoints(Update.PC.getPoints()-1);
					if(i == 1)
						Update.PC.q1 = true;
					else if(i == 2)
						Update.PC.q2 = true;
					else if(i == 4)
						Update.PC.e1 = true;
					else if(i == 5)
						Update.PC.e2 = true;
					else if(i == 7)
						Update.PC.w1 = true;
					else if(i == 8)
						Update.PC.w2 = true;
				}
				if(MousekeyListener.mouseClicked && !got.get(i) && this.points >= 2 && (i == 0 || i == 3 || i == 6)){
					if(i == 0){
						if(got.get(1) || got.get(2)){
							got.remove(i);
							got.add(i, true);
							Update.PC.q3 = true;
							Update.PC.setPoints(Update.PC.getPoints()-2);
						}
					}
					if(i == 3){
						if(got.get(4) || got.get(5)){
							got.remove(i);
							got.add(i, true);
							Update.PC.e3 = true;
							Update.PC.setPoints(Update.PC.getPoints()-2);
						}
					}
					if(i == 6){
						if(got.get(7) || got.get(8)){
							got.remove(i);
							got.add(i, true);
							Update.PC.w3 = true;
							Update.PC.setPoints(Update.PC.getPoints()-2);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Returns WIDTH of Skill Tree Interface
	 * @return
	 */
	public int getWidth() {
		return WIDTH;
	}

	/**
	 * Returns HEIGHT of Skill Tree Interface
	 * @return
	 */
	public int getHeight() {
	 return HEIGHT;
	}	
	
	/**
	 * Returns Image of Skill Tree
	 */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**
	 * Returns X Location of Skill Tree
	 */
	public int getX() {
		return SkillTree.x;
	}
	
	/**
	 * Returns Y Location of Skill Tree
	 */
	public int getY() {
		return SkillTree.y;
	}
	
	/**
	 * Updates skill points from Player class
	 */
	public void updatePoints(){
		this.points = Update.PC.getPoints();
	}
}