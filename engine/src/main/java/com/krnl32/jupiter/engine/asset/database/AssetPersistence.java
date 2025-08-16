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
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AssetPersistence {
	private static final String ASSET_METADATA_EXTENSION = ".jmeta";

	private final Path assetDatabaseDirectory;
	private final Path assetRegistryFilePath;

	public AssetPersistence(Path assetDatabaseDirectory, Path assetRegistryFilePath) {
		this.assetDatabaseDirectory = assetDatabaseDirectory;
		this.assetRegistryFilePath = assetRegistryFilePath;
	}

	public AssetDatabase loadAssetDatabase() {
		try {
			AssetDatabase assetDatabase = new AssetDatabase();

			if (!Files.exists(assetDatabaseDirectory)) {
				Files.createDirectories(assetDatabaseDirectory);
				return assetDatabase;
			}

			List<Path> metadataPaths = Files.walk(assetDatabaseDirectory)
				.filter(Files::isRegularFile)
				.filter(path -> path.toString().endsWith(ASSET_METADATA_EXTENSION))
				.toList();

			for (Path metadataPath : metadataPaths) {
				String metadataContent = FileIO.readFileContent(metadataPath);
				JSONObject metadataJson = new JSONObject(metadataContent);
				AssetMetadata assetMetadata = AssetMetadataSerializer.deserialize(metadataJson);

				assetDatabase.addAssetMetadata(assetMetadata);
			}

			return assetDatabase;
		} catch (Exception e) {
			Logger.error("AssetPersistence loadAssetDatabase({}) Failed: {}", assetDatabaseDirectory, e.getMessage());
			return null;
		}
	}

	public AssetMetadata getAssetMetadata(AssetId assetId) {
		try {
			if (Files.notExists(assetDatabaseDirectory)) {
				return null;
			}

			try (Stream<Path> paths = Files.walk(assetDatabaseDirectory)) {
				return paths
					.filter(Files::isRegularFile)
					.filter(path -> path.toString().endsWith(ASSET_METADATA_EXTENSION))
					.map(path -> {
						try {
							String metadataContent = FileIO.readFileContent(path);
							JSONObject metadataJson = new JSONObject(metadataContent);

							return AssetMetadataSerializer.deserialize(metadataJson);
						} catch (Exception e) {
							Logger.error("AssetPersistence getAssetMetadata Failed To Read Asset({}) Metadata: {}", assetId.getId(), e.getMessage());
							return null;
						}
					})
					.filter(Objects::nonNull)
					.filter(assetMetadata -> assetMetadata.getAssetId().equals(assetId))
					.findFirst()
					.orElse(null);
			}
		} catch (Exception e) {
			Logger.error("AssetPersistence getAssetMetadata Failed To Read AssetDatabase({}): {}", assetDatabaseDirectory, e.getMessage());
			return null;
		}
	}

	public AssetMetadata getAssetMetadata(String assetPath) {
		Path metadataPath = assetDatabaseDirectory.resolve(assetPath);

		if (!Files.exists(metadataPath)) {
			Logger.error("AssetPersistence getAssetMetadata Failed to get Asset({}): File Doesn't Exist", assetPath);
			return null;
		}

		try {
			String metadataContent = FileIO.readFileContent(metadataPath);
			JSONObject metadataJson = new JSONObject(metadataContent);

			return AssetMetadataSerializer.deserialize(metadataJson);
		} catch (Exception e) {
			Logger.error("AssetPersistence getAssetMetadata Failed To Read Asset({}): {}", assetPath, e.getMessage());
			return null;
		}
	}

	public void saveAssetMetadata(AssetMetadata assetMetadata) {
		try {
			Path assetPath = Path.of(assetMetadata.getAssetPath());
			Path metadataPath = FileIO.replaceFileExtension(assetPath, ASSET_METADATA_EXTENSION);
			Path metadataFilePath = assetDatabaseDirectory.resolve(metadataPath);
			Files.createDirectories(metadataFilePath.getParent());

			String serializedMetadata = AssetMetadataSerializer.serialize(assetMetadata).toString(4);
			FileIO.writeFileContent(metadataFilePath, serializedMetadata);
		} catch (Exception e) {
			Logger.error("AssetPersistence saveAssetMetadata Failed To Write Asset({}) Metadata: {}", assetMetadata.getAssetId().getId(), e.getMessage());
		}
	}

	public void removeAssetMetadata(AssetId assetId) {
		removeAssetMetadata(metadata -> {
			try {
				String metadataAssetId = metadata.optString("assetId", null);

				if (metadataAssetId == null) {
					return false;
				}

				return assetId.getId().equals(UUID.fromString(metadataAssetId));
			} catch (Exception e) {
				return false;
			}
		}, assetId.toString());
	}

	public void removeAssetMetadata(String assetPath) {
		removeAssetMetadata(metadata -> {
			String metadataAssetPath = metadata.optString("assetPath", null);
			return assetPath.equals(metadataAssetPath);
		}, assetPath);
	}

	public AssetRegistry loadAssetRegistry() {
		if (!Files.exists(assetRegistryFilePath)) {
			return new AssetRegistry();
		}

		try {
			String registryContent = FileIO.readFileContent(assetRegistryFilePath);
			JSONObject jsonRegistry = new JSONObject(registryContent);

			return AssetRegistrySerializer.deserialize(jsonRegistry);
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

	private void removeAssetMetadata(Predicate<JSONObject> predicate, String source) {
		try {
			if (Files.notExists(assetDatabaseDirectory)) {
				return;
			}

			try (Stream<Path> paths = Files.walk(assetDatabaseDirectory)) {
				paths
					.filter(Files::isRegularFile)
					.filter(path -> path.toString().endsWith(ASSET_METADATA_EXTENSION))
					.filter(path -> {
						try {
							String metadataContent = FileIO.readFileContent(path);
							JSONObject metadataJson = new JSONObject(metadataContent);

							return predicate.test(metadataJson);
						} catch (Exception e) {
							return false;
						}
					})
					.findFirst()
					.ifPresent(path -> {
						try {
							Files.delete(path);
						} catch (Exception e) {
							Logger.error("AssetPersistence removeAssetMetadata Failed for Asset({}): {}", source, e.getMessage());
						}
					});
			}
		} catch (Exception e) {
			Logger.error("AssetPersistence removeAssetMetadata Failed To Read AssetDatabase({}): {}", assetDatabaseDirectory, e.getMessage());
		}
	}
}
