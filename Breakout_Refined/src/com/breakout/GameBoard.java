package com.breakout;

import java.awt.Color;
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
import com.breakout.ui.ScoresOverlayPanel;
import com.breakout.utils.GameOverFunction;

public class GameBoard extends Engine {	
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	
	private Game game;
	private Keyboard keyboard;
	private boolean hasWon;
	private GameOverFunction gameOverCaller;
	
	public GameBoard(Game game, GameOverFunction gameOver) {
	    super();
	    setPreferredSize(new Dimension(WIDTH, HEIGHT));
	    setBackground(Main.BG_COLOR);
	    setLayout(null);
	    
	    keyboard = new Keyboard();
	    this.game = game;

	    setFocusable(true);
	    requestFocusInWindow();
	    hasWon = false;
	    this.gameOverCaller = gameOver;
	}
	
	public void endGame(boolean hasWon) {
		super.endGame();
		
		this.hasWon = hasWon;
		gameOverCaller.gameOver();
	}
	
	public boolean hasWon() { return this.hasWon; }
    
    @Override
	protected void processKeyEvent(KeyEvent e) {
		super.processKeyEvent(e);
		if (e.getID() == KeyEvent.KEY_PRESSED)
			keyboard.processKeyEvent(e.getKeyCode(), true);
		else if (e.getID() == KeyEvent.KEY_RELEASED)
			keyboard.processKeyEvent(e.getKeyCode(), false);
		
		if(getState() != GameState.START) resumeGame();
	}
    
    @Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		Graphics2D graphics = (Graphics2D)arg0;
		game.draw(graphics);
		
		ScoreManager.drawScores(graphics);
		Toolkit.getDefaultToolkit().sync();
	}



	@Override
	public void update() {
		game.update(keyboard);
	}
}
