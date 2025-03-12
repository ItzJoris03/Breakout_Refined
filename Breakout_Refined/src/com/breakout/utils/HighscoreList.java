package com.breakout.utils;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import com.breakout.input.ScoreList;



/*
 * HighscoreList will represent a sorted list of a maximum of 10 items by descending order of score count which all exists in the ScoreList.
 */
public class HighscoreList extends ScoreList {
	private List<ScoreADT> highscores;
	private static final int LIMIT = 10;
	
	private DefaultListModel<String> dlm;
	
	public HighscoreList() {
		super();
		dlm = new DefaultListModel<>();
		highscores = new ArrayList<ScoreADT>();
		
		sort();
	}
	
	/**
	 * Sorts the ScoreList by ScoreCount to represent HighScores.
	 * @return List<ScoreADT>
	 */
	public List<ScoreADT> sort() {		
		List<ScoreADT> scores = getScores();
		if(!scores.isEmpty()) scores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));
		
		int i = 0;
		highscores.clear();
		for(ScoreADT score : scores) {
			if(i < LIMIT) {
				highscores.add(score);
			}
			i++;
		}
		
		updateModel();
		return highscores;
	}
	
	public void updateHighScores() { sort(); }
	public List<ScoreADT> getHighScores() { return highscores; }
	
	public DefaultListModel<String> getModel() { return dlm; }
	private final void updateModel() {
		dlm.clear();
		
		dlm.addElement("Highscores:");
		int i = 1;
		for(ScoreADT score : highscores) {
			dlm.addElement(i + ". " + score.getUser() + " " + score.getScore());
			i++;
		}
	}
	
	public void refresh() {
		sort();
	}
}
