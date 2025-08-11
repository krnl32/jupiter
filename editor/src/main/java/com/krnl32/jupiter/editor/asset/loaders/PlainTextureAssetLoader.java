package com.krnl32.jupiter.editor.asset.loaders;

import com.krnl32.jupiter.engine.asset.handle.AssetDescriptor;
import com.krnl32.jupiter.engine.asset.loader.AssetLoader;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.renderer.texture.*;
import com.krnl32.jupiter.engine.utility.FileIO;

import java.nio.file.Path;
import java.util.Map;

public class PlainTextureAssetLoader implements AssetLoader<TextureAsset> {
	@Override
	public TextureAsset load(AssetDescriptor assetDescriptor) {
		try {
			// Get Raw Asset Data
			Path assetPath = ProjectContext.getInstance().getAssetDirectory().resolve(assetDescriptor.getAssetPath());
			byte[] assetRawData = FileIO.readFileContentBytes(assetPath);

			// Get Texture Import Settings
			TextureType type = TextureType.valueOf(assetDescriptor.getSettings().get("type").toString());
			TextureFormat format = TextureFormat.valueOf(assetDescriptor.getSettings().get("format").toString());
			int width = (int) assetDescriptor.getSettings().get("width");
			int height = (int) assetDescriptor.getSettings().get("height");
			int channels = (int) assetDescriptor.getSettings().get("channels");
			TextureWrapMode wrapMode = TextureWrapMode.valueOf(assetDescriptor.getSettings().get("wrapMode").toString());
			TextureFilterMode filterMode = TextureFilterMode.valueOf(assetDescriptor.getSettings().get("filterMode").toString());
			int mipmapCount = (int) assetDescriptor.getSettings().get("mipmapCount");
			TextureColorSpace colorSpace = TextureColorSpace.valueOf(assetDescriptor.getSettings().get("colorSpace").toString());
			TextureCompressionType compressionType = TextureCompressionType.valueOf(assetDescriptor.getSettings().get("compressionType").toString());
			int anisotropicLevel = (int) assetDescriptor.getSettings().get("anisotropicLevel");
			int flags = 0;

			boolean generateMipmaps = (boolean) ((Map<String, Object>) assetDescriptor.getSettings().get("flags")).get("generateMipmaps");
			if (generateMipmaps) {
				flags |= TextureFlag.GENERATE_MIPMAPS.getMask();
			} else {
				flags &= ~TextureFlag.GENERATE_MIPMAPS.getMask();
			}

			boolean compressed = (boolean) ((Map<String, Object>) assetDescriptor.getSettings().get("flags")).get("compressed");
			if (compressed) {
				flags |= TextureFlag.COMPRESSED.getMask();
			} else {
				flags &= ~TextureFlag.COMPRESSED.getMask();
			}

			boolean cubemap = (boolean) ((Map<String, Object>) assetDescriptor.getSettings().get("flags")).get("cubemap");
			if (cubemap) {
				flags |= TextureFlag.CUBEMAP.getMask();
			} else {
				flags &= ~TextureFlag.CUBEMAP.getMask();
			}

			boolean sRGB = (boolean) ((Map<String, Object>) assetDescriptor.getSettings().get("flags")).get("sRGB");
			if (sRGB) {
				flags |= TextureFlag.SRGB.getMask();
			} else {
				flags &= ~TextureFlag.SRGB.getMask();
			}

			boolean alpha = (boolean) ((Map<String, Object>) assetDescriptor.getSettings().get("flags")).get("alpha");
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

			return new TextureAsset(assetDescriptor.getAssetId(), settings, assetRawData);
		} catch (Exception e) {
			Logger.error("JSONTextureAssetLoader Failed to Load Asset({}): {}", assetDescriptor.getAssetId(), e.getMessage());
			return null;
		}
	}

	@Override
	public void unload(TextureAsset asset) {
	}
}
