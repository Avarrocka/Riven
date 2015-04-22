package main;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.OverlayLayout;

import java.net.URL;

import basicplayer1.BasicPlayer;
import res.ImagePanel;
import listeners.ButtonListener;
import listeners.KeyboardListener;
import listeners.MousekeyListener;

/**
 * Main graphics class for Trash Smash, generates window, starts render thread, creates main menu
 * @author Brian Chen
 */
public class GraphicsMain {
	
	//Window variables
	public JFrame window = new JFrame("Riven");
	public JPanel infoPane;
	public final static int WIDTH = 1024;
	public final static int HEIGHT = 768;
	
	//Music variables
	public BasicPlayer player = new BasicPlayer();
	
	//Thread variables
	private Thread renderThread;
	public Render render;
	
	//Listeners
	private ButtonListener l;
	private KeyboardListener kl;
	public MousekeyListener mouse; 
	//Menu variables
	private ImageIcon sButton, qButton, scButton;
	public final String MAIN_MENU = "MAINMENU", SCORES_MENU = "SCORESMENU";
	public String menuPane;
	
	/**
	 * Constructor for graphics main, opens the window
	 * @param k
	 */
	public GraphicsMain(KeyboardListener k) {
		l = new ButtonListener();
		kl = k;
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		window.setResizable(false);
		window.pack();
		window.setFocusable(true);
		window.addKeyListener(kl);
		init();
	}
	
	/**
	 * Loads images for use in menus.
	 */
	private void init() {
		mouse = new MousekeyListener();
		sButton = new ImageIcon(getClass().getClassLoader().getResource("UI/startButton.png"));
		qButton = new ImageIcon(getClass().getClassLoader().getResource("UI/quitbutton.png"));
		scButton = new ImageIcon(getClass().getClassLoader().getResource("UI/scoreButton.png"));
		window.addMouseListener(mouse);
		window.addMouseMotionListener(mouse);
	}
	
	/**
	 * Starts render thread 
	 */
	public synchronized void start() {
		renderThread = new Thread(render, "Render Thread");
		renderThread.start();
		try {
			player.stop();
		} catch (basicplayer1.BasicPlayerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Stops any previous music and starts the menu theme music (Giant Woman by Steven Universe)
	 */
	private void playMusic() {
		try {
			player.stop();
		    player.open(getClass().getClassLoader().getResource("Music/Menu.mp3"));
		    player.play();
		} catch (basicplayer1.BasicPlayerException e) {
		    e.printStackTrace();
		}
	}
	
	/**
	 * Generates the Main Menu (and clears any other display)
	 */
	public JPanel createContentPane() {
		playMusic();
		window.setVisible(false);
		window.getContentPane().removeAll();
		
		ImagePanel imgPanel = new ImagePanel("UI/MenuMockup.png");
		
		JPanel mainMenuPane = new JPanel();
		JPanel controlPane = new JPanel();
		JPanel buttonsPane = new JPanel();
		
		mainMenuPane.setLayout(new OverlayLayout(mainMenuPane));

		
		JButton startButton = new JButton();
		JButton quitButton = new JButton();
		JButton scoresButton = new JButton();
		
		startButton.setIcon(sButton);
		startButton.setBorder(null);
		startButton.addActionListener(l);
		startButton.setActionCommand("start");
		
		quitButton.setIcon(qButton);
		quitButton.setBorder(null);		
		quitButton.addActionListener(l);
		quitButton.setActionCommand("quit");
		
		//scoresButton.setIcon(scButton);
		scoresButton.setBorder(null);
		scoresButton.addActionListener(l);
		scoresButton.setActionCommand("scores");
		
		
		controlPane.setLayout(new BoxLayout(controlPane, BoxLayout.Y_AXIS));
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.X_AXIS));
		
		controlPane.add(Box.createRigidArea(new Dimension(20, 120)));
		controlPane.add(startButton);
		
		controlPane.add(Box.createRigidArea(new Dimension(0, 40)));
		controlPane.add(scoresButton);
		
		controlPane.add(Box.createRigidArea(new Dimension(0, 40)));
		controlPane.add(quitButton);
		
		buttonsPane.add(controlPane);
		buttonsPane.add(Box.createRigidArea(new Dimension(670, 0)));
		
		controlPane.setOpaque(false);
		buttonsPane.setOpaque(false);
		
		
		mainMenuPane.add(buttonsPane);
		mainMenuPane.add(imgPanel);
		
		JPanel contentPane = new JPanel(new CardLayout());
		contentPane.add(mainMenuPane, MAIN_MENU);
		
		JPanel highscoresPanel = new JPanel();
		highscoresPanel.setLayout(new OverlayLayout(highscoresPanel));
		JPanel scorePanel = new JPanel();
		
		JTextArea scores = new JTextArea();
		scores.setEditable(false);
		scores.setBorder(null);
		scores.setBackground(null);
		scores.setText(generateScores());
		scorePanel.add(scores);
		
		//highscoresPanel.add(scorePanel);
		
		contentPane.add(highscoresPanel, SCORES_MENU);
		
		window.setContentPane(contentPane);
		window.setVisible(true);
		window.repaint();
		return contentPane;
	}
	
	private String generateScores() {
		return "";
	}
	
	/**
	 * Changes the contentPane of the window to the game pane, repacks the window
	 */
	public void gameStart() {
		window.remove(window.getContentPane());
		JPanel gamePanel = new JPanel();
		window.setContentPane(gamePanel);
		window.pack();
		render = new Render((Graphics2D) window.getGraphics());
	}
	
	/**
	 * Removes the game pane
	 */
	public void menuStart() {
		window.getGraphics().dispose();
	}
}
