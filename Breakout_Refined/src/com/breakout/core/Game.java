package com.breakout.core;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.breakout.GameBoard;
import com.breakout.entities.*;
import com.breakout.input.Keyboard;
import com.breakout.input.ScoreList;
import com.breakout.levels.MapGenerator;
import com.breakout.levels.ScoreManager;
import com.breakout.ui.PixelFont;

public class Game {	
	private static final int DRAW_PADDING_X = 20;
	private static final int DRAW_PADDING_Y = 30;
	
	private Ball ball;
	private Hearts hearts;
	
	private Paddle paddle;
	int tickCount;
	
	private final int PADDLE_WIDTH = Paddle.TEXTURE_WIDTH *3;
	private final int PADDLE_HEIGHT = Paddle.TEXTURE_HEIGHT *3;
	private final int PADDLE_Y = GameBoard.HEIGHT - 50;
	
	private final int BALL_SIZE = 20;
	private final double HEARTS_SCALE = 2.0;
	
	private GameBoard board;
	private MapGenerator map;
	
	private String user;

	
	/**
	 * Init Game Properties
	 */
	public Game() {	initGame(); }
	
	private void initGame() {
		hearts = new Hearts(
			GameBoard.WIDTH - (int)(Hearts.TEXTURE_WIDTH*HEARTS_SCALE) - DRAW_PADDING_X,
			GameBoard.HEIGHT - (int)(Hearts.TEXTURE_HEIGHT*HEARTS_SCALE) - DRAW_PADDING_Y,
			HEARTS_SCALE
		);
		this.paddle = new Paddle(
			(GameBoard.WIDTH - this.PADDLE_WIDTH)/2, 
			this.PADDLE_Y - this.PADDLE_HEIGHT, 
			3.0
		);
		
		map = new MapGenerator();
		
		initBall();
	}
	
	public void reset() { 
		initGame(); 
		ScoreList.saveScore(user, ScoreManager.getScore());
		ScoreManager.reset();
	}
	public void setBoard(GameBoard board) { this.board = board;	}
	
	private void initBall() {
		this.ball = new Ball(
			this.paddle.getX() + (this.paddle.getWidth()/2) -  this.BALL_SIZE/2, 
			this.PADDLE_Y - this.PADDLE_HEIGHT - this.BALL_SIZE, 
			5.0
		);
	}
	
	public void lostBall() {
		if(hearts.getTotalHearts() == 0) board.endGame(false);
		else {
			hearts.removeHeart();
			initBall();
		}
	}
	
	public void setPaddleInitials(String initials) {
		this.user = initials;
	}
	
	public void update(Keyboard keyboard) {
		this.ball.update(keyboard);
		this.paddle.update(keyboard);
		
		this.ball.checkCollision(this.paddle);
		
		if(this.ball.getY()+this.ball.getHeight() >= GameBoard.HEIGHT) lostBall();
		
		Brick[][] bricks = map.getBricks();
		for (int row = 0; row < bricks.length; row++) {
	        for (int col = 0; col < bricks[row].length; col++) {
	            if (bricks[row][col] != null && this.ball.checkCollision(bricks[row][col])) {
	                map.removeBrick(row, col);
	            }
	        }
	    }
		
		if(this.map.getTotalBricks() == 0) board.endGame(true);
	}

	public void draw(Graphics2D g) {
		this.ball.draw(g);
		hearts.draw(g);
		
		this.paddle.draw(g);
		map.draw(g);
	}
}
