package com.krnl32.jupiter.engine.asset.importer;

import com.krnl32.jupiter.engine.asset.handle.Asset;

import java.util.ArrayList;
import java.util.List;

public class AssetImportPipeline {
	private final List<AssetImporter<? extends Asset>> importers = new ArrayList<>();

	public void registerImporter(AssetImporter<? extends Asset> importer) {
		importers.add(importer);
	}

	public <T extends Asset> AssetImporter<T> getAssetImporter(ImportRequest request) {
		for (var importer : importers) {
			if (importer.supports(request)) {
				return (AssetImporter<T>) importer;
			}
		}

		return null;
	}

	public <T extends Asset> ImportResult<T> importAsset(ImportRequest request) {
		for (var importer : importers) {
			if (importer.supports(request)) {
				return (ImportResult<T>) importer.importAsset(request);
			}
		}

		return null;
	}
}
