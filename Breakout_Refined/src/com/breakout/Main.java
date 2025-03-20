package com.breakout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.breakout.core.Game;
import com.breakout.core.Views;
import com.breakout.levels.ScoreManager;
import com.breakout.ui.GameMenu;
import com.breakout.ui.GameOverPanel;
import com.breakout.ui.ScoresOverlayPanel;

public class Main extends JFrame {
	// Constants related to the Class
	public static final Color BG_COLOR = new Color(30, 30, 30);
	
	// Fields
	private CardLayout layout;
	private GameBoard gameboard;
	private GameMenu menuPanel;
	private Views currentView;
	private Game game;
	private GameOverPanel gameOverPanel;
	private ScoresOverlayPanel scoresOverlay;
	
	/**
	* Initializing the game's frame
	*/
	public Main() {		
		setTitle("Breakout Game");
		
		// Sizing the frame to the width and height defined by the GameBoard
		Dimension size = new Dimension(GameBoard.WIDTH, GameBoard.HEIGHT);
		setSize(size);
		setMinimumSize(size);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBackground(Main.BG_COLOR);
		
		// Make resizable possible and set the frame to fullscreen by default
		setResizable(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		setFocusable(true);
		
		// Defining and setting the frame's content
		setContent();
	}
	
	/**
	* Setting up the game's content and open the menu by default
	*/
	private void setContent() {
		// Creating layout
		layout = new CardLayout();
		setLayout(layout);
		
		// Creating panels
		menuPanel = new GameMenu(e -> startGame());
		game = new Game();
		gameboard = new GameBoard(game, () -> gameOver());
		
		// Creating the game specific panel for centering the gameboard
		JPanel gamePanel = new JPanel(new GridBagLayout());
		gamePanel.setBackground(Color.BLACK);
		gamePanel.add(createGameScreen());
		
		// Adding a GameOverPanel with lambda expressions for event handlers
		gameOverPanel = new GameOverPanel(
		    e -> startGame(),
		    e -> changeScreen(Views.Menu)
		);
		
		game.setBoard(gameboard);
		
		// Adding components to the frame
		add(menuPanel, "Menu");
		add(gamePanel, "Game");
		add(gameOverPanel, "GameOver");
		
		// Setting the current visible panel to Menu
		layout.show(getContentPane(), "Menu");
		currentView = Views.Menu;
	}
	
	/**
	 * Creating the game screen with an overlay showing the latest runs and highscores whilst keep the game centered in screen.
	 * 
	 * @return JLayeredPane
	 */
	private JLayeredPane createGameScreen() {
		JLayeredPane layeredPane = new JLayeredPane();
	    layeredPane.setPreferredSize(new Dimension(GameBoard.WIDTH, GameBoard.HEIGHT));

	    // Creating the game panel itself with the gameboard included
	    JPanel gamePanel = new JPanel(new GridBagLayout());
	    gamePanel.setBounds(0, 0, GameBoard.WIDTH, GameBoard.HEIGHT);
	    gamePanel.add(gameboard);
	    layeredPane.add(gamePanel, Integer.valueOf(0));

	    // Creating the scores overlay panel on top of the gameboard
	    scoresOverlay = new ScoresOverlayPanel();
	    scoresOverlay.setBounds(0, 0, GameBoard.WIDTH, GameBoard.HEIGHT);
	    layeredPane.add(scoresOverlay, Integer.valueOf(1));

	    return layeredPane;
	}

	/**
	 * Call game over functions and reset the game and logging the scores
	 */
	private void gameOver() {	
		int score = ScoreManager.getScore();
		gameOverPanel.updateGameOverStatus(gameboard.hasWon(), score);		
		gameboard.endGame();
		
		changeScreen(Views.Game_Over);
	
		game.reset();
		scoresOverlay.addScore(score);
	}
	
	/**
	 * Start the game
	 */
	private void startGame() {
		changeScreen(Views.Game);
		game.setName(menuPanel.getPlayerName());
	}
	
	/**
	 * Changing the screen
	 * @param view
	 */
	private void changeScreen(Views view) {
		String viewName = "";
		
		// Retrieving the view's name from the parameter and return nothing if not known
		switch(view) {
			case Views.Menu: 		viewName = "Menu";		break;
			case Views.Game: 		viewName = "Game"; 		break;
			case Views.Game_Over:	viewName = "GameOver";	break;
			default:										return;
		}
		
		// Setting the currentView and change the view
		currentView = view;
		layout.show(getContentPane(), viewName);
	}
	
	/**
	 * Overriding the processKeyEvent and pass them to the gameboard if the gameboard is visible
	 */
	@Override
	protected void processKeyEvent(KeyEvent e) {
		super.processKeyEvent(e);
		
		if(currentView == Views.Game) gameboard.processKeyEvent(e);
	}

	public static void main(String[] args) {		
		SwingUtilities.invokeLater(() -> {
            Main mainframe = new Main();
            mainframe.setVisible(true);
        });
	}

}
