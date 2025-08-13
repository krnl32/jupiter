package com.krnl32.jupiter.engine.asset.serializer;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import org.json.JSONObject;

import java.util.UUID;

public class AssetMetadataSerializer {
	public static JSONObject serialize(AssetMetadata metadata) {
		return new JSONObject()
			.put("version", metadata.getVersion())
			.put("uuid", metadata.getAssetId().getId())
			.put("assetType", metadata.getAssetType().name())
			.put("assetPath", metadata.getAssetPath())
			.put("importerName", metadata.getImporterName())
			.put("importSettings", metadata.getImportSettings())
			.put("lastModified", metadata.getLastModified());
	}

	public static AssetMetadata deserialize(JSONObject metadata) {
		return new AssetMetadata(
			metadata.getInt("version"),
			new AssetId(UUID.fromString(metadata.getString("uuid"))),
			AssetType.valueOf(metadata.getString("assetType")),
			metadata.getString("assetPath"),
			metadata.getString("importerName"),
			metadata.getJSONObject("importSettings").toMap(),
			metadata.getLong("lastModified")
		);
	}
}
