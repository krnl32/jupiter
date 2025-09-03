package com.krnl32.jupiter.engine.project;

import com.krnl32.jupiter.engine.asset.core.AssetManager;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.model.Project;

import java.nio.file.Path;

public class ProjectContext {
	private static ProjectContext instance;
	private final Path projectDirectory;
	private final Project project;
	private final AssetManager assetManager;

	private ProjectContext(Path projectDirectory, Project project, AssetManager assetManager) {
		this.projectDirectory = projectDirectory;
		this.project = project;
		this.assetManager = assetManager;
	}

	public static void init(Path projectDirectory, Project project, AssetManager assetManager) {
		instance = new ProjectContext(projectDirectory, project, assetManager);
	}

	public static ProjectContext getInstance() {
		if (instance == null) {
			Logger.critical("ProjectContext Not Initialized");
			return null;
		}

		return instance;
	}

	public Path getProjectDirectory() {
		return projectDirectory;
	}

	public Project getProject() {
		return project;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public Path getAssetDirectory() {
		return projectDirectory.resolve(project.getPaths().getAssetPath());
	}

	public Path getAssetRegistryPath() {
		return projectDirectory.resolve(project.getPaths().getAssetRegistryPath());
	}

	public Path getAssetDatabaseDirectory() {
		return projectDirectory.resolve(project.getPaths().getAssetDatabasePath());
	}
}
