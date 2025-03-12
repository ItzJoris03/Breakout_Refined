package com.breakout.core;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public abstract class Engine extends JPanel {
	private GameState status;
	private static int fps;
	
	public Engine() {
		this.status = GameState.PAUSE;
		Engine.fps = 100;
	}
	
	public void startGame() {
		status = GameState.START;
		new Thread(this::engine).start();
	}
	public void resumeGame() { startGame(); }
	public void pauseGame() {
		status = GameState.PAUSE;
	}
	public void endGame() {
		status = GameState.END;
	}
	
	public void engine() {		
		System.out.println("Engine started!");
	    while (status == GameState.START) {
	        update();

	        try {
	            Thread.sleep(1000 / Engine.fps);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        // Ensure repaint happens on the EDT
	        SwingUtilities.invokeLater(this::repaint);
	    }
	    System.out.println("Engine stopped!");
	}
	
	public static int getFPS() { return Engine.fps; }
	public static void setFPS(int fps) { Engine.fps = fps; }
	
	public GameState getState() { return this.status; }
	
	public abstract void update();
}
