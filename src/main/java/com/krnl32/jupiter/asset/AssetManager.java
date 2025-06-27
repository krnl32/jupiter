package com.krnl32.jupiter.asset;

import com.krnl32.jupiter.core.Logger;

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
	private final Map<AssetID, Asset> registeredAssets = new HashMap<>();

	private final Map<AssetID, Integer> refCounter = new HashMap<>();
	private final Map<String, Supplier<? extends Asset>> assetSuppliers = new HashMap<>();

	private AssetManager() {
	}

	public static AssetManager getInstance() {
		if (instance == null)
			instance = new AssetManager();
		return instance;
	}

	public <T extends Asset> AssetID register(String key, Supplier<T> assetSupplier) {
		if (keyToAssetId.containsKey(key))
			return keyToAssetId.get(key);

		T asset = assetSupplier.get();
		AssetID assetID = asset.getId();
		keyToAssetId.put(key, assetID);
		assetIdToKey.put(assetID, key);
		registeredAssets.put(assetID, asset);
		refCounter.put(assetID, 0);
		assetSuppliers.put(key, assetSupplier);

		Logger.info("AssetManager Registered(Key={}, ID={}, Type={})", key, assetID, asset.getType());
		return assetID;
	}

	public void unregister(String key) {
		AssetID assetID = keyToAssetId.get(key);
		if (assetID == null)
			return;

		unload(key);
		registeredAssets.remove(assetID);
		refCounter.remove(assetID);
		assetSuppliers.remove(key);
		keyToAssetId.remove(key);
		assetIdToKey.remove(assetID);
		Logger.info("AssetManager Unregistered(Key={}, ID={})", key, assetID);
	}

	public void unregister(AssetID assetID) {
		String key = assetIdToKey.get(assetID);
		if (key == null)
			return;
		unregister(key);
	}

	@SuppressWarnings("unchecked")
	public <T extends Asset> T loadRegisteredAsset(String key) {
		AssetID assetID = keyToAssetId.get(key);
		if (assetID == null) {
			Logger.error("AssetManager LoadRegisteredAsset(Key={}) Not Registered", key);
			return null;
		}

		if (loadedAssets.containsKey(assetID)) {
			refCounter.put(assetID, refCounter.get(assetID) + 1);
			return (T) loadedAssets.get(assetID);
		}

		Asset asset = registeredAssets.get(assetID);
		if (asset == null) {
			Logger.error("AssetManager LoadRegisteredAsset(Key={}) Registered Asset Not Found", key);
			return null;
		}

		if (!asset.load()) {
			Logger.error("AssetManager LoadRegisteredAsset(Key={}, ID={}, Type={}) Failed to Load Registered Asset", key, assetID, asset.getType());
			return null;
		}

		loadedAssets.put(assetID, asset);
		refCounter.put(assetID, 1);
		Logger.info("AssetManager LoadRegisteredAsset(Key={}, ID={}, Type={})", key, assetID, asset.getType());
		return (T) asset;
	}

	public <T extends Asset> T loadRegisteredAsset(AssetID assetID) {
		String key = assetIdToKey.get(assetID);
		if (key == null) {
			Logger.error("AssetManager LoadRegisteredAsset(AssetID={}) Key Not Found", assetID);
			return null;
		}
		return loadRegisteredAsset(key);
	}

	public <T extends Asset> AssetID registerAndLoad(String key, Supplier<T> assetSupplier) {
		AssetID assetID = register(key, assetSupplier);
		return (loadRegisteredAsset(key) != null) ? assetID : null;
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

		Logger.info("AssetManager UNLOADED(Key={}, ID={})", key, assetID);
	}

	// Unload Asset
	public void unload(AssetID assetID) {
		String key = assetIdToKey.get(assetID);
		if (key == null) {
			Logger.error("AssetManager Unload(AssetID={}) Key Not Found", assetID);
			return;
		}
		unload(key);
	}

	public AssetID hardReload(String key) {
		var supplier = assetSuppliers.get(key);
		if (supplier == null) {
			Logger.error("AssetManager HardReload(Key={}) Supplier Not Found", key);
			return null;
		}

		AssetID oldAssetID = keyToAssetId.get(key);
		if (oldAssetID != null) {
			Asset oldAsset = loadedAssets.remove(oldAssetID);
			if (oldAsset != null)
				oldAsset.unload();
			refCounter.remove(oldAssetID);
			assetIdToKey.remove(oldAssetID);
			registeredAssets.remove(oldAssetID);
		}

		Asset newAsset = supplier.get();
		if (!newAsset.load()) {
			Logger.error("AssetManager HardReload(Key={}, ID={}, Type={}) Failed to Load New Asset", key, newAsset.getId(), newAsset.getType());
			return null;
		}

		AssetID newAssetId = newAsset.getId();
		keyToAssetId.put(key, newAssetId);
		assetIdToKey.put(newAssetId, key);
		loadedAssets.put(newAssetId, newAsset);
		registeredAssets.put(newAssetId, newAsset);
		refCounter.put(newAssetId, 1);

		Logger.info("AssetManager HardReload(Key={}, new ID={}) LOADED New Asset", key, newAssetId);
		return newAssetId;
	}

	public AssetID hardReload(AssetID assetID) {
		String key = assetIdToKey.get(assetID);
		if (key == null) {
			Logger.error("AssetManager HardReload(AssetID={}) Key Not Found", assetID);
			return null;
		}
		return hardReload(key);
	}

	public void hardReloadAll() {
		for (String key : new ArrayList<>(keyToAssetId.keySet()))
			hardReload(key);
	}

	public boolean softReload(String key) {
		AssetID assetID = keyToAssetId.get(key);
		if (assetID == null) {
			Logger.error("AssetManager SoftReload(Key={}) Not Registered", key);
			return false;
		}

		Asset asset = loadedAssets.get(assetID);
		if (asset == null) {
			Logger.error("AssetManager SoftReload(Key={}, ID={}) Not LOADED", key, assetID);
			return false;
		}

		boolean ret = asset.reload();
		Logger.info("AssetManager SoftReload(Key={}) {}", key, (ret ? "Success" : "Failed"));
		return ret;
	}

	public boolean softReload(AssetID assetID) {
		String key = assetIdToKey.get(assetID);
		if (key == null) {
			Logger.error("AssetManager SoftReload(AssetID={}) Key Not Found", assetID);
			return false;
		}
		return softReload(key);
	}

	public void softReloadAll() {
		for (String key : new ArrayList<>(keyToAssetId.keySet()))
			softReload(key);
	}

	public void unloadAll() {
		new ArrayList<>(keyToAssetId.keySet()).forEach(this::unload);
	}

	@SuppressWarnings("unchecked")
	public <T extends Asset> T getRegisteredAsset(String key) {
		AssetID assetID = keyToAssetId.get(key);
		if (assetID == null) {
			Logger.error("GetRegisteredAsset(Key={}) AssetID Not Found", key);
			return null;
		}

		Asset asset = registeredAssets.get(assetID);
		if (asset == null) {
			Logger.error("GetRegisteredAsset(Key={}, ID={}) Not Found", key, assetID);
			return null;
		}

		return (T) asset;
	}

	@SuppressWarnings("unchecked")
	public <T extends Asset> T getRegisteredAsset(AssetID assetID) {
		if (assetID == null)
			return null;

		Asset asset = registeredAssets.get(assetID);
		if (asset == null) {
			Logger.error("GetRegisteredAsset(AssetID={}) Registered Asset Not Found", assetID);
			return null;
		}

		return (T) asset;
	}

	@SuppressWarnings("unchecked")
	public <T extends Asset> T getLoadedAsset(String key) {
		AssetID assetID = keyToAssetId.get(key);
		if (assetID == null) {
			Logger.error("GetAsset(Key={}) AssetID Not Found", key);
			return null;
		}

		Asset asset = loadedAssets.get(assetID);
		if (asset == null || !asset.isLoaded()) {
			Logger.error("GetAsset(Key={}, ID={}) Not LOADED", key, assetID);
			return null;
		}

		return (T) asset;
	}

	@SuppressWarnings("unchecked")
	public <T extends Asset> T getLoadedAsset(AssetID assetID) {
		if (assetID == null)
			return null;

		Asset asset = loadedAssets.get(assetID);
		if (asset == null || !asset.isLoaded()) {
			Logger.error("GetAsset(AssetID={}) Not LOADED", assetID);
			return null;
		}

		return (T) asset;
	}

	@SuppressWarnings("unchecked")
	public <T extends Asset> T getAsset(String key) {
		AssetID assetID = keyToAssetId.get(key);
		if (assetID == null) {
			Logger.error("GetAsset(Key={}) Asset Not Registered", key);
			return null;
		}

		Asset asset = loadedAssets.get(assetID);
		if (asset != null && asset.isLoaded())
			return (T) asset;

		if (loadRegisteredAsset(key) == null) {
			Logger.error("GetAsset(Key={}, ID={}) Failed to Load Registered Asset", key, assetID);
			return null;
		}

		return (T) loadedAssets.get(assetID);
	}

	public <T extends Asset> T getAsset(AssetID assetID) {
		if (assetID == null)
			return null;

		String key = assetIdToKey.get(assetID);
		if (key == null) {
			Logger.error("GetAsset(AssetID={}) Key Not Found", assetID);
			return null;
		}
		return getAsset(key); // reuse
	}

	public boolean isRegistered(String key) {
		return keyToAssetId.containsKey(key);
	}

	public boolean isRegistered(AssetID key) {
		return assetIdToKey.containsKey(key);
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

	public List<Asset> getLoadedAssets() {
		return registeredAssets.entrySet().stream()
			.filter(a -> loadedAssets.containsKey(a.getKey()))
			.map(Map.Entry::getValue)
			.collect(Collectors.toList());
	}

	public List<Asset> getUnloadedAssets() {
		return registeredAssets.entrySet().stream()
			.filter(a -> !loadedAssets.containsKey(a.getKey()))
			.map(Map.Entry::getValue)
			.collect(Collectors.toList());
	}
}
