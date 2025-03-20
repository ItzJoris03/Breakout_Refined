package com.breakout.entities;

import com.breakout.input.Keyboard;

public class Hearts extends TexturedSprite {
	// Constants related to the Class
	private static final String TEXTURE = "/com/breakout/assets/images/UI/sp_heart_strip3.png";
	private static final int MAX_TOTAL = 2;
	public static final int TEXTURE_WIDTH = 39;
	public static final int TEXTURE_HEIGHT = 12;
	
	// Fields
	private int total;
	
	public Hearts(int x, int y, double scale) {
		super(x, y, TEXTURE_WIDTH, TEXTURE_HEIGHT, scale);
		initHearts();
	}
	
	public void initHearts() {
		total = MAX_TOTAL;
		this.setTexture(TEXTURE, TEXTURE_WIDTH*total);
	}
	
	public void reset() { initHearts(); }
	
	public void addHeart() {
		total += total == MAX_TOTAL ? 0 : 1;
		this.setTexture(TEXTURE, TEXTURE_WIDTH*total);
	}
	
	public void removeHeart() {
		total -= total == 0 ? 0 : 1;
		this.setTexture(TEXTURE, TEXTURE_WIDTH*total);
	}
	
	public int getTotalHearts() { return total; }

	@Override
	public void update(Keyboard keyboard) {  }

}
