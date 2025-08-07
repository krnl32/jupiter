package com.krnl32.jupiter.engine.asset.importer;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.importsettings.ImportSettings;

public interface AssetImporter<T extends Asset> {
	boolean supports(ImportRequest importRequest);
	ImportResult<T> importAsset(ImportRequest request);
	ImportSettings getDefaultSettings();
}
