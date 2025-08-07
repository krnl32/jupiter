package com.krnl32.jupiter.engine.asset.database;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistry;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistrySerializer;
import com.krnl32.jupiter.engine.asset.serializer.AssetMetadataSerializer;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.utility.FileIO;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

public class AssetPersistence {
	private static final String ASSET_METADATA_EXTENSION = ".jmeta";

	private final Path assetRegistryPath;
	private final Path assetMetadataPath;

	public AssetPersistence(Path assetRegistryPath, Path assetMetadataPath) {
		this.assetRegistryPath = assetRegistryPath;
		this.assetMetadataPath = assetMetadataPath;
	}

	public AssetRegistry loadAssetRegistry() {
		if (!Files.exists(assetRegistryPath)) {
			return new AssetRegistry();
		}

		try {
			return AssetRegistrySerializer.deserialize(new JSONObject(FileIO.readFileContent(assetRegistryPath)));
		} catch (Exception e) {
			Logger.error("AssetPersistence loadAssetRegistry Failed To Deserialize AssetRegistry({}): {}", assetRegistryPath, e.getMessage());
			return null;
		}
	}

	public void saveAssetRegistry(AssetRegistry assetRegistry) {
		try {
			FileIO.writeFileContent(assetRegistryPath, AssetRegistrySerializer.serialize(assetRegistry).toString(4));
		} catch (Exception e) {
			Logger.error("AssetPersistence saveRegistry Failed To Write AssetRegistry({}): {}", assetRegistryPath, e.getMessage());
		}
	}

	public AssetMetadata getAssetMetadata(AssetId assetId) {
		Path metadataFilePath = assetMetadataPath.resolve(assetId.getId() + ASSET_METADATA_EXTENSION);
		if (!Files.exists(metadataFilePath)) {
			Logger.error("AssetPersistence getAssetMetadata Failed to get Asset({}) MetadataFile({}): File Doesn't Exist", assetId, metadataFilePath.toString());
			return null;
		}

		try {
			JSONObject metadataFileData = new JSONObject(FileIO.readFileContent(metadataFilePath));
			return AssetMetadataSerializer.deserialize(metadataFileData);
		} catch (Exception e) {
			Logger.error("AssetPersistence getAssetMetadata Failed To Read Asset({}) Metadata: {}", assetId.getId(), e.getMessage());
			return null;
		}
	}

	public void saveAssetMetadata(AssetMetadata assetMetadata) {
		try {
			Path metadataFilePath = assetMetadataPath.resolve(assetMetadata.getAssetId().getId() + ASSET_METADATA_EXTENSION);
			FileIO.writeFileContent(metadataFilePath, AssetMetadataSerializer.serialize(assetMetadata).toString(4));
		} catch (Exception e) {
			Logger.error("AssetPersistence saveAssetMetadata Failed To Write Asset({}) Metadata: {}", assetMetadata.getAssetId().getId(), e.getMessage());
		}
	}
}
