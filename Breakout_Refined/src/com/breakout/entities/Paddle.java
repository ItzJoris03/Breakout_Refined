package com.breakout.entities;

import com.breakout.GameBoard;
import com.breakout.core.Engine;
import com.breakout.input.Key;
import com.breakout.input.Keyboard;

public class Paddle extends TexturedSprite implements IAnimate {
	// Constants related to the Class
	private static final String TEXTURE = "/com/breakout/assets/images/paddle/sp_paddle_strip2.png";
    private static final int TOTAL_TEXTURES = 2;
    private static final int SPEED = 5;
    public static final int TEXTURE_WIDTH = 34;
    public static final int TEXTURE_HEIGHT = 18;
    private static final int ANIMATION_UPDATE_TIMING = 8;
    
    // Fields
    private int currentTexture, tickcount;

	public Paddle(int x, int y, double scale) {
        super(x, y, TEXTURE_WIDTH, TEXTURE_HEIGHT, scale, TEXTURE);
        this.tickcount = 0;
        this.currentTexture = 0;
    }

    @Override
    public void update(Keyboard keyboard) {
    	tickcount++;
    	
    	// Updating texture after a certain tickcount
    	if(tickcount % (Engine.getFPS()/ANIMATION_UPDATE_TIMING) == 0) updateAnimation();
    	
    	// Move paddle by keyboard input to the left or right and calculate if it touches the frame or not to limit the movement
        if (keyboard.isKeyDown(Key.Left) && this.getX() > 0) {
            this.setX(this.getX() - SPEED);
        } else if (keyboard.isKeyDown(Key.Right) && this.getX() + this.getWidth() < GameBoard.WIDTH) {
            this.setX(this.getX() + SPEED);
        }
    }

    /**
     * Function to update the animation of the paddle
     */
	@Override
	public void updateAnimation() {
		currentTexture = currentTexture + 1 == TOTAL_TEXTURES ? 0 : currentTexture+1;
		this.setTexture(TEXTURE, TEXTURE_WIDTH*currentTexture);
	}
}

