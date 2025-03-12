package com.breakout.ui;

import javax.swing.*;

import com.breakout.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultySelector extends JPanel {
    private static final String[] difficulties = {"Easy", "Medium", "Hard", "Insane"};
    private int currentIndex = 0;
    private JLabel difficultyLabel;

    public DifficultySelector() {
        // Set the layout for the panel
        setLayout(new BorderLayout());
        
        // Create a panel for the arrows and the label
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // Left arrow button
        JButton leftButton = new JButton("<");
        leftButton.setFont(PixelFont.loadFont(20));
        leftButton.setFocusPainted(false);
        leftButton.setBackground(Main.BG_COLOR);
        leftButton.setForeground(Color.WHITE);
        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndex = (currentIndex - 1 + difficulties.length) % difficulties.length;
                updateDifficultyLabel();
            }
        });
        centerPanel.add(leftButton, BorderLayout.WEST);

        // Difficulty label
        difficultyLabel = new JLabel(difficulties[currentIndex], JLabel.CENTER);
        difficultyLabel.setFont(PixelFont.loadFont(18));
        difficultyLabel.setPreferredSize(new Dimension(150, 50));
        difficultyLabel.setBackground(Main.BG_COLOR);
        difficultyLabel.setForeground(Color.white);
        difficultyLabel.setOpaque(true);
        centerPanel.add(difficultyLabel, BorderLayout.CENTER);

        // Right arrow button
        JButton rightButton = new JButton(">");
        rightButton.setFont(PixelFont.loadFont(20));
        rightButton.setFocusPainted(false);
        rightButton.setBackground(Main.BG_COLOR);
        rightButton.setForeground(Color.WHITE);
        rightButton.addActionListener(new ActionListener() {
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

    private void updateDifficultyLabel() {
        difficultyLabel.setText(difficulties[currentIndex]);
    }

    public String getSelectedDifficulty() {
        return difficulties[currentIndex];
    }
}
