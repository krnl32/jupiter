package com.krnl32.jupiter.engine.asset.loader.types;

import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.loader.AssetLoader;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.renderer.texture.*;
import com.krnl32.jupiter.engine.utility.FileIO;

import java.nio.file.Path;
import java.util.Map;

public class JSONTextureAssetLoader implements AssetLoader<TextureAsset> {
	@Override
	public TextureAsset load(AssetMetadata assetMetadata) {
		try {
			// Get Raw Asset Data
			Path assetPath = ProjectContext.getInstance().getAssetDirectory().resolve(assetMetadata.getSourcePath());
			byte[] assetRawData = FileIO.readFileContentBytes(assetPath);

			// Get Texture Import Settings
			TextureType type = TextureType.valueOf(assetMetadata.getImportSettings().get("type").toString());
			TextureFormat format = TextureFormat.valueOf(assetMetadata.getImportSettings().get("format").toString());
			int width = (int) assetMetadata.getImportSettings().get("width");
			int height = (int) assetMetadata.getImportSettings().get("height");
			int channels = (int) assetMetadata.getImportSettings().get("channels");
			TextureWrapMode wrapMode = TextureWrapMode.valueOf(assetMetadata.getImportSettings().get("wrapMode").toString());
			TextureFilterMode filterMode = TextureFilterMode.valueOf(assetMetadata.getImportSettings().get("filterMode").toString());
			int mipmapCount = (int) assetMetadata.getImportSettings().get("mipmapCount");
			TextureColorSpace colorSpace = TextureColorSpace.valueOf(assetMetadata.getImportSettings().get("colorSpace").toString());
			TextureCompressionType compressionType = TextureCompressionType.valueOf(assetMetadata.getImportSettings().get("compressionType").toString());
			int anisotropicLevel = (int) assetMetadata.getImportSettings().get("anisotropicLevel");
			int flags = 0;

			boolean generateMipmaps = (boolean) ((Map<String, Object>) assetMetadata.getImportSettings().get("flags")).get("generateMipmaps");
			if (generateMipmaps) {
				flags |= TextureFlag.GENERATE_MIPMAPS.getMask();
			} else {
				flags &= ~TextureFlag.GENERATE_MIPMAPS.getMask();
			}

			boolean compressed = (boolean) ((Map<String, Object>) assetMetadata.getImportSettings().get("flags")).get("compressed");
			if (compressed) {
				flags |= TextureFlag.COMPRESSED.getMask();
			} else {
				flags &= ~TextureFlag.COMPRESSED.getMask();
			}

			boolean cubemap = (boolean) ((Map<String, Object>) assetMetadata.getImportSettings().get("flags")).get("cubemap");
			if (cubemap) {
				flags |= TextureFlag.CUBEMAP.getMask();
			} else {
				flags &= ~TextureFlag.CUBEMAP.getMask();
			}

			boolean sRGB = (boolean) ((Map<String, Object>) assetMetadata.getImportSettings().get("flags")).get("sRGB");
			if (sRGB) {
				flags |= TextureFlag.SRGB.getMask();
			} else {
				flags &= ~TextureFlag.SRGB.getMask();
			}

			boolean alpha = (boolean) ((Map<String, Object>) assetMetadata.getImportSettings().get("flags")).get("alpha");
			if (alpha) {
				flags |= TextureFlag.ALPHA.getMask();
			} else {
				flags &= ~TextureFlag.ALPHA.getMask();
			}

			TextureSettings settings = new TextureSettings(
				type,
				format,
				width,
				height,
				channels,
				wrapMode,
				filterMode,
				mipmapCount,
				colorSpace,
				compressionType,
				anisotropicLevel,
				flags
			);

			return new TextureAsset(assetMetadata.getAssetId(), settings, assetRawData);
		} catch (Exception e) {
			Logger.error("JSONTextureAssetLoader Failed to Load Asset({}): {}", assetMetadata.getAssetId(), e.getMessage());
			return null;
		}
	}

	@Override
	public void unload(TextureAsset asset) {
	}
}
