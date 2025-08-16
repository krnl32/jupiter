package com.krnl32.jupiter.engine.asset.registry;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.core.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.UUID;

public class AssetRegistrySerializer {
	public static JSONObject serialize(AssetRegistry registry) {
		JSONArray assetEntriesJson = new JSONArray();
		Collection<AssetEntry> assetEntries = registry.getAssetEntries();

		for (AssetEntry assetEntry : assetEntries) {
			JSONObject serializedEntry = serializeAssetEntry(assetEntry);
			assetEntriesJson.put(serializedEntry);
		}

		return new JSONObject()
			.put("assets", assetEntriesJson);
	}

	public static AssetRegistry deserialize(JSONObject registryData) {
		try {
			AssetRegistry assetRegistry = new AssetRegistry();
			JSONArray assetEntriesJson = registryData.getJSONArray("assets");

			for (int i = 0; i < assetEntriesJson.length(); i++) {
				JSONObject assetEntryJson = assetEntriesJson.getJSONObject(i);
				AssetEntry assetEntry = deserializeAssetEntry(assetEntryJson);
				assetRegistry.register(assetEntry);
			}

			return assetRegistry;
		} catch (Exception e) {
			Logger.error("AssetRegistrySerializer Failed to Deserialize: {}", e.getMessage());
			return null;
		}
	}

	private static JSONObject serializeAssetEntry(AssetEntry assetEntry) {
		UUID uuid = assetEntry.getAssetId().getId();
		String type = assetEntry.getAssetType().name();
		String path = assetEntry.getAssetPath();

		return new JSONObject()
			.put("uuid", uuid)
			.put("assetType", type)
			.put("assetPath", path);
	}

	private static AssetEntry deserializeAssetEntry(JSONObject assetEntryData) {
		UUID assetUUID = UUID.fromString(assetEntryData.getString("uuid"));
		AssetId assetId = new AssetId(assetUUID);
		AssetType assetType = AssetType.valueOf(assetEntryData.getString("assetType"));
		String assetPath = assetEntryData.getString("assetPath");

		return new AssetEntry(assetId, assetType, assetPath);
	}
}
