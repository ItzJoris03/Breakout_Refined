package com.breakout.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.breakout.input.Keyboard;

public abstract class Sprite {
	private int x, y, width, height;
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public int getWidth() { return this.width; }
	public int getHeight() { return this.height; }
	public void setX(int x) { this.x = x; };
	public void setY(int y) { this.y = y; };
	public void setWidth(int width) { this.width = width; };
	public void setHeight(int height) { this.height = height; };
	
	public Sprite(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public abstract void update(Keyboard keyboard);
	public abstract void draw(Graphics2D graphics);
	
	public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
