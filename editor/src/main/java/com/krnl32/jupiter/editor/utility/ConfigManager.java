package com.krnl32.jupiter.editor.utility;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private static final Properties properties = new Properties();

	static {
		String environment = System.getenv("environment");
		if (environment == null) {
			environment = "development";
		}

		if (!environment.equals("development") && !environment.equals("production")) {
			throw new RuntimeException("Invalid Environment: " + environment);
		}

		try (InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("config/" + environment + ".properties")) {
			if (inputStream == null) {
				throw new RuntimeException("Config Resource File Not Found: config/" + environment + ".properties");
			}
			properties.load(inputStream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getString(String key) {
		return properties.getProperty(key);
	}

	public static int getInt(String key) {
		return Integer.parseInt(properties.getProperty(key));
	}

	public static float getFloat(String key) {
		return Float.parseFloat(properties.getProperty(key));
	}

	public static double getDouble(String key) {
		return Double.parseDouble(properties.getProperty(key));
	}

	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(properties.getProperty(key));
	}
}
