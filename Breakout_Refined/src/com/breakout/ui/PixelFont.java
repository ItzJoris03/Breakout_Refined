package com.breakout.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class PixelFont {
	// Constants related to the Class
	public static final String FILE = "src/com/breakout/assets/files/PressStart2P-Regular.ttf";
	public static final float DEFAULT_SIZE = 18;
	public static final float TITLE_SIZE = 60;
	public static final float SMALL_SIZE = 14;
	
	// Overload  functions for loading a custom font with default or custom size
	public static Font loadFont() { return readFont(PixelFont.DEFAULT_SIZE); }
	public static Font loadFont(int size) { return readFont((float)size); }
	public static Font loadFont(float size) { return readFont(size); }
	
	// Reads the font from the file and return it as normal Font
	private static Font readFont(float size) {
		try {
			File fontFile = new File(PixelFont.FILE);
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			return font.deriveFont(size);
		} catch(IOException | FontFormatException e) {
			e.printStackTrace();
			return new Font("Arial", Font.PLAIN, (int)size);
		}
	}
}
