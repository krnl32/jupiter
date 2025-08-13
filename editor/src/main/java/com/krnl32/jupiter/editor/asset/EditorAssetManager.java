package com.krnl32.jupiter.editor.asset;

import com.krnl32.jupiter.engine.asset.core.AssetManager;
import com.krnl32.jupiter.engine.asset.core.AssetReferenceManager;
import com.krnl32.jupiter.engine.asset.database.AssetRepository;
import com.krnl32.jupiter.engine.asset.handle.*;
import com.krnl32.jupiter.engine.asset.importer.AssetImportPipeline;
import com.krnl32.jupiter.engine.asset.importer.ImportRequest;
import com.krnl32.jupiter.engine.asset.importer.ImportResult;
import com.krnl32.jupiter.engine.asset.importer.importers.TextureAssetImporter;
import com.krnl32.jupiter.engine.asset.loader.AssetLoader;
import com.krnl32.jupiter.engine.asset.loader.AssetLoaderRegistry;
import com.krnl32.jupiter.engine.asset.registry.AssetEntry;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.utility.FileIO;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EditorAssetManager implements AssetManager {
	private final Map<AssetId, Asset> loadedAssets;
	private final AssetReferenceManager assetReferenceManager;
	private final AssetRepository assetRepository;
	private final AssetImportPipeline assetImportPipeline;

	public EditorAssetManager(AssetRepository assetRepository) {
		this.loadedAssets = new HashMap<>();
		this.assetReferenceManager = new AssetReferenceManager();
		this.assetRepository = assetRepository;
		this.assetImportPipeline = new AssetImportPipeline();

		this.assetImportPipeline.registerImporter(new TextureAssetImporter());
	}

	@Override
	public <T extends Asset> T getAsset(AssetId assetId) {
		if (loadedAssets.containsKey(assetId)) {
			assetReferenceManager.acquire(assetId);
			return (T) loadedAssets.get(assetId);
		}

		AssetMetadata assetMetadata = assetRepository.getAssetMetadata(assetId);
		if (assetMetadata == null) {
			Logger.error("EditorAssetManager Failed to Get Asset({}): No AssetMetadata Found", assetId);
			return null;
		}

		AssetLoader assetLoader = AssetLoaderRegistry.getLoader(assetMetadata.getAssetType());
		if (assetLoader == null) {
			Logger.error("EditorAssetManager Get Asset({}) Failed: No AssetLoader Found For Type({})", assetMetadata.getAssetId(), assetMetadata.getAssetType());
			return null;
		}

		AssetDescriptor assetDescriptor = new AssetDescriptor(assetMetadata.getAssetId(), assetMetadata.getAssetType(), Path.of(assetMetadata.getSourcePath()), assetMetadata.getImportSettings());
		Asset asset = assetLoader.load(assetDescriptor);
		if (asset == null) {
			Logger.error("EditorAssetManager Get Asset({}) Failed: AssetLoader({}) Failed", assetMetadata.getAssetId(), assetMetadata.getAssetType());
			return null;
		}

		loadedAssets.put(assetId, asset);
		assetReferenceManager.acquire(assetId);
		return (T) asset;
	}

	@Override
	public boolean isAssetLoaded(AssetId assetId) {
		return loadedAssets.containsKey(assetId);
	}

	@Override
	public boolean isAssetRegistered(AssetId assetId) {
		return assetRepository.getAssetEntry(assetId) != null;
	}

	@Override
	public void unloadAsset(AssetId assetId) {
		Asset asset = loadedAssets.get(assetId);
		if (asset == null) {
			return;
		}

		AssetLoader assetLoader = AssetLoaderRegistry.getLoader(asset.getType());
		if (assetLoader == null) {
			Logger.error("EditorAssetManager Unload({}) Failed: No AssetLoader Found For Type({})", assetId, asset.getType());
			return;
		}

		assetLoader.unload(asset);
		loadedAssets.remove(assetId);
		assetReferenceManager.release(assetId);
	}

	public AssetId importAsset(Path filePath) {
		try {
			byte[] fileContent = FileIO.readFileContentBytes(filePath);
			ImportResult result = assetImportPipeline.importAsset(new ImportRequest(filePath.toString(), fileContent, null));
			if (result == null) {
				Logger.error("EditorAssetManager Failed to ImportAsset({})", filePath);
				return null;
			}

			Asset importedAsset = result.getAsset();

			AssetMetadata assetMetadata = new AssetMetadata(
				importedAsset.getId(),
				importedAsset.getType(),
				filePath.toString(),
				null,
				result.getImporterName(),
				result.getMetadata(),
				System.currentTimeMillis()
			);
			assetRepository.saveAssetMetadata(assetMetadata);

			return result.getAsset().getId();
		} catch (Exception e) {
			Logger.error("EditorAssetManager Failed to ImportAsset({}): {}", filePath, e.getMessage());
			return null;
		}
	}

	public <T extends Asset> T getAsset(String sourcePath) {
		AssetMetadata assetMetadata = assetRepository.getAssetMetadata(sourcePath);
		if (assetMetadata == null) {
			Logger.error("EditorAssetManager Failed to Get Asset({}): No AssetMetadata Found", sourcePath);
			return null;
		}
		return getAsset(assetMetadata.getAssetId());
	}

	public Path getAssetPath(AssetId assetId) {
		AssetMetadata assetMetadata = assetRepository.getAssetMetadata(assetId);
		if (assetMetadata == null) {
			Logger.error("EditorAssetManager Failed to Get AssetPath({}): No AssetMetadata Found", assetId);
			return null;
		}
		return Path.of(assetMetadata.getSourcePath());
	}

	public AssetType getAssetType(AssetId assetId) {
		AssetEntry assetEntry = assetRepository.getAssetEntry(assetId);
		return (assetEntry != null ? assetEntry.getAssetType() : null);
	}

	public List<AssetId> getAssetIdsByType(AssetType type) {
		return assetRepository.getAssetEntries().stream()
			.filter(assetEntry -> assetEntry.getAssetType() == type)
			.map(AssetEntry::getAssetId)
			.toList();
	}

	public List<Asset> getAssetsByType(AssetType type) {
		return assetRepository.getAssetEntries().stream()
			.filter(assetEntry -> assetEntry.getAssetType() == type)
			.map(assetEntry -> (Asset) getAsset(assetEntry.getAssetId()))
			.collect(Collectors.toList());
	}
}
