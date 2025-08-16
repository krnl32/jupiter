package com.krnl32.jupiter.engine.asset.handle;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public final class AssetMetadata {
	private static final int METADATA_VERSION = 1;

	private final int version;
	private final AssetId assetId;
	private final AssetType assetType;
	private final String assetPath;
	private final String importerName;
	private final Map<String, Object> importSettings;
	private final long lastModified;

	public AssetMetadata(AssetId assetId, AssetType assetType, String assetPath, String importerName, Map<String, Object> importSettings, long lastModified) {
		this.version = METADATA_VERSION;
		this.assetId = assetId;
		this.assetType = assetType;
		this.assetPath = assetPath;
		this.importerName = importerName;
		this.importSettings = importSettings;
		this.lastModified = lastModified;
	}

	public AssetMetadata(int version, AssetId assetId, AssetType assetType, String assetPath, String importerName, Map<String, Object> importSettings, long lastModified) {
		this.version = version;
		this.assetId = assetId;
		this.assetType = assetType;
		this.assetPath = assetPath;
		this.importerName = importerName;
		this.importSettings = importSettings;
		this.lastModified = lastModified;
	}

	public int getVersion() {
		return version;
	}

	public AssetId getAssetId() {
		return assetId;
	}

	public AssetType getAssetType() {
		return assetType;
	}

	public String getAssetPath() {
		return assetPath;
	}

	public String getImporterName() {
		return importerName;
	}

	public Map<String, Object> getImportSettings() {
		return Collections.unmodifiableMap(importSettings);
	}

	public long getLastModified() {
		return lastModified;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		AssetMetadata that = (AssetMetadata) o;
		return version == that.version && lastModified == that.lastModified && Objects.equals(assetId, that.assetId) && assetType == that.assetType && Objects.equals(assetPath, that.assetPath) && Objects.equals(importerName, that.importerName) && Objects.equals(importSettings, that.importSettings);
	}

	@Override
	public int hashCode() {
		return Objects.hash(version, assetId, assetType, assetPath, importerName, importSettings, lastModified);
	}

	@Override
	public String toString() {
		return "AssetMetadata{" +
			"version=" + version +
			", assetId=" + assetId +
			", assetType=" + assetType +
			", assetPath='" + assetPath + '\'' +
			", importerName='" + importerName + '\'' +
			", importSettings=" + importSettings +
			", lastModified=" + lastModified +
			'}';
	}
}
