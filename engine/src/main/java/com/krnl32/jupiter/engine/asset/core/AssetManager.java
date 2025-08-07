package com.krnl32.jupiter.engine.asset.core;

import com.krnl32.jupiter.engine.asset.database.AssetDatabase;
import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.loader.AssetLoader;
import com.krnl32.jupiter.engine.asset.loader.AssetLoaderRegistry;
import com.krnl32.jupiter.engine.core.Logger;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
	private final Map<AssetId, Asset> loadedAssets;
	private final AssetReferenceManager assetReferenceManager;
	private final AssetDatabase assetDatabase;

	public AssetManager(Path assetDirectoryPath) {
		this.loadedAssets = new HashMap<>();
		this.assetReferenceManager = new AssetReferenceManager();
		this.assetDatabase = new AssetDatabase();
		this.assetDatabase.loadFromDisk(assetDirectoryPath.resolve(".jmetadata"));
	}

	public <T extends Asset> T getAsset(AssetId assetId) {
		if (loadedAssets.containsKey(assetId)) {
			assetReferenceManager.acquire(assetId);
			return (T) loadedAssets.get(assetId);
		}

		AssetMetadata assetMetadata = assetDatabase.getAssetMetadata(assetId);
		if (assetMetadata == null) {
			Logger.error("AssetManager Failed to Get Asset({}): No AssetMetadata Found", assetId);
			return null;
		}

		AssetLoader assetLoader = AssetLoaderRegistry.getLoader(assetMetadata.getAssetType());
		if (assetLoader == null) {
			Logger.error("AssetManager Get Asset({}) Failed: No AssetLoader Found For Type({})", assetMetadata.getAssetId(), assetMetadata.getAssetType());
			return null;
		}

		Asset asset = assetLoader.load(assetMetadata);
		if (asset == null) {
			Logger.error("AssetManager Get Asset({}) Failed: AssetLoader({}) Failed", assetMetadata.getAssetId(), assetMetadata.getAssetType());
			return null;
		}

		loadedAssets.put(assetId, asset);
		assetReferenceManager.acquire(assetId);
		return (T) asset;
	}

	public <T extends Asset> AssetHandle<T> getAssetHandle(AssetId assetId) {
		if (loadedAssets.containsKey(assetId)) {
			return new AssetHandle<>(assetId, this);
		}

		AssetMetadata assetMetadata = assetDatabase.getAssetMetadata(assetId);
		if (assetMetadata == null) {
			Logger.error("AssetManager Failed to Get Asset({}): No AssetMetadata Found", assetId);
			return null;
		}

		AssetLoader assetLoader = AssetLoaderRegistry.getLoader(assetMetadata.getAssetType());
		if (assetLoader == null) {
			Logger.error("AssetManager Get Asset({}) Failed: No AssetLoader Found For Type({})", assetMetadata.getAssetId(), assetMetadata.getAssetType());
			return null;
		}

		Asset asset = assetLoader.load(assetMetadata);
		if (asset == null) {
			Logger.error("AssetManager Get Asset({}) Failed: AssetLoader({}) Failed", assetMetadata.getAssetId(), assetMetadata.getAssetType());
			return null;
		}

		loadedAssets.put(assetId, asset);
		return new AssetHandle<>(assetId, this);
	}

	protected void acquireAsset(AssetId assetId) {
		assetReferenceManager.acquire(assetId);
	}

	protected void releaseAsset(AssetId assetId) {
		assetReferenceManager.release(assetId);
	}

	protected <T extends Asset> T getLoadedAsset(AssetId assetId) {
		return (T) loadedAssets.get(assetId);
	}
}
