package com.krnl32.jupiter.engine.asset.loader.types;

import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.loader.AssetLoader;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializer;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializerRegistry;
import com.krnl32.jupiter.engine.asset.serializer.model.jtexture.JTexture;
import com.krnl32.jupiter.engine.asset.serializer.model.jtexture.JTextureHeader;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.renderer.texture.TextureSettings;
import com.krnl32.jupiter.engine.utility.FileIO;

import java.nio.file.Path;

public class JTextureAssetLoader implements AssetLoader<TextureAsset> {
	@Override
	public TextureAsset load(AssetMetadata assetMetadata) {
		try {
			AssetSerializer<TextureAsset, JTexture> assetSerializer = AssetSerializerRegistry.getSerializer(assetMetadata.getAssetType());
			if (assetSerializer == null) {
				Logger.error("JTextureAssetLoader Failed to Load Asset({}): No Serializer for Type({})", assetMetadata.getAssetId(), assetMetadata.getAssetType());
				return null;
			}

			Path assetPath = ProjectContext.getInstance().getAssetDirectory().resolve(assetMetadata.getAssetPath());
			byte[] assetData = FileIO.readFileContentBytes(assetPath);
			JTexture jTexture = assetSerializer.deserialize(assetData);
			if (jTexture == null) {
				Logger.error("JTextureAssetLoader AssetSerializer Failed to Deserialize Asset({})", assetMetadata.getAssetId());
				return null;
			}

			JTextureHeader jHeader = jTexture.getHeader();
			TextureSettings settings = new TextureSettings(
				jHeader.getType(),
				jHeader.getFormat(),
				jHeader.getWidth(),
				jHeader.getHeight(),
				jHeader.getChannels(),
				jHeader.getWrapMode(),
				jHeader.getFilterMode(),
				jHeader.getMipmapCount(),
				jHeader.getColorSpace(),
				jHeader.getCompressionType(),
				jHeader.getAnisotropicLevel(),
				jHeader.getFlags()
			);

			return new TextureAsset(assetMetadata.getAssetId(), settings, jTexture.getData());
		} catch (Exception e) {
			Logger.error("JTextureAssetLoader Failed to Load Asset({}): {}", assetMetadata.getAssetId(), e.getMessage());
			return null;
		}
	}

	@Override
	public void unload(TextureAsset asset) {
		asset.getTexture().destroy();
	}
}
