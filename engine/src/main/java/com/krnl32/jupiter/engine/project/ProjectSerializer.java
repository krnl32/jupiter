package com.krnl32.jupiter.engine.project;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.model.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Path;
import java.util.UUID;

public class ProjectSerializer {
	public static JSONObject serialize(Project project) {
		return new JSONObject()
			.put("info", serializeProjectInfo(project.getInfo()))
			.put("author", serializeProjectAuthor(project.getAuthor()))
			.put("paths", serializeProjectPaths(project.getPaths()))
			.put("startup", serializeProjectStartup(project.getStartup()))
			.put("metadata", serializeProjectMetadata(project.getMetadata()));
	}

	public static Project deserialize(JSONObject projectData) {
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

	private static JSONObject serializeProjectInfo(ProjectInfo projectInfo) {
		return new JSONObject()
			.put("projectName", projectInfo.getProjectName())
			.put("engineVersion", projectInfo.getEngineVersion())
			.put("createdWith", projectInfo.getCreatedWith())
			.put("description", projectInfo.getDescription())
			.put("template", projectInfo.getTemplate());
	}

	private static ProjectInfo deserializeProjectInfo(JSONObject projectInfoData) throws JSONException {
		return new ProjectInfo(
			projectInfoData.getString("projectName"),
			projectInfoData.getString("engineVersion"),
			projectInfoData.getString("createdWith"),
			projectInfoData.optString("description", ""),
			projectInfoData.getString("template")
		);
	}

	private static JSONObject serializeProjectAuthor(ProjectAuthor projectAuthor) {
		return new JSONObject()
			.put("name", projectAuthor.getName())
			.put("email", projectAuthor.getEmail())
			.put("organization", projectAuthor.getOrganization());
	}

	private static ProjectAuthor deserializeProjectAuthor(JSONObject projectAuthorData) {
		return new ProjectAuthor(
			projectAuthorData.optString("name", ""),
			projectAuthorData.optString("email", ""),
			projectAuthorData.optString("organization", "")
		);
	}

	private static JSONObject serializeProjectPaths(ProjectPaths projectPaths) {
		return new JSONObject()
			.put("asset", projectPaths.getAssetPath())
			.put("assetRegistry", projectPaths.getAssetRegistryPath())
			.put("assetDatabase", projectPaths.getAssetDatabasePath())

			.put("shader", projectPaths.getShaderPath())
			.put("scene", projectPaths.getScenePath())
			.put("spritesheet", projectPaths.getSpritesheetPath())
			.put("font", projectPaths.getFontPath())
			.put("script", projectPaths.getScriptPath());
	}

	private static ProjectPaths deserializeProjectPaths(JSONObject projectPathsData) throws JSONException {
		return new ProjectPaths(
			Path.of(projectPathsData.getString("asset")),
			Path.of(projectPathsData.getString("assetRegistry")),
			Path.of(projectPathsData.getString("assetDatabase")),

			projectPathsData.getString("shader"),
			projectPathsData.getString("scene"),
			projectPathsData.getString("spritesheet"),
			projectPathsData.getString("font"),
			projectPathsData.getString("script")
		);
	}

	private static JSONObject serializeProjectStartup(ProjectStartup projectStartup) {
		return new JSONObject()
			.put("scene", projectStartup.getSceneName());
	}

	private static ProjectStartup deserializeProjectStartup(JSONObject projectStartupData) {
		return new ProjectStartup(
			projectStartupData.optString("scene", "")
		);
	}

	private static JSONObject serializeProjectMetadata(ProjectMetadata projectMetadata) {
		return new JSONObject()
			.put("uuid", projectMetadata.getUUID())
			.put("lastModified", projectMetadata.getLastModified());
	}

	private static ProjectMetadata deserializeProjectMetadata(JSONObject projectMetadataData) throws JSONException {
		return new ProjectMetadata(
			UUID.fromString(projectMetadataData.getString("uuid")),
			projectMetadataData.getLong("lastModified")
		);
	}
}
