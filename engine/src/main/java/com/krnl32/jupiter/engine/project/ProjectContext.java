package com.krnl32.jupiter.engine.project;

import com.krnl32.jupiter.engine.asset.AssetManager;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.model.Project;

public class ProjectContext {
	private static ProjectContext instance;
	private final String projectDirectory;
	private final Project project;
	private final AssetManager assetManager;

	private ProjectContext(String projectDirectory, Project project, AssetManager assetManager) {
		this.projectDirectory = projectDirectory;
		this.project = project;
		this.assetManager = assetManager;
	}

	public static void init(String projectDirectory, Project project, AssetManager assetManager) {
		instance = new ProjectContext(projectDirectory, project, assetManager);
	}

	public static ProjectContext getInstance() {
		if (instance == null) {
			Logger.critical("ProjectContext Not Initialized");
			return null;
		}
		return instance;
	}

	public String getProjectDirectory() {
		return projectDirectory;
	}

	public Project getProject() {
		return project;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public String getAssetDirectory() {
		return projectDirectory + "/" + project.getPaths().getAssetPath();
	}
}
