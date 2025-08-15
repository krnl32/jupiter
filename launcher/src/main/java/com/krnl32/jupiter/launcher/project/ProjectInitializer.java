package com.krnl32.jupiter.launcher.project;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectSerializer;
import com.krnl32.jupiter.engine.project.model.*;
import com.krnl32.jupiter.engine.utility.FileIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class ProjectInitializer {
	private static final String PROJECT_EXTENSION = ".jproject";
	private static final String ASSET_REGISTRY_EXTENSION = ".jregistry";
	private static final String ASSET_DATABASE_DIRECTORY = ".jmetadata";

	public static JProject createProject(String projectName, Path directoryPath, String engineVersion, String template) {
		if (!Files.exists(directoryPath) || !Files.isDirectory(directoryPath)) {
			Logger.error("ProjectInitializer Failed to Create Project({}), Path({}) Not Found", projectName, directoryPath);
			return null;
		}

		Path projectPath = directoryPath.resolve(projectName);
		if (!generateProjectDirectories(projectPath)) {
			Logger.error("ProjectInitializer Failed to Create Project({}, {}), Failed to Generate Project Directories", projectName, projectPath);
			return null;
		}

		Project project = generateProjectFile(projectName, projectPath, engineVersion, template);
		if (project == null) {
			Logger.error("ProjectInitializer Failed to Create Project({}, {}), Failed to Generate Project File", projectName, projectPath);
			return null;
		}

		return new JProject(project.getInfo().getProjectName(), projectPath.toString(), project.getInfo().getEngineVersion(), template);
	}

	private static boolean generateProjectDirectories(Path projectPath) {
		try {
			// Generate Project Directory
			Files.createDirectories(projectPath);

			// Generate Asset Directory
			Files.createDirectories(projectPath.resolve("Assets"));
			Files.createDirectories(projectPath.resolve("Assets").resolve(ASSET_DATABASE_DIRECTORY));
			return true;
		} catch (IOException e) {
			Logger.error("ProjectInitializer Failed to Create Project({}): {}", projectPath, e.getMessage());
			return false;
		}
	}

	private static Project generateProjectFile(String projectName, Path projectPath, String engineVersion, String template) {
		UUID uuid = UUID.randomUUID();
		long generationTime = System.currentTimeMillis();
		String assetRegistryPath = "Assets/" + projectName + ASSET_REGISTRY_EXTENSION;
		String assetDatabasePath = "Assets/" + ASSET_DATABASE_DIRECTORY;

		Project project = new Project(
			new ProjectInfo(projectName, engineVersion, "JupiterEngine", "", template),
			new ProjectAuthor("", "", ""),
			new ProjectPaths("Assets", assetRegistryPath, assetDatabasePath),
			new ProjectStartup(""),
			new ProjectMetadata(uuid, generationTime)
		);

		Path projectFilePath = projectPath.resolve(projectName + PROJECT_EXTENSION);
		try {
			FileIO.writeFileContent(projectFilePath, ProjectSerializer.serialize(project).toString(4));
			return project;
		} catch (IOException e) {
			Logger.error("ProjectInitializer Failed to Create Project File({}): {}", projectName, e.getMessage());
			return null;
		}
	}
}
