package com.krnl32.jupiter.engine.asset.importer.importers;

import com.krnl32.jupiter.engine.asset.importer.AssetImporter;
import com.krnl32.jupiter.engine.asset.importer.ImportRequest;
import com.krnl32.jupiter.engine.asset.importer.ImportResult;
import com.krnl32.jupiter.engine.asset.importsettings.ImportSettings;
import com.krnl32.jupiter.engine.asset.importsettings.types.TextureAssetImportSettings;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.renderer.texture.*;

import java.nio.file.Path;
import java.util.Map;

public class TextureAssetImporter implements AssetImporter<TextureAsset> {
	@Override
	public boolean supports(ImportRequest importRequest) {
		String fileName = Path.of(importRequest.getSource()).getFileName().toString().toLowerCase();
		return fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg");
	}

	@Override
	public ImportResult<TextureAsset> importAsset(ImportRequest request) {
		TextureAssetImportSettings textureImportSettings = (TextureAssetImportSettings) request.getImportSettings();

		TextureSettings textureSettings = new TextureSettings(textureImportSettings.getType(), textureImportSettings.getFormat(), textureImportSettings.getWidth(),
			textureImportSettings.getHeight(), textureImportSettings.getChannels(), textureImportSettings.getWrapMode(), 
			textureImportSettings.getFilterMode(), textureImportSettings.getMipmapCount(), textureImportSettings.getColorSpace(),
			textureImportSettings.getCompressionType(), textureImportSettings.getAnisotropicLevel(), textureImportSettings.getFlags());

		TextureAsset textureAsset = new TextureAsset(textureSettings, request.getData());

		ImportResult<TextureAsset> importResult = new ImportResult<>(textureAsset);
		importResult.setMetadata("type", textureImportSettings.getType().name());
		importResult.setMetadata("format", textureImportSettings.getFormat().name());
		importResult.setMetadata("width", textureImportSettings.getWidth());
		importResult.setMetadata("height", textureImportSettings.getHeight());
		importResult.setMetadata("channels", textureImportSettings.getChannels());
		importResult.setMetadata("wrapMode", textureImportSettings.getWrapMode().name());
		importResult.setMetadata("filterMode", textureImportSettings.getFilterMode().name());
		importResult.setMetadata("mipmapCount", textureImportSettings.getMipmapCount());
		importResult.setMetadata("colorSpace", textureImportSettings.getColorSpace().name());
		importResult.setMetadata("compressionType", textureImportSettings.getCompressionType().name());
		importResult.setMetadata("anisotropicLevel", textureImportSettings.getAnisotropicLevel());
		importResult.setMetadata("flags", Map.of(
			"generateMipmaps", textureImportSettings.isGenerateMipmaps(),
			"compressed", textureImportSettings.isCompressed(),
			"cubemap", textureImportSettings.isCubemap(),
			"sRGB", textureImportSettings.isSRGB(),
			"alpha", textureImportSettings.isAlpha()
		));

		return importResult;
	}

	@Override
	public ImportSettings getDefaultSettings() {
		TextureAssetImportSettings importSettings = new TextureAssetImportSettings();
		importSettings.setType(TextureType.TEXTURE_2D);
		importSettings.setFormat(TextureFormat.RGBA8);
		importSettings.setWidth(1024);
		importSettings.setHeight(1024);
		importSettings.setChannels(4);
		importSettings.setWrapMode(TextureWrapMode.CLAMP_TO_EDGE);
		importSettings.setFilterMode(TextureFilterMode.LINEAR);
		importSettings.setMipmapCount(0);
		importSettings.setColorSpace(TextureColorSpace.LINEAR);
		importSettings.setCompressionType(TextureCompressionType.NONE);
		importSettings.setAnisotropicLevel(4);
		importSettings.setFlags(0);
		importSettings.setGenerateMipmaps(true);
		importSettings.setCompressed(false);
		importSettings.setCubemap(false);
		importSettings.setSRGB(false);
		importSettings.setAlpha(true);
		return importSettings;
	}
}
