package com.krnl32.jupiter.launcher.project;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.utility.FileIO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProjectManager {
	private final List<Project> projects;
	private final String configPath;

	public ProjectManager(String configPath) {
		this.projects = new ArrayList<>();
		this.configPath = configPath;

		loadProjects();
	}

	public void addProject(Project project) {
		projects.add(project);
		saveProjects();
	}

	public void removeProject(Project project) {
		projects.remove(project);
		saveProjects();
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void	reloadProjects() {
		this.projects.clear();
		this.loadProjects();
	}

	private void loadProjects() {
		if (!Files.exists(Path.of(configPath))) {
			Logger.error("ProjectManager Failed to Save Projects, ConfigFile({}) Doesn't Exist", configPath);
			return;
		}

		try {
			JSONObject projectFileData = new JSONObject(FileIO.readFileContent(configPath));

			JSONArray projectsData = projectFileData.getJSONArray("projects");
			if (projectsData != null) {
				for (int i = 0; i < projectsData.length(); i++) {
					JSONObject projectData = projectsData.getJSONObject(i);
					addProject(new Project(projectData.getString("name"), projectData.getString("path"), projectData.getString("engineVersion")));
				}
			}
		} catch (Exception e) {
			Logger.error("ProjectManager Failed to Load Projects: {}", e.getMessage());
		}
	}

	private void saveProjects() {
		JSONArray projectsData = new JSONArray();
		for (Project project : projects) {
			JSONObject projectData =
				new JSONObject()
					.put("name", project.getName())
					.put("path", project.getPath())
					.put("engineVersion", project.getEngineVersion());
			projectsData.put(projectData);
		}

		try {
			JSONObject projectFileData = new JSONObject().put("projects", projectsData);
			FileIO.writeFileContent(configPath, projectFileData.toString(4));
		} catch (Exception e) {
			Logger.error("ProjectManager Failed to Save Projects: {}", e.getMessage());
		}
	}
}
