package com.krnl32.jupiter.engine.project.model;

public final class ProjectPaths {
	private final String assetPath;
	private final String texturePath;
	private final String shaderPath;
	private final String scenePath;
	private final String spritesheetPath;
	private final String fontPath;
	private final String scriptPath;

	public ProjectPaths(String assetPath, String texturePath, String shaderPath, String scenePath, String spritesheetPath, String fontPath, String scriptPath) {
		this.assetPath = assetPath;
		this.texturePath = texturePath;
		this.shaderPath = shaderPath;
		this.scenePath = scenePath;
		this.spritesheetPath = spritesheetPath;
		this.fontPath = fontPath;
		this.scriptPath = scriptPath;
	}

	public String getAssetPath() {
		return assetPath;
	}

	public String getTexturePath() {
		return texturePath;
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
			"assetPath='" + assetPath + '\'' +
			", texturePath='" + texturePath + '\'' +
			", shaderPath='" + shaderPath + '\'' +
			", scenePath='" + scenePath + '\'' +
			", spritesheetPath='" + spritesheetPath + '\'' +
			", fontPath='" + fontPath + '\'' +
			", scriptPath='" + scriptPath + '\'' +
			'}';
	}
}
