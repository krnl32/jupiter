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
		AssetMetadata cachedMetadata = assetDatabase.getAssetMetadata(assetId);

		if (cachedMetadata != null) {
			return cachedMetadata;
		}

		AssetMetadata persistedMetadata = assetPersistence.getAssetMetadata(assetId);

		if (persistedMetadata != null) {
			assetDatabase.addAssetMetadata(persistedMetadata);
		}

		return persistedMetadata;
	}

	public AssetMetadata getAssetMetadata(String assetPath) {
		AssetMetadata cachedMetadata = assetDatabase.getAssetMetadata(assetPath);

		if (cachedMetadata != null) {
			return cachedMetadata;
		}

		AssetMetadata persistedMetadata = assetPersistence.getAssetMetadata(assetPath);

		if (persistedMetadata != null) {
			assetDatabase.addAssetMetadata(persistedMetadata);
		}

		return persistedMetadata;
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

	public Collection<AssetMetadata> getAllAssetMetadata() {
		return assetDatabase.getAllAssetMetadata();
	}

	public AssetEntry getAssetEntry(AssetId assetId) {
		return assetRegistry.getAssetEntry(assetId);
	}

	public Collection<AssetEntry> getAssetEntries() {
		return assetRegistry.getAssetEntries();
	}
}
