package com.krnl32.jupiter.editor.asset;

import com.krnl32.jupiter.engine.asset.core.AssetManager;
import com.krnl32.jupiter.engine.asset.core.AssetReferenceManager;
import com.krnl32.jupiter.engine.asset.database.AssetDatabase;
import com.krnl32.jupiter.engine.asset.handle.*;
import com.krnl32.jupiter.engine.asset.loader.AssetLoader;
import com.krnl32.jupiter.engine.asset.loader.AssetLoaderRegistry;
import com.krnl32.jupiter.engine.asset.registry.AssetEntry;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistry;
import com.krnl32.jupiter.engine.core.Logger;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EditorAssetManager implements AssetManager {
	private final Map<AssetId, Asset> loadedAssets;
	private final AssetReferenceManager assetReferenceManager;
	private final AssetRegistry assetRegistry;
	private final AssetDatabase assetDatabase;

	public EditorAssetManager(AssetRegistry assetRegistry, AssetDatabase assetDatabase) {
		this.loadedAssets = new HashMap<>();
		this.assetReferenceManager = new AssetReferenceManager();
		this.assetRegistry = assetRegistry;
		this.assetDatabase = assetDatabase;
	}

	@Override
	public <T extends Asset> T getAsset(AssetId assetId) {
		if (loadedAssets.containsKey(assetId)) {
			assetReferenceManager.acquire(assetId);
			return (T) loadedAssets.get(assetId);
		}

		AssetMetadata assetMetadata = assetDatabase.getAssetMetadata(assetId);
		if (assetMetadata == null) {
			Logger.error("EditorAssetManager Failed to Get Asset({}): No AssetMetadata Found", assetId);
			return null;
		}

		AssetLoader assetLoader = AssetLoaderRegistry.getLoader(assetMetadata.getAssetType());
		if (assetLoader == null) {
			Logger.error("EditorAssetManager Get Asset({}) Failed: No AssetLoader Found For Type({})", assetMetadata.getAssetId(), assetMetadata.getAssetType());
			return null;
		}

		AssetDescriptor assetDescriptor = new AssetDescriptor(assetMetadata.getAssetId(), assetMetadata.getAssetType(), Path.of(assetMetadata.getSourcePath()), assetMetadata.getImportSettings());
		Asset asset = assetLoader.load(assetDescriptor);
		if (asset == null) {
			Logger.error("EditorAssetManager Get Asset({}) Failed: AssetLoader({}) Failed", assetMetadata.getAssetId(), assetMetadata.getAssetType());
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
			Logger.error("EditorAssetManager Unload({}) Failed: No AssetLoader Found For Type({})", assetId, asset.getType());
			return;
		}

		assetLoader.unload(asset);
		loadedAssets.remove(assetId);
		assetReferenceManager.release(assetId);
	}

	public <T extends Asset> T getAsset(String sourcePath) {
		AssetMetadata assetMetadata = assetDatabase.getAssetMetadata(sourcePath);
		if (assetMetadata == null) {
			Logger.error("EditorAssetManager Failed to Get Asset({}): No AssetMetadata Found", sourcePath);
			return null;
		}
		return getAsset(assetMetadata.getAssetId());
	}

	public Path getAssetPath(AssetId assetId) {
		AssetMetadata assetMetadata = assetDatabase.getAssetMetadata(assetId);
		if (assetMetadata == null) {
			Logger.error("EditorAssetManager Failed to Get AssetPath({}): No AssetMetadata Found", assetId);
			return null;
		}
		return Path.of(assetMetadata.getSourcePath());
	}

	public AssetType getAssetType(AssetId assetId) {
		AssetEntry assetEntry = assetRegistry.getAssetEntry(assetId);
		return (assetEntry != null ? assetEntry.getAssetType() : null);
	}

	public List<AssetId> getAssetIdsByType(AssetType type) {
		return assetRegistry.getAssetEntries().stream()
			.filter(assetEntry -> assetEntry.getAssetType() == type)
			.map(AssetEntry::getAssetId)
			.toList();
	}

	public List<Asset> getAssetsByType(AssetType type) {
		return assetRegistry.getAssetEntries().stream()
			.filter(assetEntry -> assetEntry.getAssetType() == type)
			.map(assetEntry -> (Asset) getAsset(assetEntry.getAssetId()))
			.collect(Collectors.toList());
	}
}
