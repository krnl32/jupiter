package com.krnl32.jupiter.engine.asset.handle;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

public final class AssetDescriptor {
	private final AssetId assetId;
	private final AssetType assetType;
	private final Path assetPath;
	private final Map<String, Object> settings;

	public AssetDescriptor(AssetId assetId, AssetType assetType, Path assetPath, Map<String, Object> settings) {
		this.assetId = assetId;
		this.assetType = assetType;
		this.assetPath = assetPath;
		this.settings = settings;
	}

	public AssetId getAssetId() {
		return assetId;
	}

	public AssetType getAssetType() {
		return assetType;
	}

	public Path getAssetPath() {
		return assetPath;
	}

	public Map<String, Object> getSettings() {
		return settings;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		AssetDescriptor that = (AssetDescriptor) o;
		return Objects.equals(assetId, that.assetId) && assetType == that.assetType && Objects.equals(assetPath, that.assetPath) && Objects.equals(settings, that.settings);
	}

	@Override
	public int hashCode() {
		return Objects.hash(assetId, assetType, assetPath, settings);
	}

	@Override
	public String toString() {
		return "AssetDescriptor{" +
			"assetId=" + assetId +
			", assetType=" + assetType +
			", assetPath=" + assetPath +
			", settings=" + settings +
			'}';
	}
}
