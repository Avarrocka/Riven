package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
/**
 * Mouse listener for main menu, performs actions when buttons are pressed, not part of update or render threads
 * @author Brian Chen
 * @version 1.0
 */
public class MousekeyListener implements MouseListener, MouseMotionListener{

	public static boolean mouseClicked = false;
	public static int x = 0;
	public static int y = 0;
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		x = arg0.getX();
		y = arg0.getY();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		x = arg0.getX();
		y = arg0.getY();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mouseClicked = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouseClicked = false;
	}
	
	public static int getX(){
		return x;
	}
	
	public static int getY(){
		return y;
	}
}
