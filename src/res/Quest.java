package res;

import main.Main;
import main.Update;

/**
 * Class defines a Quest interface
 * @author Brian Chen
 *
 */
public class Quest{
	private String questName;
	private String description;
	private String task;
	private boolean qDone = false, qFin = false;
	public Quest(String ID) {
		//Quest is defined by ID, defining description and quest info
		this.questName = ID;
		if(this.questName == "Fixing the Portal"){
			description = "Help the Gate Guardian fix the Dimensional Gate found at the Ruins of Largos.";
			task = "Locate a Soul Gem to supercharge the Gate with. Venture deeper into the forest for clues.";
		}
		if(this.questName == "Priam's Task"){
			description = "Help Guard Priam slay 5 slimes to help him along his guard shift.";
			task = "Kill 5 Slimes. You've currently killed 0/5";
		}
	}
	
	/**
	 * Updates the quest task depending on how progress is
	 */
	public void update(){
		if(this.questName == "Priam's Task"){
			if(!qFin)
				task = "Kill 5 Slimes. You've currently killed " + Main.update.slimesSlain +"/5";
		}
		checkComplete();
	}
	
	/**
	 * Checks if quest is complete, and sets variables if it is
	 */
	private void checkComplete(){
		if(this.questName == "Priam's Task" && Main.update.slimesSlain >= 5 && !qFin){
			qFin = true;
			updateDialogue();
			Main.update.priamDone = true;
		}
		if(this.questName == "Fixing the Portal"){
			if(Main.update.gem){
				boolean hasGemStill = false;
				for(int i = 0; i < Update.PC.qItems.size(); i++){
					if(Update.PC.qItems.get(i).getID() == "Soul Gem"){
						hasGemStill = true;
					}
				}
				if(!hasGemStill){
					qDone = true;
				}
				if(hasGemStill && !qFin){
					qFin = true;
					updateDialogue();
				}
			}
		}
	}
	
	/**
	 * Returns description (flavor text) of quest
	 * @return
	 */
	public String getDesc(){
		return this.description;
	}
	
	/**
	 * Returns quest name
	 * @return
	 */
	public String getName(){
		return this.questName;
	}
	
	/**
	 * Returns tips and tracker of quest
	 * @return
	 */
	public String getTask(){
		return this.task;
	}
	
	/**
	 * Returns if Quest is finished (not yet claimed)
	 * @return
	 */
	public boolean getFinished(){
		return this.qFin;
	}
	
	/**
	 * Returns if Quest is done (ready to be removed)
	 * @return
	 */
	public boolean getDone(){
		return this.qDone;
	}
	
	/**
	 * Updates the dialogue at key points in the Quest
	 */
	private void updateDialogue(){
		if(this.questName == "Priam's Task" && qFin){
			task = "You've killed the five slimes. Return to Priam for a reward.";
		}
		if(this.questName == "Fixing the Portal" && qFin){
			task = "You've found the Soul Gem. Return to Yenfay to fix the portal.";
		}
	}
	
	/**
	 * Claims reward for the quest and sets quest to Done from Finished
	 */
	public void claimReward(){
		if(this.questName == "Priam's Task"){
			Update.PC.setEXP(50);
			qDone = true;
		}
	}
}