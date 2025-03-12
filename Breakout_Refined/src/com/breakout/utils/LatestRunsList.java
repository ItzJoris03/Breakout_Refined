package com.breakout.utils;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import com.breakout.input.ScoreList;

public class LatestRunsList extends ScoreList {
	private List<ScoreADT> latestRuns;
	private static final int LIMIT = 3;
	
	private DefaultListModel<String> dlm;
	
	public LatestRunsList() {
		super();
		dlm = new DefaultListModel<>();
		latestRuns = new ArrayList<ScoreADT>();
		
		sort();
	}
	
	/**
	 * Sorts the ScoreList by Date to represent LatestRuns.
	 * @return List<ScoreADT>
	 */
	public List<ScoreADT> sort() {		
		List<ScoreADT> scores = getScores();
		if(!scores.isEmpty()) scores.sort((s1, s2) -> s2.getDate().compareTo(s1.getDate()));
		
		int i = 0;
		latestRuns.clear();
		for(ScoreADT score : scores) {
			if(i < LIMIT) {
//				System.out.println(score.getDate());
				latestRuns.add(score);
			}
			i++;
		}
		
		updateModel();
		return latestRuns;
	}
	
	public void updateHighScores() { sort(); }
	public List<ScoreADT> getLatestRuns() { return latestRuns; }
	
	public DefaultListModel<String> getModel() { return dlm; }
	private final void updateModel() {
		dlm.clear();
		
		dlm.addElement("Latest Runs");
		
		int i = 1;
		for(ScoreADT score : latestRuns) {
			dlm.addElement(i + ". " + score.getScore());
			i++;
		}
	}
	
	public void push(int score) {		
		if(latestRuns.size() == LIMIT) latestRuns.remove(LIMIT - 1);
		
		for(int i = latestRuns.size(); i > 0; i--) {
			if(i == latestRuns.size()) latestRuns.add(latestRuns.get(i - 1));
			else latestRuns.set(i, latestRuns.get(i - 1));
		}
		
		if(latestRuns.size() == 0) latestRuns.add(new ScoreADT("", score));
		else latestRuns.set(0, new ScoreADT("", score));
		
		updateModel();
	}
}
