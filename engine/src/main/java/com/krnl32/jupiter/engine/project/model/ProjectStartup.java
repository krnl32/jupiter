package com.krnl32.jupiter.engine.project.model;

public final class ProjectStartup {
	private final String sceneName;

	public ProjectStartup(String sceneName) {
		this.sceneName = sceneName;
	}

	public String getSceneName() {
		return sceneName;
	}

	@Override
	public String toString() {
		return "ProjectStartup{" +
			"sceneName='" + sceneName + '\'' +
			'}';
	}
}
