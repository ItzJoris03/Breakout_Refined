package com.breakout.levels;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.breakout.GameBoard;
import com.breakout.input.ScoreList;
import com.breakout.ui.PixelFont;
import com.breakout.utils.ScoreADT;


public class ScoreManager {
	public static final String FILE = "src/com/breakout/assets/files/scores.txt";
	private static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final int MAX_SCORES = 3;
	private static final int DRAW_PADDING_X = 20;
	private static final int DRAW_PADDING_Y = 30;
	
	private static int currentScore = 0;
	
//	public static void saveScore(String playerName) {
//		scores.addScore("Test", currentScore);
//		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
//            for (ScoreADT score : scores.getScores()) {
//                writer.write(score.getUser() + " - " + score.getScore() + " (" + score.getDate() + ")");
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//	}
	
	public static void reset() {
		currentScore = 0;
	}
	
//	public static List<ScoreADT> getHighScores() {
//		if(scores.getScores().isEmpty())
//		
//		try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
//	        String line;
//	        while ((line = reader.readLine()) != null) {
//	            line = line.trim(); // Remove any leading/trailing spaces
//
//	            // Skip empty lines
//	            if (line.isEmpty()) continue;
//
//	            System.out.println("Reading line: " + line);
//
//	            String[] parts = line.split(" - ");
//	            if (parts.length == 2) {
//	                String playerName = parts[0].isEmpty() ? "Unknown" : parts[0]; // Handle missing names
//
//	                // Extract score and date
//	                String[] scoreDateParts = parts[1].split("\\(");
//	                String scoreString = scoreDateParts[0].trim(); 
//	                int score = Integer.parseInt(scoreString);
//
//	                // Extract and parse date
//	                Date date = parseDate(scoreDateParts.length > 1 ? scoreDateParts[1].replace(")", "").trim() : "");
//
//	                scores.addScore(playerName, score, date);
//
//	                System.out.println("Parsed Score: " + score + " for Player: " + playerName);
//	            } else {
//	                System.out.println("Skipping malformed line: " + line);
//	            }
//	        }
//	    } catch (IOException e) {
//	        System.out.println("No high scores found, starting fresh.");
//	    }
//
//	    return scores.getScores();
//	}
//	
	public static void addScore(int increment) {
		currentScore += increment;
	}
	
	public static void removeScore(int decrement) {
		currentScore -= decrement;
	}
	
	public static int getScore() {
		return currentScore;
	}
	
	public static void drawScores(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(PixelFont.loadFont());
        
        String scoreNameString = "SCORE: " + currentScore;
        g.drawString(scoreNameString, DRAW_PADDING_X, GameBoard.HEIGHT - DRAW_PADDING_Y);

        // Draw the high score in the top-left corner
//        g.drawString("HIGH SCORE: ", DRAW_PADDING_X, DRAW_PADDING_Y);
//        
//        if(getHighScores().size() > 0) {
//        	for(int i = 0; i < MAX_SCORES && i < getHighScores().size(); i++) {
//            	g.drawString(i+1 + ". " + getHighScores().get(i).getScore(), DRAW_PADDING_X, DRAW_PADDING_Y*2 + i * DRAW_PADDING_Y);
//            }
//        } else {
//        	g.drawString("1. 0", DRAW_PADDING_X, DRAW_PADDING_Y*2);
//        }
	}
}
