package res;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Class defines a JPanel with a custom background image
 * @author Brian Chen
 *
 */
public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 8886187983405191952L;
	private BufferedImage img;

	/**
	 * Constructor. Loads image and creates ImagePanel out of it.
	 * @param imgLink the image location
	 */
	public ImagePanel(String imgLink) {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setVisible(true);

		try {
			this.img = ImageIO.read(getClass().getClassLoader().getResource(imgLink));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setSize(size);
		setLayout(null);
	}

	/**
	 * A Paint Component used for Drawing
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}
}
