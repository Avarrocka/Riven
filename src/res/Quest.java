package res;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
public class Quest{
	private String questName;
	private String description;
	private String task;
	private boolean qDone = false;
	public Quest(String ID) {
		this.questName = ID;
		if(this.questName == "Fixing the Portal"){
			description = "Help the Gate Guardian fix the Dimensional Gate found at the Ruins of Largos.";
			task = "Locate a Soul Gem to supercharge the Gate with. Someone in town may have an idea";
		}
		if(this.questName == "Priam's Task"){
			description = "Help Guard Priam slay 5 slimes to help him along his guard shift.";
			task = "Kill 5 Slimes. You've currently killed 0/5";
		}
	}
	public void update(){
		if(this.questName == "Priam's Task"){
			task = "Kill 5 Slimes. You've currently killed " + Main.update.slimesSlain +"/5";
		}
		checkComplete();
	}
	private void checkComplete(){
		if(this.questName == "Priam's Task" && Main.update.slimesSlain >= 5){
			qDone = true;
		}
		if(this.questName == "Fixing the Portal"){
			if(Main.update.gem){
				boolean hasGemStill = false;
				for(int i = 0; i < Main.update.PC.qItems.size(); i++){
					if(Main.update.PC.qItems.get(i).getID() == "Soul Gem"){
						hasGemStill = true;
					}
				}
				if(!hasGemStill){
					qDone = true;
				}
			}
		}
	}
	public String getDesc(){
		return this.description;
	}
	public String getName(){
		return this.questName;
	}
	public String getTask(){
		return this.task;
	}
	public boolean getDone(){
		return this.qDone;
	}
}