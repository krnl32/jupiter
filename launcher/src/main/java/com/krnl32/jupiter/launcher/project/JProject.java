package com.krnl32.jupiter.launcher.project;

public class JProject {
	private final String name;
	private final String path;
	private final String engineVersion;
	private final String template;

	public JProject(String name, String path, String engineVersion, String template) {
		this.name = name;
		this.path = path;
		this.engineVersion = engineVersion;
		this.template = template;
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

	public String getTemplate() {
		return template;
	}
}
