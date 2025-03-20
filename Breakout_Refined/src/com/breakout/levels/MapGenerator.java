package com.breakout.levels;

import java.awt.Graphics2D;

import com.breakout.GameBoard;
import com.breakout.entities.Brick;
import com.breakout.entities.BrickType;

public class MapGenerator {
	// Constants related to the Object
    private final int BRICK_ROWS = 5;
    private final int BRICK_COLS = 10;
    private final double BRICK_SCALE = GameBoard.WIDTH / Brick.TEXTURE_WIDTH / BRICK_COLS;
    private final int GAP = (int) ((GameBoard.WIDTH - (int) (Brick.TEXTURE_WIDTH * BRICK_SCALE * BRICK_COLS))) / 2;

    // Fields
    private Brick[][] bricks;
    private int totalBricks;

    public MapGenerator() {
        this.bricks = new Brick[BRICK_ROWS][BRICK_COLS];
        this.totalBricks = BRICK_ROWS * BRICK_COLS;
        generateBricks();
    }

    /**
     * Generate the bricks
     */
    private void generateBricks() {
        for (int row = 0; row < BRICK_ROWS; row++) {
            BrickType type = getBrickTypeForRow(row);
            for (int col = 0; col < BRICK_COLS; col++) {
                this.bricks[row][col] = new Brick(
                    (int) (col * Brick.TEXTURE_WIDTH * BRICK_SCALE) + GAP,
                    (int) (row * Brick.TEXTURE_HEIGH * BRICK_SCALE),
                    BRICK_SCALE,
                    type
                );
            }
        }
    }

    /**
     * Returns bricktype by row
     * @param row
     * @return BrickType
     */
    private BrickType getBrickTypeForRow(int row) {
        switch (row) {
            case 0: return BrickType.GOLD;          
            case 1: return BrickType.SILVER;      
            case 2: return BrickType.REINFORCED_BLUE;
            default: return BrickType.BLUE; 
        }
    }

    public int getTotalBricks() { return totalBricks; }
    public Brick[][] getBricks() { return bricks; }

    /**
     * Removes the brick in a certain row and column
     * @param row
     * @param col
     */
    public void removeBrick(int row, int col) {
        if (row >= 0 && row < BRICK_ROWS && col >= 0 && col < BRICK_COLS) {
        	// Adding damage if the brick exists.
            Brick brick = this.bricks[row][col].addDamage();
            
            // if brick is null -> add points.
            if (brick == null) {
                ScoreManager.addScore(this.bricks[row][col].getPoints());
                totalBricks--;
            }
            
            // Replace the old brick with the new one.
            this.bricks[row][col] = brick;
        }
    }
    
    /**
     * Update function to update all bricks
     */
    public void update() {
		for(int i = 0; i < this.BRICK_ROWS; i++) {
			for(int j = 0; j < this.BRICK_COLS; j++) {
				if(this.bricks[i][j] != null)
					this.bricks[i][j].update(null);
			}
		}
	}
	
    /**
     * Draw function to draw all bricks
     * @param graphics
     */
	public void draw(Graphics2D graphics) {
		for(int i = 0; i < this.BRICK_ROWS; i++) {
			for(int j = 0; j < this.BRICK_COLS; j++) {
				if(this.bricks[i][j] != null) this.bricks[i][j].draw(graphics);
			}
		}
	}
}
