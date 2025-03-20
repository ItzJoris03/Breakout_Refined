package com.breakout.utils;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import com.breakout.input.ScoreList;

/**
 * LatestRunsList will represent a sorted list of a maximum of 3 items by descending order of date which all exists in the ScoreList by default.
 */
public class LatestRunsList extends ScoreList {
	// Constants related to the Class
	private static final int LIMIT = 3;
	
    // Fields
	private List<ScoreADT> latestRuns;	
	private DefaultListModel<String> dlm;
	
	public LatestRunsList() {
		super();
		this.dlm = new DefaultListModel<>();
		this.latestRuns = new ArrayList<ScoreADT>();
		
		sort();
	}
	
	/**
	 * Sorts the ScoreList by Date to represent LatestRuns.
	 * @return List<ScoreADT>
	 */
	public List<ScoreADT> sort() {	
		// Get all scores
		List<ScoreADT> scores = getScores();
		
		// Sort the scores by date in ascending order if the array isn't empty
		if(!scores.isEmpty()) scores.sort((s1, s2) -> s2.getDate().compareTo(s1.getDate()));
		
		// Clears the list and adding all scores until the LIMIT reached
		int i = 0;
		latestRuns.clear();
		for(ScoreADT score : scores) {
			if(i < LIMIT) {
				latestRuns.add(score);
			}
			i++;
		}
		
		// Update the model
		updateModel();
		return latestRuns;
	}
	
	/**
	 * Calling the sort function to auto update the Latest Runs.
	 */
	public void updateLatestRuns() { sort(); }
	
	/**
	 * Gives the latest runs in a list
	 * @return List<ScoreADT>
	 */
	public List<ScoreADT> getLatestRuns() { return latestRuns; }
	
	/**
	 * Gives the model of strings of the Latest Runs
	 * @return DefaultListModel<String>
	 */
	public DefaultListModel<String> getModel() { return dlm; }
	
	/**
	 * Updates the model
	 */
	private final void updateModel() {
		// Clears the model
		dlm.clear();
		
		// Adds the title to the model
		dlm.addElement("Latest Runs");
		
		// Loop through the list of scores to provide the index and gets the score only in text.
		int i = 1;
		for(ScoreADT score : latestRuns) {
			dlm.addElement(i + ". " + score.getScore());
			i++;
		}
	}
	
	/**
	 * Pushing a new score to the list and limits it by the given LIMIT.
	 * @param score
	 */
	public void push(int score) {		
		// Removes the last items if the length of the latestRuns array is LIMIT
		if(latestRuns.size() == LIMIT) latestRuns.remove(LIMIT - 1);
		
		// Moves all items in the array by 1
		for(int i = latestRuns.size(); i > 0; i--) {
			if(i == latestRuns.size()) latestRuns.add(latestRuns.get(i - 1));
			else latestRuns.set(i, latestRuns.get(i - 1));
		}
		
		// Adding the new score in front
		if(latestRuns.size() == 0) latestRuns.add(new ScoreADT("", score));
		else latestRuns.set(0, new ScoreADT("", score));
		
		// Update the model
		updateModel();
	}
}
