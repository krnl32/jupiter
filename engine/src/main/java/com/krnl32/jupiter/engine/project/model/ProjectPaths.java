package com.krnl32.jupiter.engine.project.model;

import java.nio.file.Path;

public final class ProjectPaths {
	private final Path assetPath;
	private final Path assetRegistryPath;
	private final Path assetDatabasePath;

	private final String shaderPath;
	private final String scenePath;
	private final String spritesheetPath;
	private final String fontPath;
	private final String scriptPath;

	public ProjectPaths(Path assetPath, Path assetRegistryPath, Path assetDatabasePath, String shaderPath, String scenePath, String spritesheetPath, String fontPath, String scriptPath) {
		this.assetPath = assetPath;
		this.assetRegistryPath = assetRegistryPath;
		this.assetDatabasePath = assetDatabasePath;

		this.shaderPath = shaderPath;
		this.scenePath = scenePath;
		this.spritesheetPath = spritesheetPath;
		this.fontPath = fontPath;
		this.scriptPath = scriptPath;
	}

	public Path getAssetPath() {
		return assetPath;
	}

	public Path getAssetRegistryPath() {
		return assetRegistryPath;
	}

	public Path getAssetDatabasePath() {
		return assetDatabasePath;
	}

	public String getShaderPath() {
		return shaderPath;
	}

	public String getScenePath() {
		return scenePath;
	}

	public String getSpritesheetPath() {
		return spritesheetPath;
	}

	public String getFontPath() {
		return fontPath;
	}

	public String getScriptPath() {
		return scriptPath;
	}

	@Override
	public String toString() {
		return "ProjectPaths{" +
			"assetPath=" + assetPath +
			", assetRegistryPath=" + assetRegistryPath +
			", assetDatabasePath=" + assetDatabasePath +
			", shaderPath='" + shaderPath + '\'' +
			", scenePath='" + scenePath + '\'' +
			", spritesheetPath='" + spritesheetPath + '\'' +
			", fontPath='" + fontPath + '\'' +
			", scriptPath='" + scriptPath + '\'' +
			'}';
	}
}
