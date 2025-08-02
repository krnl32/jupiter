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

	public static JProject createProject(String projectName, String path, String engineVersion, String template) {
		if (!Files.exists(Path.of(path))) {
			Logger.error("ProjectInitializer Failed to Create Project({}), Path({}) Not Found", projectName, path);
			return null;
		}

		String projectPath = path + "/" + projectName;
		if (!generateProjectDirectories(projectPath)) {
			Logger.error("ProjectInitializer Failed to Create Project({}, {}), Failed to Generate Project Directories", projectName, projectPath);
			return null;
		}

		Project project = generateProjectFile(projectName, projectPath, engineVersion, template);
		if (project == null) {
			Logger.error("ProjectInitializer Failed to Create Project({}, {}), Failed to Generate Project File", projectName, projectPath);
			return null;
		}

		return new JProject(project.getInfo().getProjectName(), projectPath, project.getInfo().getEngineVersion(), template);
	}

	private static boolean generateProjectDirectories(String projectPath) {
		try {
			// Generate Project Directory
			Files.createDirectories(Path.of(projectPath));

			// Generate Asset Directory
			Files.createDirectories(Path.of(projectPath + "/Assets"));
			Files.createDirectories(Path.of(projectPath + "/Assets/Textures"));
			Files.createFile(Path.of(projectPath + "/Assets/Textures/.keep"));
			Files.createDirectories(Path.of(projectPath + "/Assets/Shaders"));
			Files.createFile(Path.of(projectPath + "/Assets/Shaders/.keep"));
			Files.createDirectories(Path.of(projectPath + "/Assets/Scenes"));
			Files.createFile(Path.of(projectPath + "/Assets/Scenes/.keep"));
			Files.createDirectories(Path.of(projectPath + "/Assets/Spritesheets"));
			Files.createFile(Path.of(projectPath + "/Assets/Spritesheets/.keep"));
			Files.createDirectories(Path.of(projectPath + "/t/fonts"));
			Files.createFile(Path.of(projectPath + "/t/fonts/.keep"));
			Files.createDirectories(Path.of(projectPath + "/Assets/Scripts"));
			Files.createFile(Path.of(projectPath + "/Assets/Scripts/.keep"));
			return true;
		} catch (IOException e) {
			Logger.error("ProjectInitializer Failed to Create Project({}): {}", projectPath, e.getMessage());
			return false;
		}
	}

	private static Project generateProjectFile(String projectName, String projectPath, String engineVersion, String template) {
		Project project = new Project(
			new ProjectInfo(projectName, engineVersion, "JupiterEngine", "", template),
			new ProjectAuthor("", "", ""),
			new ProjectPaths("Assets", "Assets/Textures", "Assets/Shaders", "Assets/Scenes", "Assets/Spritesheets", "t/fonts", "Assets/Scripts"),
			new ProjectStartup(""),
			new ProjectMetadata(UUID.randomUUID(), System.currentTimeMillis())
		);

		try {
			FileIO.writeFileContent(projectPath + "/" + projectName + PROJECT_EXTENSION, ProjectSerializer.serialize(project).toString(4));
			return project;
		} catch (IOException e) {
			Logger.error("ProjectInitializer Failed to Create Project File({}): {}", projectName, e.getMessage());
			return null;
		}
	}
}
