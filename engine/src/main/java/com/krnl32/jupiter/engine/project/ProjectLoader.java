package com.krnl32.jupiter.engine.project;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.model.Project;
import com.krnl32.jupiter.engine.utility.FileIO;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class ProjectLoader {
	private static final String PROJECT_EXTENSION = ".jproj";

	public static Project load(String projectPath) {
		File projectFile = getProjectFile(projectPath);
		if (projectFile == null) {
			Logger.error("ProjectLoader failed to GetProject({})", projectPath);
			return null;
		}

		Project project;
		try {
			ProjectSerializer projectSerializer = new ProjectSerializer();
			project = projectSerializer.deserialize(new JSONObject(FileIO.readFileContent(projectFile.getAbsolutePath())));
		} catch (IOException e) {
			Logger.error(e.getMessage());
			return null;
		}

		return project;
	}

	private static File getProjectFile(String projectPath) {
		File folder = new File(projectPath);
		if (!folder.exists() || !folder.isDirectory()) {
			Logger.error("ProjectLoader failed to GetProject({}) Invalid", projectPath);
			return null;
		}

		File[] files = folder.listFiles((dir, name) -> name.endsWith(PROJECT_EXTENSION));
		if (files == null || files.length == 0) {
			Logger.error("ProjectLoader failed to GetProject({}) Project({}) File Not Found", projectPath, PROJECT_EXTENSION);
			return null;
		}

		return files[0];
	}
}
