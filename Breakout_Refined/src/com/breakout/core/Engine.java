package com.breakout.core;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Custom Game Engine using the JPanel as base.
 */
public abstract class Engine extends JPanel {
	// Constants related by the Class
	private static final int DEFAULT_FPS = 100; // Recommended 100fps since the game's speed will be slow otherwise.
	
	// Fields
	private static int fps;
	private GameState status;
	
	public Engine() {
		this.status = GameState.PAUSE;
		Engine.fps = DEFAULT_FPS; 
	}
	
	/**
	 * Starts the game engine on a new thread.
	 */
	public void startGame() {
		status = GameState.START;
		
		/*
		 * A new thread is called since otherwise the frame will never be drawn, this problem will be fully explained in the report that is handed over.
		 * - Thanks with help of ChatGPT
		 */
		new Thread(this::engine).start();
	}

	// Functions to resume, pause and end the game
	public void resumeGame() { startGame(); }
	public void pauseGame() { status = GameState.PAUSE; }
	public void endGame() { status = GameState.END; }
	
	/**
	 * The engine function to loop whilst the engine has started and update the game by the defined FPS.
	 */
	public void engine() {		
		// Looping until the status is not GameState.START
	    while (status == GameState.START) {
	    	// Calling the update function
	        update();

	        // Stop the engine for a little bit using Thread.sleep
	        try {
	            Thread.sleep(1000 / Engine.fps);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        // Ensure repaint happens on the EDT - Thanks with help of ChatGPT
	        SwingUtilities.invokeLater(this::repaint);
	    }
	}
	
	// Functions to get and set the FPS
	public static int getFPS() { return Engine.fps; }
	public static void setFPS(int fps) { Engine.fps = fps; }
	
	// Function to get the current status of the engine (START, PAUSE or END)
	public GameState getState() { return this.status; }
	
	// Update function to be defined later using inheritance.
	public abstract void update();
}
