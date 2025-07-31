package com.krnl32.jupiter.engine.project;

import com.krnl32.jupiter.engine.asset.AssetManager;
import com.krnl32.jupiter.engine.project.model.Project;

public class ProjectContext {
	private static String projectDirectory;
	private static Project project;
	private static AssetManager assetManager;

	public static String getProjectDirectory() {
		return projectDirectory;
	}

	public static void setProjectDirectory(String projectDirectory) {
		ProjectContext.projectDirectory = projectDirectory;
	}

	public static Project getProject() {
		return project;
	}

	public static void setProject(Project project) {
		ProjectContext.project = project;
	}

	public static AssetManager getAssetManager() {
		return assetManager;
	}

	public static void setAssetManager(AssetManager assetManager) {
		ProjectContext.assetManager = assetManager;
	}

	public static String getAssetDirectory() {
		return projectDirectory + "/" + project.getPaths().getAssetPath();
	}
}
