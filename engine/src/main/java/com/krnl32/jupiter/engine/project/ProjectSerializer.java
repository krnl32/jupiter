package com.krnl32.jupiter.engine.project;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.model.*;
import org.json.JSONObject;

import java.util.UUID;

public class ProjectSerializer {
	public JSONObject serialize(Project project) {
		return new JSONObject()
			.put("info", serializeProjectInfo(project.getInfo()))
			.put("author", serializeProjectAuthor(project.getAuthor()))
			.put("paths", serializeProjectPaths(project.getPaths()))
			.put("startup", serializeProjectStartup(project.getStartup()))
			.put("metadata", serializeProjectMetadata(project.getMetadata()));
	}

	public Project deserialize(JSONObject projectData) {
		try {
			return new Project(
				deserializeProjectInfo(projectData.getJSONObject("info")),
				deserializeProjectAuthor(projectData.getJSONObject("author")),
				deserializeProjectPaths(projectData.getJSONObject("paths")),
				deserializeProjectStartup(projectData.getJSONObject("startup")),
				deserializeProjectMetadata(projectData.getJSONObject("metadata"))
			);
		} catch (Exception e) {
			Logger.error("ProjectSerializer Failed to Deserialize: {}", e.getMessage());
			return null;
		}
	}

	private JSONObject serializeProjectInfo(ProjectInfo projectInfo) {
		return new JSONObject()
			.put("projectName", projectInfo.getProjectName())
			.put("engineVersion", projectInfo.getEngineVersion())
			.put("createdWith", projectInfo.getCreatedWith())
			.put("description", projectInfo.getDescription());
	}

	private ProjectInfo deserializeProjectInfo(JSONObject projectInfoData) {
		return new ProjectInfo(
			projectInfoData.getString("projectName"),
			projectInfoData.getString("engineVersion"),
			projectInfoData.getString("createdWith"),
			projectInfoData.getString("description")
		);
	}

	private JSONObject serializeProjectAuthor(ProjectAuthor projectAuthor) {
		return new JSONObject()
			.put("name", projectAuthor.getName())
			.put("email", projectAuthor.getEmail())
			.put("organization", projectAuthor.getOrganization());
	}

	private ProjectAuthor deserializeProjectAuthor(JSONObject projectAuthorData) {
		return new ProjectAuthor(
			projectAuthorData.getString("name"),
			projectAuthorData.getString("email"),
			projectAuthorData.getString("organization")
		);
	}

	private JSONObject serializeProjectPaths(ProjectPaths projectPaths) {
		return new JSONObject()
			.put("asset", projectPaths.getAssetPath())
			.put("texture", projectPaths.getTexturePath())
			.put("shader", projectPaths.getShaderPath())
			.put("scene", projectPaths.getScenePath())
			.put("spritesheet", projectPaths.getSpritesheetPath())
			.put("font", projectPaths.getFontPath())
			.put("script", projectPaths.getScriptPath());
	}

	private ProjectPaths deserializeProjectPaths(JSONObject projectPathsData) {
		return new ProjectPaths(
			projectPathsData.getString("asset"),
			projectPathsData.getString("texture"),
			projectPathsData.getString("shader"),
			projectPathsData.getString("scene"),
			projectPathsData.getString("spritesheet"),
			projectPathsData.getString("font"),
			projectPathsData.getString("script")
		);
	}

	private JSONObject serializeProjectStartup(ProjectStartup projectStartup) {
		return new JSONObject()
			.put("scene", projectStartup.getSceneName());
	}

	private ProjectStartup deserializeProjectStartup(JSONObject projectStartupData) {
		return new ProjectStartup(
			projectStartupData.getString("scene")
		);
	}

	private JSONObject serializeProjectMetadata(ProjectMetadata projectMetadata) {
		return new JSONObject()
			.put("uuid", projectMetadata.getUUID())
			.put("lastModified", projectMetadata.getLastModified());
	}

	private ProjectMetadata deserializeProjectMetadata(JSONObject projectMetadataData) {
		return new ProjectMetadata(
			UUID.fromString(projectMetadataData.getString("uuid")),
			projectMetadataData.getLong("lastModified")
		);
	}
}
