package com.breakout.entities;

import com.breakout.GameBoard;
import com.breakout.input.Key;
import com.breakout.input.Keyboard;

public class Ball extends TexturedSprite {
	private final int SPEED = 4;
	private int dy, dx;
	private boolean isMoving;
	
	public final static int TEXTURE_SIZE = 4;

	public Ball(int x, int y, double scale) {
		super(x, y, TEXTURE_SIZE, TEXTURE_SIZE, scale, "/com/breakout/assets/images/Misc/sp_ball.png");
		
		this.dx = SPEED;
		this.dy = SPEED;
		this.isMoving = false;
	}
	
	public int getDx() { return this.dx; }
	public int getDy() { return this.dy; }
	public void setDx(int dx) { this.dx = dx; }
	public void setDy(int dy) { this.dy = dy; }	

	@Override
	public void update(Keyboard keyboard) {
		// Update the ball's position
		if(!this.isMoving) {
			this.dy = -SPEED;
			if(keyboard.isKeyDown(Key.Left)) {
				this.dx = -SPEED;
				this.isMoving = true;
			} else if(keyboard.isKeyDown(Key.Right)) {
				this.dx = SPEED;
				this.isMoving = true;
			}
		}
		
        if(this.isMoving) {
        	this.setX(this.getX() + dx);
            this.setY(this.getY() + dy);

            // Bounce off the left and right borders
            if (this.getX() <= 0 || this.getX() + this.getWidth() >= GameBoard.WIDTH) {
                dx = -dx; 
            }

            // Bounce off the top borders
            if (this.getY() <= 0) {
                dy = -dy; 
            }
            
            // Remove live when touching bottom
            if(this.getY() + this.getHeight() >= GameBoard.HEIGHT) {
            	
            }
        }
	}	
	
	public boolean checkCollision(Sprite sprite) {
	    if (getBounds().intersects(sprite.getBounds())) {
	        int overlapLeft = (getX() + getWidth()) - sprite.getX();
	        int overlapRight = (sprite.getX() + sprite.getWidth()) - getX();
	        int overlapTop = (getY() + getHeight()) - sprite.getY();
	        int overlapBottom = (sprite.getY() + sprite.getHeight()) - getY();

	        int minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

	        if (minOverlap == overlapTop) {
	            // Collision from the top
	            this.dy = -Math.abs(this.dy);
	            this.setY(sprite.getY() - this.getHeight()); // Move the ball out of the object
	        } 
	        else if (minOverlap == overlapBottom) {
	            // Collision from the bottom
	            this.dy = Math.abs(this.dy);
	            this.setY(sprite.getY() + sprite.getHeight()); // Move the ball out of the object
	        } 
	        else if (minOverlap == overlapLeft) {
	            // Collision from the left
	            this.dx = -Math.abs(this.dx);
	            this.setX(sprite.getX() - this.getWidth()); // Move the ball out of the object
	        } 
	        else if (minOverlap == overlapRight) {
	            // Collision from the right
	            this.dx = Math.abs(this.dx);
	            this.setX(sprite.getX() + sprite.getWidth()); // Move the ball out of the object
	        }

	        return true;
	    }
	    return false;
	}

}
