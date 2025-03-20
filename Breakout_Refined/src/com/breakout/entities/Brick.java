package com.breakout.entities;

import com.breakout.input.Keyboard;

public class Brick extends TexturedSprite {
	// Constants related to the Class
	// - All textures
	private static final String TEXTURE_FOLDER = "/com/breakout/assets/images/bricks/sp_brick_";
	private static final String TEXTURE_BLUE = "blue.png";
	private static final String TEXTURE_BLACK = "black.png";
	private static final String TEXTURE_GOLD = "gold_strip3.png";
	private static final String TEXTURE_GREEN = "green.png";
	private static final String TEXTURE_ORANGE = "orange.png";
	private static final String TEXTURE_PINK = "pink.png";
	private static final String TEXTURE_PURPLE = "purple.png";
	private static final String TEXTURE_RED = "red.png";
	private static final String TEXTURE_SILVER = "silver_strip2.png";
	private static final String TEXTURE_WHITE = "white.png";
	private static final String TEXTURE_REINFORCED_BLUE = "reinforced_blue.png";
	private static final String TEXTURE_REINFORCED_GREEN = "reinforced_green.png";
	private static final String TEXTURE_REINFORCED_RED = "reinforced_red.png";
	
	// - Default value
	private static final int DEFAULT_VALUE = 10;
	
	// - Brick difficulties / hardness
	private static final int HARDNESS_BLUE = 1;
	private static final int HARDNESS_BLACK = 1;
	private static final int HARDNESS_GOLD = 3;
	private static final int HARDNESS_GREEN = 1;
	private static final int HARDNESS_ORANGE = 1;
	private static final int HARDNESS_PINK = 1;
	private static final int HARDNESS_PURPLE = 1;
	private static final int HARDNESS_RED = 1;
	private static final int HARDNESS_SILVER = 2;
	private static final int HARDNESS_WHITE = 1;
	private static final int HARDNESS_REINFORCED_BLUE = 2;
	private static final int HARDNESS_REINFORCED_GREEN = 2;
	private static final int HARDNESS_REINFORCED_RED = 2;
	
	// - Texture width and height
	public static final int TEXTURE_WIDTH = 19;
	public static final int TEXTURE_HEIGH = 9;
	
	// Fields
	private int value, damage, hardness;
	private String texture;
	private BrickType type;

	public Brick(int x, int y, double scale, BrickType type) {
		super(x, y, TEXTURE_WIDTH, TEXTURE_HEIGH, scale);
		this.type = type;
		this.texture = TEXTURE_FOLDER;
		this.damage = 0;
		
		initBrick();
	}
	
	/**
	 * Initializing the brick by given type and calculating it's value
	 */
	private void initBrick() {
		switch(type) {
		case BrickType.BLUE:
			texture += TEXTURE_BLUE;
			hardness = HARDNESS_BLUE;
			break;
		case BrickType.BLACK:
			texture += TEXTURE_BLACK;
			hardness = HARDNESS_BLACK;
			break;
		case BrickType.RED:
			texture += TEXTURE_RED;
			hardness = HARDNESS_RED;
			break;
		case BrickType.GOLD:
			texture += TEXTURE_GOLD;
			hardness = HARDNESS_GOLD;
			break;
		case BrickType.GREEN:
			texture += TEXTURE_GREEN;
			hardness = HARDNESS_GREEN;
			break;
		case BrickType.ORANGE:
			texture += TEXTURE_ORANGE;
			hardness = HARDNESS_ORANGE;
			break;
		case BrickType.PINK:
			texture += TEXTURE_PINK;
			hardness = HARDNESS_PINK;
			break;
		case BrickType.PURPLE:
			texture += TEXTURE_PURPLE;
			hardness = HARDNESS_PURPLE;
			break;
		case BrickType.SILVER:
			texture += TEXTURE_SILVER;
			hardness = HARDNESS_SILVER;
			break;
		case BrickType.WHITE:
			texture += TEXTURE_WHITE;
			hardness = HARDNESS_WHITE;
			break;
		case BrickType.REINFORCED_BLUE:
			texture += TEXTURE_REINFORCED_BLUE;
			hardness = HARDNESS_REINFORCED_BLUE;
			break;
		case BrickType.REINFORCED_RED:
			texture += TEXTURE_REINFORCED_RED;
			hardness = HARDNESS_REINFORCED_RED;
			break;
		case BrickType.REINFORCED_GREEN:
			texture += TEXTURE_REINFORCED_GREEN;
			hardness = HARDNESS_REINFORCED_GREEN;
			break;
		default:
			break;
		}
		value = (int)(DEFAULT_VALUE * hardness); // Calculating the amount of points the user will get once the brick breaks.
		setTexture(texture);
	}
	
	public int getPoints() { return value; }
	
	/**
	 * Adds damage to the brick and return the new brick with the applied damage
	 * @return Brick
	 */
	public Brick addDamage() {
		damage++;
		
		// Adjust the reinforced textures to their equivalent normal brick textures.
		switch(type) {
		case BrickType.REINFORCED_BLUE:
			texture = TEXTURE_FOLDER + TEXTURE_BLUE;
			hardness = HARDNESS_BLUE;
			damage = 0;
			type = BrickType.BLUE;
			break;
		case BrickType.REINFORCED_RED:
			texture = TEXTURE_FOLDER + TEXTURE_RED;
			hardness = HARDNESS_RED;
			damage = 0;
			type = BrickType.RED;
			break;
		case BrickType.REINFORCED_GREEN:
			texture = TEXTURE_FOLDER + TEXTURE_GREEN;
			hardness = HARDNESS_GREEN;
			damage = 0;
			type = BrickType.GREEN;
			break;
		default:	break;
		}
		
		// Returning null if damage is equal to hardness
		if(damage == hardness) return null;
		
		// Setting their equivalent textures by damage (damage works as texture offset for certain textures)
		setTexture(texture, TEXTURE_WIDTH*damage);
		
		// Return this object after applied damage
		return this;
	}

	@Override
	public void update(Keyboard keyboard) { }
}
