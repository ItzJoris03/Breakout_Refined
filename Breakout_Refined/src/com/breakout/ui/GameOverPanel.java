package com.breakout.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import com.breakout.Main;

public class GameOverPanel extends JPanel {
    private static final int GUI_INSETS = 10;
    private static final int MAX_WIDTH_COMPONENT = 500;
    private static final int MAX_HEIGHT_COMPONENT = 50;
    private static final Color COLOR_BUTTONS = new Color(50, 50, 50);

    private GridBagConstraints gbc;
    private JLabel titleLabel;
    private JLabel scoreLabel;

    public GameOverPanel(ActionListener playAgainListener, ActionListener backToMenuListener) {
        setLayout(new GridBagLayout());
        setBackground(Main.BG_COLOR);

        initGBC();

        Font optionFont = PixelFont.loadFont();

        // Title (Initially Empty)
        titleLabel = createTitle("");
        addComponent(titleLabel);

        addComponent(spacer());

        // Score Display (Initially Empty)
        scoreLabel = new JLabel("", SwingConstants.CENTER);
        scoreLabel.setFont(PixelFont.loadFont());
        scoreLabel.setForeground(Color.WHITE);
        addComponent(scoreLabel);

        addComponent(spacer());

        // Buttons

        JButton playAgainButton = createButton("- Play Again -", optionFont, playAgainListener);
        JButton menuButton = createButton("- Main Menu -", optionFont, backToMenuListener);

        addComponent(playAgainButton);
        addComponent(menuButton);
    }

    private void initGBC() {
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(GUI_INSETS, GUI_INSETS, GUI_INSETS, GUI_INSETS);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
    }

    private JLabel createTitle(String title) {
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(PixelFont.loadFont(PixelFont.TITLE_SIZE));
        titleLabel.setForeground(Color.WHITE);
        return titleLabel;
    }

    private JButton createButton(String name, Font font, ActionListener listener) {
        JButton btn = new JButton(name);
        btn.setFont(font);
        btn.setForeground(Color.WHITE);
        btn.setBackground(COLOR_BUTTONS);
        btn.setPreferredSize(new Dimension(MAX_WIDTH_COMPONENT, MAX_HEIGHT_COMPONENT));
        btn.setFocusPainted(false);
        btn.addActionListener(listener);
        return btn;
    }

    private JLabel spacer() {
        return new JLabel(" ");
    }

    private void addComponent(Component component) {
        add(component, gbc);
        gbc.gridy++;
    }

    /**
     * Updates the panel with the player's win/loss status and score.
     */
    public void updateGameOverStatus(boolean isWinner, int score) {
        titleLabel.setText(isWinner ? "Victory!" : "Game Over");
        scoreLabel.setText("Score: " + score);
        repaint();
    }
}
