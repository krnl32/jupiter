package com.krnl32.jupiter.engine.asset.registry;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.core.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AssetRegistry {
	private final Map<String, AssetId> assetPathToAssetId = new HashMap<>();
	private final Map<AssetId, AssetEntry> assetIdToAssetEntry = new HashMap<>();

	public void register(AssetEntry assetEntry) {
		AssetId assetEntryId = assetEntry.getAssetId();
		String assetEntryPath = assetEntry.getAssetPath();

		// Check if Path Already Registered with a Different AssetID
		AssetId existingId = assetPathToAssetId.get(assetEntryPath);
		if (existingId != null && !existingId.equals(assetEntryId)) {
			return;
		}

		if (!assetIdToAssetEntry.containsKey(assetEntryId)) {
			assetPathToAssetId.put(assetEntryPath, assetEntryId);
			assetIdToAssetEntry.put(assetEntryId, assetEntry);
			Logger.debug("AssetRegistry Registering({})", assetEntry);
		}
	}

	public void unregister(AssetId assetId) {
		AssetEntry entry = assetIdToAssetEntry.remove(assetId);

		if (entry != null) {
			assetPathToAssetId.remove(entry.getAssetPath());
			Logger.debug("AssetRegistry Unregistering({})", assetId);
		}
	}

	public AssetId getAssetId(String assetPath) {
		return assetPathToAssetId.get(assetPath);
	}

	public AssetEntry getAssetEntry(AssetId assetId) {
		return assetIdToAssetEntry.get(assetId);
	}

	public Collection<AssetEntry> getAssetEntries() {
		return Collections.unmodifiableCollection(assetIdToAssetEntry.values());
	}
}
