package com.breakout.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JList;
import javax.swing.JPanel;

import com.breakout.Main;
import com.breakout.utils.HighscoreList;
import com.breakout.utils.LatestRunsList;

public class ScoresOverlayPanel extends JPanel {
	private HighscoreList highscores;
	private LatestRunsList latestRuns;
	
	public ScoresOverlayPanel() {
		super();
		this.setOpaque(false);
		this.setBackground(null);
		highscores = new HighscoreList();
		latestRuns = new LatestRunsList();
		
		JList<String> hsList = new JList<String>(highscores.getModel());
		hsList.setFocusable(false);
		hsList.setOpaque(false);
		hsList.setBackground(Main.BG_COLOR);
		hsList.setForeground(Color.white);
		hsList.setFont(PixelFont.loadFont());
		
		JList<String> lrList = new JList<String>(latestRuns.getModel());
		lrList.setFocusable(false);
		lrList.setOpaque(false);
		lrList.setForeground(Color.white);
		lrList.setBackground(Main.BG_COLOR);
		lrList.setFont(PixelFont.loadFont());
        
        setLayout(new BorderLayout());
        add(hsList, BorderLayout.WEST);
        add(lrList, BorderLayout.EAST);
	}
	
	public void addScore(int score) {
		highscores.refresh();
		latestRuns.push(score);
	}
}
