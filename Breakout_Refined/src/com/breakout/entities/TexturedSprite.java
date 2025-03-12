package com.breakout.entities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class TexturedSprite extends Sprite {
    private BufferedImage scaledTexture; // The scaled texture after extraction
    private double scale;  // Scale factor
    private int textureWidth, textureHeight;
    private int textureXOffset; // X offset for selecting texture (left or right part)

    public TexturedSprite(int x, int y, int textureWidth, int textureHeight, double scale) {
        super(x, y, (int)(textureWidth * scale), (int)(textureHeight * scale));
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.scale = scale;
        this.textureXOffset = 0;
    }
    
    public TexturedSprite(int x, int y, int textureWidth, int textureHeight, double scale, String texturePath) {
        super(x, y, (int)(textureWidth * scale), (int)(textureHeight * scale));
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.scale = scale;
        this.textureXOffset = 0;
        loadTexture(texturePath);
    }
    
    public TexturedSprite(int x, int y, int textureWidth, int textureHeight, double scale, String texturePath, int textureOffset) {
        super(x, y, (int)(textureWidth * scale), (int)(textureHeight * scale));
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.scale = scale;
        this.textureXOffset = textureOffset <= 0 ? textureWidth * textureOffset : textureOffset;
        loadTexture(texturePath);
    }
    
    public void setTexture(String texturePath) {
    	loadTexture(texturePath);
    }
    
    public void setTexture(String texturePath, int textureOffset) {
    	this.textureXOffset = textureOffset;
    	loadTexture(texturePath);
    }

    private void loadTexture(String texturePath) {
        try {
            // Load the full texture image
            BufferedImage fullTexture = ImageIO.read(getClass().getResource(texturePath));
            if (fullTexture != null) {
                // Extract one of the two textures from the image
                BufferedImage texturePart = fullTexture.getSubimage(textureXOffset, 0, textureWidth, textureHeight);

                // Now scale the texture part based on the provided scale
                int newWidth = (int) (textureWidth * scale);
                int newHeight = (int) (textureHeight * scale);

                // Create a scaled version of the texture
                Image scaledImage = texturePart.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                scaledTexture = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = scaledTexture.createGraphics();
                g.drawImage(scaledImage, 0, 0, null);
                g.dispose();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("⚠️ Failed to load texture: " + texturePath);
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        if (scaledTexture != null) {
            // Draw the scaled texture at the sprite's position
            graphics.drawImage(scaledTexture, getX(), getY(), null);
        } else {
            // Fallback: Draw a basic rectangle if texture fails to load
            graphics.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
