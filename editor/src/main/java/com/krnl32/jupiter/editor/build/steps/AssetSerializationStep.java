package com.krnl32.jupiter.editor.build.steps;

import com.krnl32.jupiter.editor.asset.EditorAssetManager;
import com.krnl32.jupiter.editor.build.BuildContext;
import com.krnl32.jupiter.editor.build.BuildException;
import com.krnl32.jupiter.editor.build.BuildStep;
import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.asset.registry.AssetEntry;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializer;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializerRegistry;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.utility.FileIO;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AssetSerializationStep implements BuildStep {
	private final EditorAssetManager assetManager;
	private final AssetSerializerRegistry assetSerializerRegistry;

	public AssetSerializationStep(EditorAssetManager assetManager, AssetSerializerRegistry assetSerializerRegistry) {
		this.assetManager = assetManager;
		this.assetSerializerRegistry = assetSerializerRegistry;
	}

	@Override
	public String getName() {
		return "AssetSerializationStep";
	}

	@Override
	public boolean execute(BuildContext context) throws BuildException {
		Logger.info("[{}] Serializing Assets...", getName());

		List<AssetEntry> assetEntries = new ArrayList<>();

		for (Asset asset : assetManager.getAssets()) {
			AssetType assetType = asset.getType();
			AssetSerializer assetSerializer = assetSerializerRegistry.getSerializer(assetType);

			if (assetSerializer == null) {
				Logger.error("[{}] Skipping Asset({}), No Serializer Found for Type({})", getName(), asset.getId(), assetType);
				continue;
			}

			try {
				String assetExtension = assetTypeToExtension(assetType);
				Path assetPath = assetManager.getAssetPath(asset.getId());
				Path nativeAssetPath = FileIO.replaceFileExtension(assetPath, assetExtension);
				Path serializedFileOutput = context.getOutputDirectory().resolve(nativeAssetPath);
				Files.createDirectories(serializedFileOutput.getParent());

				byte[] serializedData = assetSerializer.serialize(asset);

				if (serializedData == null) {
					Logger.error("[{}] Skipping Asset({}), Serialization Failed for Type({})", getName(), asset.getId(), assetType);
				}

				FileIO.writeFileContentBytes(serializedFileOutput, serializedData);

				String relativeNativeAssetPath = serializedFileOutput.toString().replace(ProjectContext.getInstance().getAssetDirectory().toString(), "").substring(1).replace("\\", "/");
				assetEntries.add(new AssetEntry(asset.getId(), assetType, relativeNativeAssetPath));

				Logger.info("[{}] Serialized Asset({}, {}) -> {}", getName(), asset.getId(), assetType, relativeNativeAssetPath);
			} catch (Exception e) {
				Logger.error("[{}] Skipping Asset({}), Failed to Save Serialized Data for Type({})", getName(), asset.getId(), assetType);
			}
		}

		context.setUserData("assetEntries", assetEntries);
		return true;
	}

	private String assetTypeToExtension(AssetType type) {
		return switch (type) {
			case TEXTURE -> "jtexture";
			case SCENE -> "jscene";
			case SCRIPT -> "jscript";
		};
	}
}
