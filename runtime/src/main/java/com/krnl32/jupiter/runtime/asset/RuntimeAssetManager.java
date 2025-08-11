package com.krnl32.jupiter.runtime.asset;

import com.krnl32.jupiter.engine.asset.core.AssetManager;
import com.krnl32.jupiter.engine.asset.core.AssetReferenceManager;
import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetDescriptor;
import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.asset.loader.AssetLoader;
import com.krnl32.jupiter.engine.asset.loader.AssetLoaderRegistry;
import com.krnl32.jupiter.engine.asset.registry.AssetEntry;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistry;
import com.krnl32.jupiter.engine.core.Logger;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class RuntimeAssetManager implements AssetManager {
	private final Map<AssetId, Asset> loadedAssets;
	private final AssetReferenceManager assetReferenceManager;
	private final AssetRegistry assetRegistry;

	public RuntimeAssetManager(AssetRegistry assetRegistry) {
		this.loadedAssets = new HashMap<>();
		this.assetReferenceManager = new AssetReferenceManager();
		this.assetRegistry = assetRegistry;
	}

	@Override
	public <T extends Asset> T getAsset(AssetId assetId) {
		if (loadedAssets.containsKey(assetId)) {
			assetReferenceManager.acquire(assetId);
			return (T) loadedAssets.get(assetId);
		}

		AssetEntry assetEntry = assetRegistry.getAssetEntry(assetId);
		if (assetEntry == null) {
			Logger.error("RuntimeAssetManager Failed to Get Asset({}): No AssetEntry Found", assetId);
			return null;
		}

		AssetLoader assetLoader = AssetLoaderRegistry.getLoader(assetEntry.getAssetType());
		if (assetLoader == null) {
			Logger.error("RuntimeAssetManager Get Asset({}) Failed: No AssetLoader Found For Type({})", assetEntry.getAssetId(), assetEntry.getAssetType());
			return null;
		}

		AssetDescriptor assetDescriptor = new AssetDescriptor(assetEntry.getAssetId(), assetEntry.getAssetType(), Path.of(assetEntry.getAssetPath()), null);
		Asset asset = assetLoader.load(assetDescriptor);
		if (asset == null) {
			Logger.error("RuntimeAssetManager Get Asset({}) Failed: AssetLoader({}) Failed", assetEntry.getAssetId(), assetEntry.getAssetType());
			return null;
		}

		loadedAssets.put(assetId, asset);
		assetReferenceManager.acquire(assetId);
		return (T) asset;
	}

	@Override
	public boolean isAssetLoaded(AssetId assetId) {
		return loadedAssets.containsKey(assetId);
	}

	@Override
	public boolean isAssetRegistered(AssetId assetId) {
		return assetRegistry.getAssetEntry(assetId) != null;
	}

	@Override
	public void unloadAsset(AssetId assetId) {
		Asset asset = loadedAssets.get(assetId);
		if (asset == null) {
			return;
		}

		AssetLoader assetLoader = AssetLoaderRegistry.getLoader(asset.getType());
		if (assetLoader == null) {
			Logger.error("RuntimeAssetManager Unload({}) Failed: No AssetLoader Found For Type({})", assetId, asset.getType());
			return;
		}

		assetLoader.unload(asset);
		loadedAssets.remove(assetId);
		assetReferenceManager.release(assetId);
	}

	public AssetType getAssetType(AssetId assetId) {
		AssetEntry assetEntry = assetRegistry.getAssetEntry(assetId);
		return (assetEntry != null ? assetEntry.getAssetType() : null);
	}
}
