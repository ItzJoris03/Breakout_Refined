package com.breakout.ui;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.breakout.Main;

import java.awt.*;
import java.awt.event.ActionListener;

public class GameMenu extends JPanel {
    private static final int GUI_INSETS = 10;
	private static final int DEFAULT_INSETS = 5;
	private static final int MAX_WIDTH_COMPONENT = 250;
	private static final int MAX_HEIGHT_COMPONENT = 50;
	
	private static final Color COLOR_BUTTONS = new Color(50, 50, 50); 
	
	private GridBagConstraints gbc;
	private JTextField nameField;
	
	public GameMenu(ActionListener startGameListener) {
		setLayout(new GridBagLayout());
		setBackground(Main.BG_COLOR);
		
		initGBC();
		
		Font optionFont = PixelFont.loadFont();
		
		// Title
		addComponent(createTitle("Breakout"));
		addComponent(createTitle("Refined"));
		
		// Add two spacers for better GUI
		addComponent(spacer());
		addComponent(spacer());
		
		DifficultySelector difficultySelector = new DifficultySelector();
		addComponent(difficultySelector);
		
		addComponent(spacer());		
		
		this.createNameComponent();
		     
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        
        JButton playButton = createButton("-  Play  -", optionFont, startGameListener);
//        playButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String playerName = nameField.getText();
//                String selectedDifficulty = difficultySelector.getSelectedDifficulty();
//                JOptionPane.showMessageDialog(GameMenu.this, "Starting game for " + playerName + " on " + selectedDifficulty + " difficulty!");
//            }
//        });
        
        ActionListener dummy = e -> System.out.println("Button Pressed");
        
        JButton settingsButton = createButton("- Settings -", optionFont, dummy);
        
        
        GridBagConstraints buttonGBC = new GridBagConstraints();
        buttonGBC.insets = new Insets(0, DEFAULT_INSETS, 0, DEFAULT_INSETS);
        buttonGBC.gridx = 0;
        buttonPanel.add(playButton, buttonGBC);
        
        buttonGBC.gridx = 1;
        buttonPanel.add(settingsButton, buttonGBC);
        addComponent(buttonPanel);
	}
	
	private JLabel spacer() {
		return new JLabel(" ");
	}
	
	private void initGBC() {
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(GUI_INSETS, GUI_INSETS, GUI_INSETS, GUI_INSETS);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.CENTER;
	}
	
	private void createNameComponent() {
	    Font textFieldFont = PixelFont.loadFont(PixelFont.SMALL_SIZE);
	    
	    // Input + Name Label Panel
	    JPanel namePanel = new JPanel(new GridBagLayout());
	    namePanel.setOpaque(false);
	    namePanel.setPreferredSize(new Dimension(MAX_WIDTH_COMPONENT*2, MAX_HEIGHT_COMPONENT));

	    // Constraints for the components
	    GridBagConstraints nameGBC = new GridBagConstraints();

	    // Name Label
	    JLabel nameLabel = new JLabel("Enter Name:");
	    nameLabel.setFont(PixelFont.loadFont());
	    nameLabel.setForeground(Color.WHITE);
	    
	    // Add Name Label to the left
	    nameGBC.gridx = 0;
	    nameGBC.weightx = 0; // Minimal space for the label
	    namePanel.add(nameLabel, nameGBC);
	    
	    // Name Input Field (Limited to 3 Letters)
	    this.nameField = createNameField(textFieldFont, 3);
	    
	    // Add Name Field to the right, taking available space
	    nameGBC.gridx = 1;
	    nameGBC.weightx = 1.0; // Give more space to the input field
	    nameGBC.anchor = GridBagConstraints.EAST;
	    namePanel.add(this.nameField, nameGBC);
	    
	    addComponent(namePanel);
	    addComponent(spacer());
	}



	private JTextField createNameField(Font font, int size) {
		JTextField textField = new JTextField(MAX_WIDTH_COMPONENT/font.getSize());
		textField.setFont(font);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setForeground(Color.white);
		textField.setOpaque(false);
		
		textField.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createMatteBorder(0,  0, 2, 0, Color.white),
			BorderFactory.createEmptyBorder(DEFAULT_INSETS, DEFAULT_INSETS, DEFAULT_INSETS, DEFAULT_INSETS)
		));
		textField.setCaretColor(Color.white);
		
        limitTextField(textField, 3);
        return textField;
	}

	private JLabel createTitle(String title) {
		JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
		titleLabel.setFont(PixelFont.loadFont(PixelFont.TITLE_SIZE));
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
	
	private void addComponent(Component component) {
		add(component, gbc);
		gbc.gridy++;
	}
	
	private void limitTextField(JTextField textField, int maxLength) {
	    ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
	        @Override
	        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
	            if ((fb.getDocument().getLength() + text.length()) <= maxLength) {
	                super.insertString(fb, offset, text.toUpperCase(), attr);
	            }
	        }

	        @Override
	        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
	            if ((fb.getDocument().getLength() - length + text.length()) <= maxLength) {
	                super.replace(fb, offset, length, text.toUpperCase(), attr); 
	            }
	        }
	    });
	}
	
	public String getPlayerName() {
		return nameField.getText().trim();
	}
	
	public String getPlayerInitials() {
	    String[] names = nameField.getText().trim().split("\\s+");
	    StringBuilder initials = new StringBuilder();

	    for (String name : names) {
	        if (!name.isEmpty()) {
	            initials.append(Character.toUpperCase(name.charAt(0)));
	        }
	    }

	    return initials.toString();
	}

}
