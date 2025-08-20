package com.krnl32.jupiter.editor.build;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BuildContext {
	private BuildSettings settings;
	private Path outputDirectory;
	private final Map<String, Object> userData = new HashMap<>();

	public BuildSettings getSettings() {
		return settings;
	}

	public void setSettings(BuildSettings settings) {
		this.settings = settings;
	}

	public Path getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(Path outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public Object getUserData(String key) {
		return userData.get(key);
	}

	public void setUserData(String key, Object value) {
		userData.put(key, value);
	}
}
