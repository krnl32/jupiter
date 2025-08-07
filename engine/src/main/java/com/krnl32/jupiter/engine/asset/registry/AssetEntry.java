package com.krnl32.jupiter.engine.asset.registry;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetType;

import java.util.Objects;

public final class AssetEntry {
	private final AssetId assetId;
	private final AssetType assetType;
	private final String assetPath;

	public AssetEntry(AssetId assetId, AssetType assetType, String assetPath) {
		this.assetId = assetId;
		this.assetType = assetType;
		this.assetPath = assetPath;
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

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		AssetEntry that = (AssetEntry) o;
		return Objects.equals(assetId, that.assetId) && assetType == that.assetType && Objects.equals(assetPath, that.assetPath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(assetId, assetType, assetPath);
	}

	@Override
	public String toString() {
		return "AssetEntry{" +
			"assetId=" + assetId +
			", assetType=" + assetType +
			", assetPath='" + assetPath + '\'' +
			'}';
	}
}
