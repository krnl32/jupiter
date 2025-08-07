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
	private final Map<AssetId, AssetMetadata> metadataCache = new HashMap<>();

	public AssetMetadata getAssetMetadata(AssetId assetId) {
		return metadataCache.get(assetId);
	}

	public void addAssetMetadata(AssetMetadata assetMetadata) {
		metadataCache.put(assetMetadata.getAssetId(), assetMetadata);
	}

	public void loadFromDisk(Path assetMetadataFolder) {
		File metadataDir = new File(assetMetadataFolder.toString());
		File[] metadataFiles = metadataDir.listFiles();
		if (metadataFiles != null) {
			for (File metadataFile : metadataFiles) {
				if (metadataFile.isFile() && metadataFile.getName().endsWith(".jmeta")) {
					try {
						JSONObject metadata = new JSONObject(FileIO.readFileContent(metadataFile.getAbsolutePath()));
						AssetMetadata assetMetadata = AssetMetadataSerializer.deserialize(metadata);;
						metadataCache.put(assetMetadata.getAssetId(), assetMetadata);
					} catch (Exception e) {
						Logger.error("AssetDatabase LoadFromDisk Failed to Load Asset({}): {}", metadataFile.getName(), e.getMessage());
					}
				}
			}
		}
	}
}
