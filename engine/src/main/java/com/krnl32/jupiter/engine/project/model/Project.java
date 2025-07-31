package com.krnl32.jupiter.engine.project.model;

public final class Project {
	private final ProjectInfo info;
	private final ProjectAuthor author;
	private final ProjectPaths paths;
	private final ProjectStartup startup;
	private final ProjectMetadata metadata;

	public Project(ProjectInfo info, ProjectAuthor author, ProjectPaths paths, ProjectStartup startup, ProjectMetadata metadata) {
		this.info = info;
		this.author = author;
		this.paths = paths;
		this.startup = startup;
		this.metadata = metadata;
	}

	public ProjectInfo getInfo() {
		return info;
	}

	public ProjectAuthor getAuthor() {
		return author;
	}

	public ProjectPaths getPaths() {
		return paths;
	}

	public ProjectStartup getStartup() {
		return startup;
	}

	public ProjectMetadata getMetadata() {
		return metadata;
	}

	@Override
	public String toString() {
		return "Project{" +
			"info=" + info +
			", author=" + author +
			", paths=" + paths +
			", startup=" + startup +
			", metadata=" + metadata +
			'}';
	}
}
