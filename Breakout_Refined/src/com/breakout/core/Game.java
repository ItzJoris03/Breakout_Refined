package com.breakout.core;

import java.awt.Graphics2D;

import com.breakout.GameBoard;
import com.breakout.entities.*;
import com.breakout.input.Keyboard;
import com.breakout.input.ScoreList;
import com.breakout.levels.MapGenerator;
import com.breakout.levels.ScoreManager;

public class Game {	
	// Constants related to the Class
	private static final int DRAW_PADDING_X = 20;
	private static final int DRAW_PADDING_Y = 30;
	
	// Constants related to the Object
	private final int PADDLE_WIDTH = Paddle.TEXTURE_WIDTH *3;
	private final int PADDLE_HEIGHT = Paddle.TEXTURE_HEIGHT *3;
	private final int PADDLE_Y = GameBoard.HEIGHT - 50;
	private final int BALL_SIZE = 20;
	private final double HEARTS_SCALE = 2.0;
	
	// Fields
	private Ball ball;
	private Hearts hearts;
	private Paddle paddle;
	private GameBoard board;
	private MapGenerator map;
	private String user;

	
	/**
	 * Init Game Properties
	 */
	public Game() {	initGame(); }
	
	/**
	 * Initializing the Game variables
	 */
	private void initGame() {
		this.hearts = new Hearts(
			GameBoard.WIDTH - (int)(Hearts.TEXTURE_WIDTH*HEARTS_SCALE) - DRAW_PADDING_X,
			GameBoard.HEIGHT - (int)(Hearts.TEXTURE_HEIGHT*HEARTS_SCALE) - DRAW_PADDING_Y,
			HEARTS_SCALE
		);
		
		this.paddle = new Paddle(
			(GameBoard.WIDTH - this.PADDLE_WIDTH)/2, 
			this.PADDLE_Y - this.PADDLE_HEIGHT, 
			3.0
		);
		
		this.map = new MapGenerator();
		
		initBall();
	}
	
	/**
	 * Resets the game
	 */
	public void reset() { 
		initGame(); 
		ScoreList.saveScore(user, ScoreManager.getScore());
		ScoreManager.reset();
	}
	
	// Sets the gameboard to provide easy communication
	public void setBoard(GameBoard board) { this.board = board;	}
	
	/**
	 * Initializing a ball
	 */
	private void initBall() {
		this.ball = new Ball(
			this.paddle.getX() + (this.paddle.getWidth()/2) -  this.BALL_SIZE/2, 
			this.PADDLE_Y - this.PADDLE_HEIGHT - this.BALL_SIZE, 
			5.0
		);
	}
	
	/**
	 * Function that will be called when losing a ball
	 */
	public void lostBall() {
		// Ends the game once all hearts / lives are gone
		if(hearts.getTotalHearts() == 0) board.endGame(false);
		
		// Else remove a live and summon a new ball
		else {
			hearts.removeHeart();
			initBall();
		}
	}
	
	/**
	 * Sets the player name
	 * @param initials
	 */
	public void setName(String initials) { this.user = initials; }
	
	/**
	 * Updates the game logic
	 * @param keyboard
	 */
	public void update(Keyboard keyboard) {
		this.ball.update(keyboard);
		this.paddle.update(keyboard);
		
		this.ball.checkCollision(this.paddle);
		
		// Check if the ball didn't got below the frame
		if(this.ball.getY()+this.ball.getHeight() >= GameBoard.HEIGHT) lostBall();
		
		// Check if the ball collides with a brick and remove the brick if true.
		Brick[][] bricks = map.getBricks();
		for (int row = 0; row < bricks.length; row++) {
	        for (int col = 0; col < bricks[row].length; col++) {
	            if (bricks[row][col] != null && this.ball.checkCollision(bricks[row][col])) {
	                map.removeBrick(row, col);
	            }
	        }
	    }
		
		// End the game if all bricks are gone
		if(this.map.getTotalBricks() == 0) board.endGame(true);
	}
	
	/**
	 * Draw function to draw all entities
	 * @param graphics
	 */
	public void draw(Graphics2D g) {
		ball.draw(g);
		hearts.draw(g);
		paddle.draw(g);
		map.draw(g);
	}
}
