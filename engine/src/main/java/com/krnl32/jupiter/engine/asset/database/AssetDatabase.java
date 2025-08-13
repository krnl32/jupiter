package com.krnl32.jupiter.engine.asset.database;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.serializer.AssetMetadataSerializer;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.utility.FileIO;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class AssetDatabase {
	private final Map<AssetId, AssetMetadata> assetIdToMetadata = new HashMap<>();
	private final Map<String, AssetId> pathToAssetId = new HashMap<>();

	public AssetMetadata getAssetMetadata(AssetId assetId) {
		return assetIdToMetadata.get(assetId);
	}

	public AssetMetadata getAssetMetadata(String sourcePath) {
		AssetId assetId = pathToAssetId.get(sourcePath);
		return assetIdToMetadata.get(assetId);
	}

	public void addAssetMetadata(AssetMetadata assetMetadata) {
		assetIdToMetadata.put(assetMetadata.getAssetId(), assetMetadata);
		pathToAssetId.put(assetMetadata.getSourcePath(), assetMetadata.getAssetId());
	}

	public void loadFromDisk(Path assetDatabaseDirectory) {

		File[] metadataFiles = new File(assetDatabaseDirectory.toString()).listFiles();
		if (metadataFiles == null) {
			return;
		}

		for (File metadataFile : metadataFiles) {
			if (!metadataFile.isFile() && !metadataFile.getName().endsWith(".jmeta")) {
				continue;
			}

			try {
				Path metadataPath = metadataFile.toPath();
				String metadataContent = FileIO.readFileContent(metadataPath);
				JSONObject metadata = new JSONObject(metadataContent);
				AssetMetadata assetMetadata = AssetMetadataSerializer.deserialize(metadata);;
				assetIdToMetadata.put(assetMetadata.getAssetId(), assetMetadata);
				pathToAssetId.put(assetMetadata.getSourcePath(), assetMetadata.getAssetId());
			} catch (Exception e) {
				Logger.error("AssetDatabase LoadFromDisk Failed to Load Asset({}): {}", metadataFile.getName(), e.getMessage());
			}
		}
	}
}
