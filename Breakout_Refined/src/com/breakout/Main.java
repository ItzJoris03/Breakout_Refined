package com.breakout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
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
	public static final Color BG_COLOR = new Color(30, 30, 30);
	private CardLayout layout;
	private GameBoard gameboard;
	private GameMenu menuPanel;
	private Views currentView;
	private Game game;
	private GameOverPanel gameOverPanel;
	private ScoresOverlayPanel scoresOverlay;
	
	/**
	* Initializing the game's frame and set it to full screen by default
	*/
	public Main() {		
		setTitle("Breakout Game");
		setSize(GameBoard.WIDTH, GameBoard.HEIGHT);
		setMinimumSize(new Dimension(GameBoard.WIDTH, GameBoard.HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBackground(BG_COLOR);
		
		setResizable(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		setFocusable(true);
		
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
		JPanel gamePanel = new JPanel(new GridBagLayout());
		gamePanel.setBackground(Color.BLACK);
		gamePanel.add(createGameScreen());
		
		gameOverPanel = new GameOverPanel(
		    e -> startGame(),
		    e -> changeScreen(Views.Menu)
		);
		
		game.setBoard(gameboard);
		
		add(menuPanel, "Menu");
		add(gamePanel, "Game");
		add(gameOverPanel, "GameOver");
		layout.show(getContentPane(), "Menu");
		currentView = Views.Menu;
	}
	
	private JLayeredPane createGameScreen() {
		JLayeredPane layeredPane = new JLayeredPane();
	    layeredPane.setPreferredSize(new Dimension(GameBoard.WIDTH, GameBoard.HEIGHT));

	    JPanel gamePanel = new JPanel(new GridBagLayout());
	    gamePanel.setBounds(0, 0, GameBoard.WIDTH, GameBoard.HEIGHT);
	    gamePanel.add(gameboard);
	    layeredPane.add(gamePanel, Integer.valueOf(0));

	    scoresOverlay = new ScoresOverlayPanel();
	    scoresOverlay.setBounds(0, 0, GameBoard.WIDTH, GameBoard.HEIGHT);
	    layeredPane.add(scoresOverlay, Integer.valueOf(1));

	    return layeredPane;
	}
	
	private void gameOver() {	
		int score = ScoreManager.getScore();
		gameOverPanel.updateGameOverStatus(gameboard.hasWon(), score);		
		gameboard.endGame();
		
		changeScreen(Views.Game_Over);
	
		game.reset();
		scoresOverlay.addScore(score);
	}
	
	private void startGame() {
		changeScreen(Views.Game);
		game.setPaddleInitials(menuPanel.getPlayerName());
	}
	
	private void changeScreen(Views view) {
		switch(view) {
		case Views.Menu: 
			layout.show(getContentPane(), "Menu");
			currentView = Views.Menu;
			break;
		case Views.Game:
			layout.show(getContentPane(), "Game");
			currentView = Views.Game;
			break;
		case Views.Game_Over:
			layout.show(getContentPane(), "GameOver");
			currentView = Views.Game_Over;
			break;
		default:
			break;
		}
	}
	
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
