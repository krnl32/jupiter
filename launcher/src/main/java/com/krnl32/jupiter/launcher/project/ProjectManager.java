package com.krnl32.jupiter.launcher.project;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.utility.FileIO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectManager {
	private final List<JProject> projects;
	private final Path configFilePath;

	public ProjectManager() {
		this.projects = new ArrayList<>();
		this.configFilePath = Paths.get(System.getProperty("user.home"), ".jupiter", "launcher", "projects.json");

		if (!initConfig()) {
			Logger.error("ProjectManager Failed to Generate Config File({})", configFilePath);
			return;
		}

		loadProjects();
	}

	public void addProject(JProject project) {
		projects.add(project);
		saveProjects();
	}

	public void removeProject(JProject project) {
		projects.remove(project);
		saveProjects();
	}

	public List<JProject> getProjects() {
		return Collections.unmodifiableList(projects);
	}

	public void reloadProjects() {
		projects.clear();
		loadProjects();
	}

	private void loadProjects() {
		if (!Files.exists(configFilePath)) {
			Logger.error("ProjectManager Failed to Load Projects, ConfigFile({}) Doesn't Exist", configFilePath);
			return;
		}

		try {
			JSONObject projectFileData = new JSONObject(FileIO.readFileContent(configFilePath.toString()));

			JSONArray projectsData = projectFileData.getJSONArray("projects");
			if (projectsData != null) {
				for (int i = 0; i < projectsData.length(); i++) {
					JSONObject projectData = projectsData.getJSONObject(i);
					projects.add(new JProject(projectData.getString("name"), projectData.getString("path"), projectData.getString("engineVersion"), projectData.getString("template")));
				}
			}
		} catch (Exception e) {
			Logger.error("ProjectManager Failed to Load Projects: {}", e.getMessage());
		}
	}

	private void saveProjects() {
		JSONArray projectsData = new JSONArray();
		for (JProject project : projects) {
			JSONObject projectData =
				new JSONObject()
					.put("name", project.getName())
					.put("path", project.getPath())
					.put("engineVersion", project.getEngineVersion())
					.put("template", project.getTemplate());
			projectsData.put(projectData);
		}

		try {
			JSONObject projectFileData = new JSONObject().put("projects", projectsData);
			FileIO.writeFileContent(configFilePath.toString(), projectFileData.toString(4));
		} catch (Exception e) {
			Logger.error("ProjectManager Failed to Save Projects: {}", e.getMessage());
		}
	}

	private boolean initConfig() {
		try {
			Files.createDirectories(configFilePath.getParent());
			if (Files.notExists(configFilePath)) {
				Files.createFile(configFilePath);
				FileIO.writeFileContent(configFilePath.toString(), new JSONObject().put("projects", new JSONArray()).toString(4));
			}
		} catch (Exception e) {
			Logger.error("ProjectManager Failed to InitConfig({})", configFilePath);
			return false;
		}
		return true;
	}
}
