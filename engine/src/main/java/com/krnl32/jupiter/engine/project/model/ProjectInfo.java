package com.krnl32.jupiter.engine.project.model;

public final class ProjectInfo {
	private final String projectName;
	private final String engineVersion;
	private final String createdWith;
	private final String description;
	private final String template;

	public ProjectInfo(String projectName, String engineVersion, String createdWith, String description, String template) {
		this.projectName = projectName;
		this.engineVersion = engineVersion;
		this.createdWith = createdWith;
		this.description = description;
		this.template = template;
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

	public String getTemplate() {
		return template;
	}

	@Override
	public String toString() {
		return "ProjectInfo{" +
			"projectName='" + projectName + '\'' +
			", engineVersion='" + engineVersion + '\'' +
			", createdWith='" + createdWith + '\'' +
			", description='" + description + '\'' +
			", template='" + template + '\'' +
			'}';
	}
}
