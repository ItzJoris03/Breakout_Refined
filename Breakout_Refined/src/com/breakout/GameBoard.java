package com.breakout;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import com.breakout.core.Engine;
import com.breakout.core.Game;
import com.breakout.core.GameState;
import com.breakout.input.Keyboard;
import com.breakout.levels.ScoreManager;
import com.breakout.utils.GameOverFunction;

/**
 * Defining the Breakout GameBoard using a custom Engine.
 */
public class GameBoard extends Engine {	
	// Constants related to the Class
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	
	// Fields
	private Game game;
	private Keyboard keyboard;
	private boolean hasWon;
	private GameOverFunction gameOverCaller;
	
	/**
	 * Initializing the GameBoard, needs the game logic and a GameOverFunction to fully function.
	 * @param game
	 * @param gameOver
	 */
	public GameBoard(Game game, GameOverFunction gameOver) {
	    super();	    
	    
	    // Initializing fields
	    this.game = game;
	    this.gameOverCaller = gameOver;
	    this.keyboard = new Keyboard();
	    this.hasWon = false;

	    // Set JPanel variable inherited by the Engine class
	    setPreferredSize(new Dimension(WIDTH, HEIGHT));
	    setBackground(Main.BG_COLOR);
	    setLayout(null);
	    setFocusable(true);
	    requestFocusInWindow();
	}
	
	/**
	 * Ends the gameplay.
	 * @param hasWon
	 */
	public void endGame(boolean hasWon) {
		super.endGame();
		
		this.hasWon = hasWon;
		gameOverCaller.gameOver();
	}
	
	/**
	 * Returns if the player won or not.
	 * @return boolean
	 */
	public boolean hasWon() { return this.hasWon; }
    
	/**
	 * Overrides the default processKeyEvent to add user input to the gameplay.
	 */
    @Override
	protected void processKeyEvent(KeyEvent e) {
		super.processKeyEvent(e);
		
		// Validate the key and process it.
		if (e.getID() == KeyEvent.KEY_PRESSED)
			keyboard.processKeyEvent(e.getKeyCode(), true);
		else if (e.getID() == KeyEvent.KEY_RELEASED)
			keyboard.processKeyEvent(e.getKeyCode(), false);
		
		// If the gamestate was paused, the game will be resumed and playable again
		if(getState() != GameState.START) resumeGame();
	}
    
    /**
     * Painting the component on screen.
     */
    @Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		
		Graphics2D graphics = (Graphics2D)arg0;
		
		// Draws the game logic
		game.draw(graphics);
		
		// Draws the current scores
		ScoreManager.drawScores(graphics);
		
		// Synchronizes so it works on Linux properly
		Toolkit.getDefaultToolkit().sync();
	}

    /**
     * Updates the overall game logic.
     */
	@Override
	public void update() { game.update(keyboard); }
}
