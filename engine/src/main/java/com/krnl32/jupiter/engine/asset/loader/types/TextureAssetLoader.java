package com.krnl32.jupiter.engine.asset.loader.types;

import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.loader.AssetLoader;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializer;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializerRegistry;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.utility.FileIO;

import java.nio.file.Path;

public class TextureAssetLoader implements AssetLoader<TextureAsset> {
	@Override
	public TextureAsset load(AssetMetadata assetMetadata) {
		try {
			AssetSerializer assetSerializer = AssetSerializerRegistry.getSerializer(assetMetadata.getAssetType());
			if (assetSerializer == null) {
				Logger.error("TextureAssetLoader Failed to Load Asset({}): No Serializer for Type({})", assetMetadata.getAssetId(), assetMetadata.getAssetType());
				return null;
			}

			return (TextureAsset) assetSerializer.deserialize(FileIO.readFileContentBytes(Path.of(ProjectContext.getInstance().getAssetDirectory(), assetMetadata.getAssetPath())));
		} catch (Exception e) {
			Logger.error("TextureAssetLoader Failed to Load Asset({}): {}", assetMetadata.getAssetId(), e.getMessage());
			return null;
		}
	}

	@Override
	public void unload(TextureAsset asset) {
		asset.getTexture().destroy();
	}
}
