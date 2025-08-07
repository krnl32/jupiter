package com.krnl32.jupiter.engine.asset.registry;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.core.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.UUID;

public class AssetRegistrySerializer {
	public static JSONObject serialize(AssetRegistry registry) {
		JSONArray assetEntriesData = new JSONArray();
		registry.getAssetEntries().forEach(assetEntry -> assetEntriesData.put(serializeAssetEntry(assetEntry)));
		return new JSONObject().put("assets", assetEntriesData);
	}

	public static AssetRegistry deserialize(JSONObject registryData) {
		try {
			AssetRegistry registry = new AssetRegistry();

			JSONArray assetEntriesData = registryData.getJSONArray("assets");
			for (int i = 0; i < assetEntriesData.length(); i++) {
				registry.register(deserializeAssetEntry(assetEntriesData.getJSONObject(i)));
			}

			return registry;
		} catch (Exception e) {
			Logger.error("AssetRegistrySerializer Failed to Deserialize: {}", e.getMessage());
			return null;
		}
	}

	private static JSONObject serializeAssetEntry(AssetEntry assetEntry) {
		return new JSONObject()
			.put("uuid", assetEntry.getAssetId().getId())
			.put("assetType", assetEntry.getAssetType().name())
			.put("assetPath", assetEntry.getAssetPath());
	}

	private static AssetEntry deserializeAssetEntry(JSONObject assetEntryData) {
		return new AssetEntry(
			new AssetId(UUID.fromString(assetEntryData.getString("uuid"))),
			AssetType.valueOf(assetEntryData.getString("assetType")),
			assetEntryData.getString("assetPath")
		);
	}
}
