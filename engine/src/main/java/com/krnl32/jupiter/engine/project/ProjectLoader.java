package com.krnl32.jupiter.engine.project;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.model.Project;
import com.krnl32.jupiter.engine.utility.FileIO;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ProjectLoader {
	private static final String PROJECT_EXTENSION = ".jproject";

	public static Project load(Path projectDirectory) {
		File projectFile = getProjectFile(projectDirectory);
		if (projectFile == null) {
			Logger.error("ProjectLoader failed to GetProject({})", projectDirectory);
			return null;
		}

		Project project;
		try {
			project = ProjectSerializer.deserialize(new JSONObject(FileIO.readFileContent(Path.of(projectFile.getAbsolutePath()))));
		} catch (IOException e) {
			Logger.error(e.getMessage());
			return null;
		}

		return project;
	}

	private static File getProjectFile(Path projectDirectory) {
		File folder = new File(projectDirectory.toString());
		if (!folder.exists() || !folder.isDirectory()) {
			Logger.error("ProjectLoader failed to GetProject({}) Invalid", projectDirectory);
			return null;
		}

		File[] files = folder.listFiles((dir, name) -> name.endsWith(PROJECT_EXTENSION));
		if (files == null || files.length == 0) {
			Logger.error("ProjectLoader failed to GetProject({}) Project({}) File Not Found", projectDirectory, PROJECT_EXTENSION);
			return null;
		}

		return files[0];
	}
}
