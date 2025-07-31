package com.krnl32.jupiter.engine.project.model;

public final class ProjectInfo {
	private final String projectName;
	private final String engineVersion;
	private final String createdWith;
	private final String description;

	public ProjectInfo(String projectName, String engineVersion, String createdWith, String description) {
		this.projectName = projectName;
		this.engineVersion = engineVersion;
		this.createdWith = createdWith;
		this.description = description;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getEngineVersion() {
		return engineVersion;
	}

	public String getCreatedWith() {
		return createdWith;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "ProjectInfo{" +
			"projectName='" + projectName + '\'' +
			", engineVersion='" + engineVersion + '\'' +
			", createdWith='" + createdWith + '\'' +
			", description='" + description + '\'' +
			'}';
	}
}
