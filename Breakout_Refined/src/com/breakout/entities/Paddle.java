package com.breakout.entities;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.breakout.GameBoard;
import com.breakout.core.Engine;
import com.breakout.input.Key;
import com.breakout.input.Keyboard;
import com.breakout.physics.IAnimate;
import com.breakout.ui.PixelFont;

public class Paddle extends TexturedSprite implements IAnimate {
    private static final int SPEED = 5;
    public static final int TEXTURE_WIDTH = 34;
    public static final int TEXTURE_HEIGHT = 18;
    
    private static final String TEXTURE = "/com/breakout/assets/images/paddle/sp_paddle_strip2.png";
    private static final int TOTAL_TEXTURES = 2;
    private int currentTexture;
    
    private String initials;

;
	private int tickcount;

	public Paddle(int x, int y, double scale) {
        super(x, y, TEXTURE_WIDTH, TEXTURE_HEIGHT, scale, TEXTURE);
        initials = "";
        tickcount = 0;
        currentTexture = 0;
    }
    
    public void setInitials(String initials) {
		this.initials = initials;
	}
    public String getInitials() {
		return initials;
	}

    @Override
    public void update(Keyboard keyboard) {
    	tickcount++;
    	if(tickcount % (Engine.getFPS()/8) == 0) updateAnimation();
    	
        if (keyboard.isKeyDown(Key.Left) && this.getX() > 0) {
            this.setX(this.getX() - SPEED);
        } else if (keyboard.isKeyDown(Key.Right) && this.getX() + this.getWidth() < GameBoard.WIDTH) {
            this.setX(this.getX() + SPEED);
        }
    }
    
    @Override
    public void draw(Graphics2D g) {
        super.draw(g); // Draw the paddle texture first

        if (!initials.isEmpty()) {
            g.setFont(PixelFont.loadFont(16)); // Set font
            g.setColor(new Color(218, 165, 32));

            FontMetrics metrics = g.getFontMetrics();
            int textWidth = metrics.stringWidth(initials);
            int textHeight = metrics.getHeight();

            int textX = this.getX() + (this.getWidth() - textWidth) / 2;
            int textY = this.getY() + (this.getHeight()/2 + textHeight) / 2; 

            g.drawString(initials, textX, textY);
        }
    }

	@Override
	public void updateAnimation() {
		currentTexture = currentTexture + 1 == TOTAL_TEXTURES ? 0 : currentTexture+1;
		this.setTexture(TEXTURE, TEXTURE_WIDTH*currentTexture);
	}
}

