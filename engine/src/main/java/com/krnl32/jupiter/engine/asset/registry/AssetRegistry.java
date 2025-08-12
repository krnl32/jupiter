package com.krnl32.jupiter.engine.asset.registry;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.core.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AssetRegistry {
	private final Map<String, AssetId> assetPathToId = new HashMap<>();
	private final Map<AssetId, AssetEntry> assetIdToEntry = new HashMap<>();

	public void register(AssetEntry assetEntry) {
		AssetId existingId = assetPathToId.get(assetEntry.getAssetPath());
		if (existingId != null && !existingId.equals(assetEntry.getAssetId())) {
			return;
		}

		if (!assetIdToEntry.containsKey(assetEntry.getAssetId())) {
			assetPathToId.put(assetEntry.getAssetPath(), assetEntry.getAssetId());
			assetIdToEntry.put(assetEntry.getAssetId(), assetEntry);
			Logger.debug("AssetRegistry Registering({})", assetEntry);
		}
	}

	public void unregister(AssetId assetId) {
		AssetEntry entry = assetIdToEntry.remove(assetId);
		if (entry != null) {
			assetPathToId.remove(entry.getAssetPath());
			Logger.debug("AssetRegistry Unregistering({})", assetId);
		}
	}

	public AssetId getAssetId(String assetPath) {
		return assetPathToId.get(assetPath);
	}

	public AssetEntry getAssetEntry(AssetId assetId) {
		return assetIdToEntry.get(assetId);
	}

	public Collection<AssetEntry> getAssetEntries() {
		return Collections.unmodifiableCollection(assetIdToEntry.values());
	}
}
