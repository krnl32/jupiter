package com.krnl32.jupiter.engine.asset.database;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.registry.AssetEntry;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistry;

import java.util.Collection;

public class AssetRepository {
	private final AssetPersistence assetPersistence;
	private final AssetDatabase assetDatabase;
	private final AssetRegistry assetRegistry;

	public AssetRepository(AssetPersistence assetPersistence) {
		this.assetPersistence = assetPersistence;
		this.assetDatabase = assetPersistence.loadAssetDatabase();
		this.assetRegistry = assetPersistence.loadAssetRegistry();
	}

	public AssetMetadata getAssetMetadata(AssetId assetId) {
		AssetMetadata assetMetadata = assetDatabase.getAssetMetadata(assetId);
		if (assetMetadata != null) {
			return assetMetadata;
		}

		assetMetadata = assetPersistence.getAssetMetadata(assetId);
		if (assetMetadata != null) {
			assetDatabase.addAssetMetadata(assetMetadata);
		}

		return assetMetadata;
	}

	public AssetMetadata getAssetMetadata(String assetPath) {
		AssetMetadata assetMetadata = assetDatabase.getAssetMetadata(assetPath);
		if (assetMetadata != null) {
			return assetMetadata;
		}

		assetMetadata = assetPersistence.getAssetMetadata(assetPath);
		if (assetMetadata != null) {
			assetDatabase.addAssetMetadata(assetMetadata);
		}

		return assetMetadata;
	}

	public void saveAssetMetadata(AssetMetadata assetMetadata) {
		assetDatabase.addAssetMetadata(assetMetadata);
		assetPersistence.saveAssetMetadata(assetMetadata);
	}

	public void removeAssetMetadata(AssetId assetId) {
		assetDatabase.removeAssetMetadata(assetId);
		assetPersistence.removeAssetMetadata(assetId);
	}

	public void removeAssetMetadata(String assetPath) {
		assetDatabase.removeAssetMetadata(assetPath);
		assetPersistence.removeAssetMetadata(assetPath);
	}

	public Collection<AssetMetadata> getAssetMetadatas() {
		return assetDatabase.getAssetMetadatas();
	}

	public AssetEntry getAssetEntry(AssetId assetId) {
		return assetRegistry.getAssetEntry(assetId);
	}

	public Collection<AssetEntry> getAssetEntries() {
		return assetRegistry.getAssetEntries();
	}
}
