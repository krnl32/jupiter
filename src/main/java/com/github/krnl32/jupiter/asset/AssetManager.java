package com.github.krnl32.jupiter.asset;

import com.github.krnl32.jupiter.core.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AssetManager {
	private static AssetManager instance;
	private final Map<String, AssetID> keyToAssetId = new HashMap<>();
	private final Map<AssetID, String> assetIdToKey = new HashMap<>();
	private final Map<AssetID, Asset> loadedAssets = new HashMap<>();
	private final Map<AssetID, Integer> refCounter = new HashMap<>();
	private final Map<String, Supplier<? extends Asset>> assetSuppliers = new HashMap<>();

	private AssetManager() {
	}

	public static AssetManager getInstance() {
		if (instance == null)
			instance = new AssetManager();
		return instance;
	}

	public <T extends Asset> AssetID load(String key, Supplier<T> assetSupplier) {
		if (keyToAssetId.containsKey(key)) {
			AssetID assetID = keyToAssetId.get(key);
			refCounter.put(assetID, refCounter.get(assetID) + 1);
			return assetID;
		}

		assetSuppliers.put(key, assetSupplier);

		T asset = assetSupplier.get();
		if (!asset.load()) {
			Logger.error("AssetManager failed to Load(Key={}, ID={}, Type={})", key, asset.getId(), asset.getType());
			return null;
		}

		AssetID assetID = asset.getId();
		keyToAssetId.put(key, assetID);
		assetIdToKey.put(assetID, key);
		loadedAssets.put(assetID, asset);
		refCounter.put(assetID, 1);
		Logger.info("AssetManager Loaded(Key={}, ID={}, Type={})", key, assetID, asset.getType());
		return assetID;
	}

	public void unload(String key) {
		AssetID assetID = keyToAssetId.get(key);
		if (assetID == null)
			return;

		int refCount = refCounter.getOrDefault(assetID, 1) - 1;
		if (refCount > 0) {
			refCounter.put(assetID, refCount);
			return;
		}

		Asset asset = loadedAssets.remove(assetID);
		if (asset != null)
			asset.unload();
		refCounter.remove(assetID);
		keyToAssetId.remove(key);
		assetIdToKey.remove(assetID);
		Logger.info("AssetManager Unloaded(Key={}, ID={})", key, assetID);
	}

	public void unload(AssetID assetID) {
		String key = assetIdToKey.get(assetID);
		if (key == null) {
			Logger.error("AssetManager Unload(AssetID={}) Key Not Found", assetID);
			return;
		}
		unload(key);
	}

	public AssetID reload(String key) {
		var supplier = assetSuppliers.get(key);
		if (supplier == null) {
			Logger.error("AssetManager Reload(Key={}) Supplier Not Found", key);
			return null;
		}

		AssetID assetID = keyToAssetId.get(key);
		if (assetID != null) {
			Asset asset = loadedAssets.get(assetID);
			if (asset != null) {
				boolean ret = asset.reload();
				Logger.info("AssetManager Reload(Key={}) {}", key, (ret ? "Success" : "Failed"));
				return assetID;
			}
		}

		Asset newAsset = supplier.get();
		if (!newAsset.load()) {
			Logger.error("AssetManager Reload(Key={}, ID={}, Type={}) Failed to Load New Asset", key, newAsset.getId(), newAsset.getType());
			return null;
		}

		AssetID newAssetId = newAsset.getId();
		keyToAssetId.put(key, newAssetId);
		assetIdToKey.put(newAssetId, key);
		loadedAssets.put(newAssetId, newAsset);
		refCounter.put(newAssetId, 1);
		Logger.info("AssetManager Reload(Key={}, new ID={}) Loaded New Asset", key, newAssetId);
		return newAssetId;
	}

	public AssetID reload(AssetID assetID) {
		String key = assetIdToKey.get(assetID);
		if (key == null) {
			Logger.error("AssetManager Reload(AssetID={}) Key Not Found", assetID);
			return null;
		}
		return reload(key);
	}

	public void reloadAll() {
		for (String key : new ArrayList<>(keyToAssetId.keySet()))
			reload(key);
	}

	public void unloadAll() {
		new ArrayList<>(keyToAssetId.keySet()).forEach(this::unload);
	}

	@SuppressWarnings("unchecked")
	public <T extends Asset> T getAsset(String key) {
		AssetID assetID = keyToAssetId.get(key);
		if (assetID == null) {
			Logger.error("getAsset(Key={}) AssetID Not Found", key);
			return null;
		}

		Asset asset = loadedAssets.get(assetID);
		if (asset == null) {
			Logger.error("getAsset(Key={}, ID={}) null", key, assetID);
			return null;
		}

		if(!asset.isLoaded()) {
			Logger.error("getAsset(Key={}, ID={}) Not Loaded", key, assetID);
			return null;
		}

		return (T) asset;
	}

	@SuppressWarnings("unchecked")
	public <T extends Asset> T getAsset(AssetID assetID) {
		if (assetID == null)
			return null;

		Asset asset = loadedAssets.get(assetID);
		if (asset == null) {
			Logger.error("getAsset(AssetID={}) null", assetID);
			return null;
		}

		if(!asset.isLoaded()) {
			Logger.error("getAsset(AssetID={}) Not Loaded", assetID);
			return null;
		}

		return (T) asset;
	}

	public boolean isLoaded(String key) {
		AssetID assetID = keyToAssetId.get(key);
		Asset asset = assetID != null ? loadedAssets.get(assetID) : null;
		return asset != null && asset.isLoaded();
	}

	public boolean isLoaded(AssetID assetID) {
		Asset asset = loadedAssets.get(assetID);
		return asset != null && asset.isLoaded();
	}

	public List<Asset> getAssetsByType(AssetType assetType) {
		return loadedAssets.values().stream()
			.filter(a -> a.getType() == assetType)
			.collect(Collectors.toList());
	}
}
