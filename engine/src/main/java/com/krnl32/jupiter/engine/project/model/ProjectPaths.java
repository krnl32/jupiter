package com.krnl32.jupiter.engine.project.model;

public final class ProjectPaths {
	private final String assetPath;
	private final String assetRegistryPath;
	private final String assetDatabasePath;

	public ProjectPaths(String assetPath, String assetRegistryPath, String assetDatabasePath) {
		this.assetPath = assetPath;
		this.assetRegistryPath = assetRegistryPath;
		this.assetDatabasePath = assetDatabasePath;
	}

	public String getAssetPath() {
		return assetPath;
	}

	public String getAssetRegistryPath() {
		return assetRegistryPath;
	}

	public String getAssetDatabasePath() {
		return assetDatabasePath;
	}

	@Override
	public String toString() {
		return "ProjectPaths{" +
			"assetPath='" + assetPath + '\'' +
			", assetRegistryPath='" + assetRegistryPath + '\'' +
			", assetDatabasePath='" + assetDatabasePath + '\'' +
			'}';
	}
}
