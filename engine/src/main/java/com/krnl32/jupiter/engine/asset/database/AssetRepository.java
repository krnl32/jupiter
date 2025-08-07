package com.krnl32.jupiter.engine.asset.database;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.registry.AssetEntry;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistry;

public class AssetRepository {
	private final AssetPersistence assetPersistence;
	private final AssetRegistry assetRegistry;
	private final AssetDatabase assetDatabase;

	public AssetRepository(AssetPersistence assetPersistence) {
		this.assetPersistence = assetPersistence;
		this.assetRegistry = assetPersistence.loadAssetRegistry();
		this.assetDatabase = assetPersistence.loadAssetDatabase();
	}

	public void saveAssetMetadata(AssetMetadata assetMetadata) {
		assetDatabase.addAssetMetadata(assetMetadata);
		assetPersistence.saveAssetMetadata(assetMetadata);
		assetRegistry.register(new AssetEntry(assetMetadata.getAssetId(), assetMetadata.getAssetType(), assetMetadata.getAssetPath()));
		assetPersistence.saveAssetRegistry(assetRegistry);
	}

	public AssetMetadata getAssetMetadata(AssetId assetId) {
		AssetMetadata assetMetadata = assetDatabase.getAssetMetadata(assetId);
		if (assetMetadata != null) {
			return assetMetadata;
		}

		assetMetadata = assetPersistence.getAssetMetadata(assetId);
		if (assetMetadata != null) {
			assetDatabase.addAssetMetadata(assetMetadata);
			return assetMetadata;
		}

		return null;
	}
}
