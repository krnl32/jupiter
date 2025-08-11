package com.krnl32.jupiter.engine.project.model;

public final class ProjectStartup {
	private final String scenePath;

	public ProjectStartup(String scenePath) {
		this.scenePath = scenePath;
	}

	public String getScenePath() {
		return scenePath;
	}

	@Override
	public String toString() {
		return "ProjectStartup{" +
			"scenePath='" + scenePath + '\'' +
			'}';
	}
}
