package com.breakout.input;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Unused Config class which would love the desired settings which can be adjusted by the user 
 * - I wanna finish this project as showcase on my portfolio, so I keep this in the codebase
 */
public class Config {
	private Properties props = new Properties();
	
	/**
	 * Loading the configuration file.
	 * @param filename
	 */
	public Config(String filename) {
		try (FileInputStream fs = new FileInputStream(filename)) {
			this.props.load(fs);
		} catch(IOException e) {
			System.err.println("Error loading configuration file: " + e.getMessage());
		}
	}
	
	/**
	 * Getting the value of the property string
	 * @param key
	 * @param defaultValue
	 * @return Integer
	 */
	public int getInt(String key, int defaultValue) {
		return Integer.parseInt(this.props.getProperty(key, String.valueOf(defaultValue)));
	}
}
