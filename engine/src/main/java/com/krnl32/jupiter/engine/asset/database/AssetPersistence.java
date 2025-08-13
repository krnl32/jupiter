package com.krnl32.jupiter.engine.asset.database;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistry;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistrySerializer;
import com.krnl32.jupiter.engine.asset.serializer.AssetMetadataSerializer;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.utility.FileIO;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class AssetPersistence {
	private static final String ASSET_METADATA_EXTENSION = ".jmeta";

	private final Path assetRegistryFilePath;
	private final Path assetDatabaseDirectory;

	public AssetPersistence(Path assetRegistryFilePath, Path assetDatabaseDirectory) {
		this.assetRegistryFilePath = assetRegistryFilePath;
		this.assetDatabaseDirectory = assetDatabaseDirectory;
	}

	public AssetRegistry loadAssetRegistry() {
		if (!Files.exists(assetRegistryFilePath)) {
			return new AssetRegistry();
		}

		try {
			String fileContent = FileIO.readFileContent(assetRegistryFilePath);
			JSONObject assetRegistry = new JSONObject(fileContent);
			return AssetRegistrySerializer.deserialize(assetRegistry);
		} catch (Exception e) {
			Logger.error("AssetPersistence loadAssetRegistry Failed To Deserialize AssetRegistry({}): {}", assetRegistryFilePath, e.getMessage());
			return null;
		}
	}

	public void saveAssetRegistry(AssetRegistry assetRegistry) {
		try {
			String serializedData = AssetRegistrySerializer.serialize(assetRegistry).toString(4);
			FileIO.writeFileContent(assetRegistryFilePath, serializedData);
		} catch (Exception e) {
			Logger.error("AssetPersistence saveRegistry Failed To Write AssetRegistry({}): {}", assetRegistryFilePath, e.getMessage());
		}
	}

	public AssetDatabase loadAssetDatabase() {
		if (!Files.exists(assetDatabaseDirectory)) {
			return new AssetDatabase();
		}

		AssetDatabase assetDatabase = new AssetDatabase();
		assetDatabase.loadFromDisk(assetDatabaseDirectory);
		return assetDatabase;
	}

	public AssetMetadata getAssetMetadata(AssetId assetId) {
		Path metadataFilePath = assetDatabaseDirectory.resolve(assetId.getId() + ASSET_METADATA_EXTENSION);
		if (!Files.exists(metadataFilePath)) {
			Logger.error("AssetPersistence getAssetMetadata Failed to get Asset({}) MetadataFile({}): File Doesn't Exist", assetId, metadataFilePath.toString());
			return null;
		}

		try {
			String metadataContent = FileIO.readFileContent(metadataFilePath);
			JSONObject metadataFileData = new JSONObject(metadataContent);
			return AssetMetadataSerializer.deserialize(metadataFileData);
		} catch (Exception e) {
			Logger.error("AssetPersistence getAssetMetadata Failed To Read Asset({}) Metadata: {}", assetId.getId(), e.getMessage());
			return null;
		}
	}

	public AssetMetadata getAssetMetadata(String assetPath) {
		File[] metadataFiles = new File(assetDatabaseDirectory.toString()).listFiles();
		if (metadataFiles == null) {
			return null;
		}

		for (File metadataFile : metadataFiles) {
			if (!metadataFile.isFile() && !metadataFile.getName().endsWith(".jmeta")) {
				continue;
			}

			try {
				Path metadataPath = metadataFile.toPath();
				String metadataContent = FileIO.readFileContent(metadataPath);
				JSONObject metadata = new JSONObject(metadataContent);
				AssetMetadata assetMetadata = AssetMetadataSerializer.deserialize(metadata);

				if (assetMetadata.getAssetPath().equals(assetPath)) {
					return assetMetadata;
				}
			} catch (Exception e) {
				Logger.error("AssetDatabase LoadFromDisk Failed to Load Asset({}): {}", metadataFile.getName(), e.getMessage());
			}
		}
		return null;
	}

	public void saveAssetMetadata(AssetMetadata assetMetadata) {
		try {
			Path metadataFilePath = assetDatabaseDirectory.resolve(assetMetadata.getAssetId().getId() + ASSET_METADATA_EXTENSION);
			String serializedMetadata = AssetMetadataSerializer.serialize(assetMetadata).toString(4);
			FileIO.writeFileContent(metadataFilePath, serializedMetadata);
		} catch (Exception e) {
			Logger.error("AssetPersistence saveAssetMetadata Failed To Write Asset({}) Metadata: {}", assetMetadata.getAssetId().getId(), e.getMessage());
		}
	}
}
