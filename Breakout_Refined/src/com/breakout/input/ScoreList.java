package com.breakout.input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.breakout.levels.ScoreManager;
import com.breakout.utils.ScoreADT;

public abstract class ScoreList {
	// Constants related to the Class
	private static final SimpleDateFormat DATE = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

	// Fields
	private static List<ScoreADT> scores;
	
	public ScoreList() {
		scores = new ArrayList<>();
	}
	
	/** 
	 * Adding score to the scores ArrayList
	 * @param name
	 * @param score
	 * @param date
	 */
	public static void addScore(String name, int score) { addScore(new ScoreADT(name, score)); }
	public static void addScore(String name, int score, Date date) { addScore(new ScoreADT(name, score, date)); }
	public static void addScore(ScoreADT score) { scores.add(score); }
	
	/**
	 * Parsing the date by string or provide the date of today if string cannot be parsed.
	 * @param dateString
	 * @return Date
	 */
	private static Date parseDate(String dateString) {
        try {
            return DATE.parse(dateString);
        } catch (Exception e) {
            return new Date();
        }
    }
	
	/**
	 * Saving the score to the file by playername and date.
	 * @param playerName
	 * @param currentScore
	 */
	public static void saveScore(String playerName, int currentScore) {
		addScore(playerName, currentScore);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ScoreManager.FILE))) {
            for (ScoreADT score : getScores()) {
                writer.write(score.getUser() + " - " + score.getScore() + " (" + score.getDate() + ")");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public abstract List<ScoreADT> sort();	
	public static final List<ScoreADT> getScores() { return loadScores(); }
	
	/**
	 * Loading the scores saving the the scores files.
	 * @return List<ScoreADT>
	 */
	public static final List<ScoreADT> loadScores() {
		if(scores.isEmpty()) {		
			try (BufferedReader reader = new BufferedReader(new FileReader(ScoreManager.FILE))) {
		        String line;
		        while ((line = reader.readLine()) != null) {
		            line = line.trim(); // Remove any leading/trailing spaces
	
		            // Skip empty lines
		            if (line.isEmpty()) continue;
	
		            String[] parts = line.split(" - ");
		            if (parts.length == 2) {
		                String playerName = parts[0].isEmpty() ? "Unknown" : parts[0]; // Handle missing names
	
		                // Extract score and date
		                String[] scoreDateParts = parts[1].split("\\(");
		                String scoreString = scoreDateParts[0].trim(); 
		                int score = Integer.parseInt(scoreString);
	
		                // Extract and parse date
		                Date date = parseDate(scoreDateParts.length > 1 ? scoreDateParts[1].replace(")", "").trim() : "");
	
		                addScore(playerName, score, date);
		            }
		        }
		    } catch (IOException e) { e.printStackTrace(); }
		}
		
		return scores;
	}
}
