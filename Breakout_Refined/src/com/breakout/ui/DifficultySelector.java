package com.breakout.ui;

import javax.swing.*;

import com.breakout.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultySelector extends JPanel {
	// Constants related to the Class
    private static final String[] difficulties = {"Easy", "Medium", "Hard", "Insane"};
    
    // Fields
    private int currentIndex;
    private JLabel difficultyLabel;

    public DifficultySelector() {
    	this.currentIndex = 0;
    	
        // Set the layout for the panel
        setLayout(new BorderLayout());
        
        // Create a panel for the arrows and the label
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // Left arrow button
        JButton leftButton = createButton("<", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndex = (currentIndex - 1) % difficulties.length;
                updateDifficultyLabel();
            }
        });
        centerPanel.add(leftButton, BorderLayout.WEST);

        // Difficulty label
        difficultyLabel = createDifficultyLabel();
        centerPanel.add(difficultyLabel, BorderLayout.CENTER);

        // Right arrow button
        JButton rightButton = createButton(">", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndex = (currentIndex + 1) % difficulties.length;
                updateDifficultyLabel();
            }
        });
        centerPanel.add(rightButton, BorderLayout.EAST);

        // Add the center panel to the DifficultySelector
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private JButton createButton(String text, ActionListener listener) {
    	JButton btn = new JButton(">");
    	btn.setFont(PixelFont.loadFont(20));
    	btn.setFocusPainted(false);
    	btn.setBackground(Main.BG_COLOR);
    	btn.setForeground(Color.WHITE);
    	btn.addActionListener(listener);
    	return btn;
    }
    
    private JLabel createDifficultyLabel() {
    	JLabel difficultyLabel = new JLabel(difficulties[currentIndex], JLabel.CENTER);
        difficultyLabel.setFont(PixelFont.loadFont(18));
        difficultyLabel.setPreferredSize(new Dimension(150, 50));
        difficultyLabel.setBackground(Main.BG_COLOR);
        difficultyLabel.setForeground(Color.white);
        difficultyLabel.setOpaque(true);
        return difficultyLabel;
    }

    private void updateDifficultyLabel() { difficultyLabel.setText(difficulties[currentIndex]); }
    public String getSelectedDifficulty() { return difficulties[currentIndex]; }
}
