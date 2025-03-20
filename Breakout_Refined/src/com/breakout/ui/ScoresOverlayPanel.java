package com.breakout.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.breakout.Main;
import com.breakout.utils.HighscoreList;
import com.breakout.utils.LatestRunsList;

public class ScoresOverlayPanel extends JPanel {
    // Fields
	private HighscoreList highscores;
	private LatestRunsList latestRuns;
	
	public ScoresOverlayPanel() {
		super();
		this.highscores = new HighscoreList();
		this.latestRuns = new LatestRunsList();
		
		setOpaque(false);
		setBackground(null);
		
		JList<String> hsList = createList(this.highscores.getModel());		
		JList<String> lrList = createList(this.latestRuns.getModel());
        
        setLayout(new BorderLayout());
        add(hsList, BorderLayout.WEST);
        add(lrList, BorderLayout.EAST);
	}
	
	private JList<String> createList(DefaultListModel<String> listModel) {
		JList<String> list = new JList<String>(listModel);
		list.setFocusable(false);
		list.setOpaque(false);
		list.setForeground(Color.white);
		list.setBackground(Main.BG_COLOR);
		list.setFont(PixelFont.loadFont());
		return list;
	}
	
	public void addScore(int score) {
		highscores.refresh();
		latestRuns.push(score);
	}
}
