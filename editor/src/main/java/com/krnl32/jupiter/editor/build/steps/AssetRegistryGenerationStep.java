package com.krnl32.jupiter.editor.build.steps;

import com.krnl32.jupiter.editor.build.BuildContext;
import com.krnl32.jupiter.editor.build.BuildException;
import com.krnl32.jupiter.editor.build.BuildStep;
import com.krnl32.jupiter.engine.asset.registry.AssetEntry;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistry;
import com.krnl32.jupiter.engine.asset.registry.AssetRegistrySerializer;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.utility.FileIO;

import java.nio.file.Path;
import java.util.List;

public class AssetRegistryGenerationStep implements BuildStep {
	private final Path assetRegistryFilePath;

	public AssetRegistryGenerationStep(Path assetRegistryFilePath) {
		this.assetRegistryFilePath = assetRegistryFilePath;
	}

	@Override
	public String getName() {
		return "AssetRegistryGenerationStep";
	}

	@Override
	public boolean execute(BuildContext context) throws BuildException {
		Logger.info("[{}] Generating Asset Registry...", getName());

		AssetRegistry assetRegistry = new AssetRegistry();
		List<AssetEntry> assetEntries = (List<AssetEntry>) context.getUserData("assetEntries");

		for (AssetEntry assetEntry : assetEntries) {
			assetRegistry.register(assetEntry);
			Logger.info("[{}] Registering AssetEntry({}, {}) -> {}", getName(), assetEntry.getAssetId(), assetEntry.getAssetType(), assetEntry.getAssetPath());
		}

		try {
			String serializedData = AssetRegistrySerializer.serialize(assetRegistry).toString(4);
			FileIO.writeFileContent(assetRegistryFilePath, serializedData);

			Logger.info("[{}] Generated AssetRegistry -> {}", getName(), assetRegistryFilePath);
		} catch (Exception e) {
			Logger.error("[{}] Failed to Save AssetRegistry({})", getName(), assetRegistryFilePath);
			return false;
		}

		return true;
	}
}
