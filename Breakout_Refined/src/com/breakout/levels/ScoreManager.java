package com.breakout.levels;

import java.awt.Color;
import java.awt.Graphics;

import com.breakout.GameBoard;
import com.breakout.ui.PixelFont;


public class ScoreManager {
	// Constants related to the Class
	public static final String FILE = "src/com/breakout/assets/files/scores.txt";
	private static final int DRAW_PADDING_X = 20;
	private static final int DRAW_PADDING_Y = 30;
	
	// Fields
	private static int currentScore = 0;
	
	public static void reset() { currentScore = 0; }
		
	public static void addScore(int increment) { currentScore += increment; }
	public static void removeScore(int decrement) { currentScore -= decrement; }
	
	public static int getScore() { return currentScore; }
	
	/**
	 * Draws the scores onto the screen.
	 * @param g
	 */
	public static void drawScores(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(PixelFont.loadFont());
        
        String scoreNameString = "SCORE: " + currentScore;
        g.drawString(scoreNameString, DRAW_PADDING_X, GameBoard.HEIGHT - DRAW_PADDING_Y);
	}
}
