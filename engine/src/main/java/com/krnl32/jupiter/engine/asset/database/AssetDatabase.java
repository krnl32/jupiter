package com.krnl32.jupiter.engine.asset.database;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AssetDatabase {
	private final Map<AssetId, AssetMetadata> assetIdToMetadata = new HashMap<>();
	private final Map<String, AssetId> pathToAssetId = new HashMap<>();

	public AssetMetadata getAssetMetadata(AssetId assetId) {
		return assetIdToMetadata.get(assetId);
	}

	public AssetMetadata getAssetMetadata(String assetPath) {
		AssetId assetId = pathToAssetId.get(assetPath);
		return assetIdToMetadata.get(assetId);
	}

	public Collection<AssetMetadata> getAssetMetadatas() {
		return Collections.unmodifiableCollection(assetIdToMetadata.values());
	}

	public void addAssetMetadata(AssetMetadata assetMetadata) {
		assetIdToMetadata.put(assetMetadata.getAssetId(), assetMetadata);
		pathToAssetId.put(assetMetadata.getAssetPath(), assetMetadata.getAssetId());
	}

	public void removeAssetMetadata(AssetId assetId) {
		AssetMetadata assetMetadata = assetIdToMetadata.get(assetId);
		if (assetMetadata == null) {
			return;
		}
		pathToAssetId.remove(assetMetadata.getAssetPath());
		assetIdToMetadata.remove(assetId);
	}

	public void removeAssetMetadata(String assetPath) {
		AssetId assetId = pathToAssetId.get(assetPath);
		if (assetId == null) {
			return;
		}
		assetIdToMetadata.remove(assetId);
		pathToAssetId.remove(assetPath);
	}
}
