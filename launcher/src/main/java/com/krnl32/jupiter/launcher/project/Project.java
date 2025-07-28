package com.krnl32.jupiter.launcher.project;

public class Project {
	private final String name;
	private final String path;
	private final String engineVersion;

	public Project(String name, String path, String engineVersion) {
		this.name = name;
		this.path = path;
		this.engineVersion = engineVersion;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getEngineVersion() {
		return engineVersion;
	}
}
