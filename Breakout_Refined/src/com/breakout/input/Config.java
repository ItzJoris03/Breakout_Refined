package com.breakout.input;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private Properties props = new Properties();
	
	public Config(String filename) {
		try (FileInputStream fs = new FileInputStream(filename)) {
			this.props.load(fs);
		} catch(IOException e) {
			System.err.println("Error loading configuration file: " + e.getMessage());
		}
	}
	
	public int getInt(String key, int defaultValue) {
		return Integer.parseInt(this.props.getProperty(key, String.valueOf(defaultValue)));
	}
}
