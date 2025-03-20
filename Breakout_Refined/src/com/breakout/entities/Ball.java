package com.breakout.entities;

import com.breakout.GameBoard;
import com.breakout.input.Key;
import com.breakout.input.Keyboard;

public class Ball extends TexturedSprite {
	// Constants related to the Class
	public static final int TEXTURE_SIZE = 4;
	
	// Constants related to the Object
	private final int SPEED = 4;
	
	// Fields
	private int dy, dx;
	private boolean isMoving;
	
	public Ball(int x, int y, double scale) {
		super(x, y, TEXTURE_SIZE, TEXTURE_SIZE, scale, "/com/breakout/assets/images/Misc/sp_ball.png");
		
		this.dx = SPEED;
		this.dy = SPEED;
		this.isMoving = false;
	}
	
	// Getters and setters for dx and dy
	public int getDx() { return this.dx; }
	public int getDy() { return this.dy; }
	public void setDx(int dx) { this.dx = dx; }
	public void setDy(int dy) { this.dy = dy; }	

	@Override
	public void update(Keyboard keyboard) {
		// Move the ball to a certain direction once not moving yet.
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
		
		// Once the ball moves, update the direction of the ball and check bounce-offs.
        if(this.isMoving) {
        	this.setX(this.getX() + dx);
            this.setY(this.getY() + dy);

            // Bounce off the left and right borders
            if (this.getX() <= 0 || this.getX() + this.getWidth() >= GameBoard.WIDTH) dx = -dx; 
            
            // Bounce off the top borders
            if (this.getY() <= 0) dy = -dy;
        }
	}	

	/**
	 * Check if the ball collides with a sprite
	 * @param sprite
	 * @return boolean
	 */
	public boolean checkCollision(Sprite sprite) {
		// Check if the ball touches the sprite
	    if (getBounds().intersects(sprite.getBounds())) {
	    	
	    	// Calculates the overlap
	        int overlapLeft = (getX() + getWidth()) - sprite.getX();
	        int overlapRight = (sprite.getX() + sprite.getWidth()) - getX();
	        int overlapTop = (getY() + getHeight()) - sprite.getY();
	        int overlapBottom = (sprite.getY() + sprite.getHeight()) - getY();

	        // Calculate a minimum overlap
	        int minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

	        // Check collision from the top
	        if (minOverlap == overlapTop) {
	            this.dy = -Math.abs(this.dy);
	            this.setY(sprite.getY() - this.getHeight()); // Move the ball out of the object
	        } 
	        // Check collision from the bottom
	        else if (minOverlap == overlapBottom) {
	            this.dy = Math.abs(this.dy);
	            this.setY(sprite.getY() + sprite.getHeight()); // Move the ball out of the object
	        } 
	        // Check collision from the left
	        else if (minOverlap == overlapLeft) {
	            this.dx = -Math.abs(this.dx);
	            this.setX(sprite.getX() - this.getWidth()); // Move the ball out of the object
	        } 
	        // Check collision from the right
	        else if (minOverlap == overlapRight) {
	            this.dx = Math.abs(this.dx);
	            this.setX(sprite.getX() + sprite.getWidth()); // Move the ball out of the object
	        }

	        return true;
	    }
	    return false;
	}

}
