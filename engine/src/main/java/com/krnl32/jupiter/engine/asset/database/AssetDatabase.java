package com.krnl32.jupiter.engine.asset.database;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AssetDatabase {
	private final Map<AssetId, AssetMetadata> assetIdToAssetMetadata = new HashMap<>();
	private final Map<String, AssetId> assetPathToAssetId = new HashMap<>();

	public AssetMetadata getAssetMetadata(AssetId assetId) {
		return assetIdToAssetMetadata.get(assetId);
	}

	public AssetMetadata getAssetMetadata(String assetPath) {
		AssetId assetId = assetPathToAssetId.get(assetPath);
		return assetIdToAssetMetadata.get(assetId);
	}

	public Collection<AssetMetadata> getAllAssetMetadata() {
		return Collections.unmodifiableCollection(assetIdToAssetMetadata.values());
	}

	public void addAssetMetadata(AssetMetadata assetMetadata) {
		AssetId assetId = assetMetadata.getAssetId();
		String assetPath = assetMetadata.getAssetPath();

		assetIdToAssetMetadata.put(assetId, assetMetadata);
		assetPathToAssetId.put(assetPath, assetId);
	}

	public void removeAssetMetadata(AssetId assetId) {
		AssetMetadata assetMetadata = assetIdToAssetMetadata.get(assetId);

		if (assetMetadata == null) {
			return;
		}

		assetPathToAssetId.remove(assetMetadata.getAssetPath());
		assetIdToAssetMetadata.remove(assetId);
	}

	public void removeAssetMetadata(String assetPath) {
		AssetId assetId = assetPathToAssetId.get(assetPath);

		if (assetId == null) {
			return;
		}

		assetIdToAssetMetadata.remove(assetId);
		assetPathToAssetId.remove(assetPath);
	}
}
