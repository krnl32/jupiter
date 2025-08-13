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
		TextureAssetImportSettings textureImportSettings;
		if (request.getImportSettings() == null) {
			textureImportSettings = (TextureAssetImportSettings) getDefaultSettings();
		} else {
			textureImportSettings = (TextureAssetImportSettings) request.getImportSettings();
		}

		TextureSettings textureSettings = textureImportSettings.getSettings();

		TextureAsset textureAsset = new TextureAsset(textureSettings, request.getData());

		ImportResult<TextureAsset> importResult = new ImportResult<>(textureAsset, getClass().getSimpleName());
		importResult.setMetadata("type", textureSettings.getType().name());
		importResult.setMetadata("format", textureSettings.getFormat().name());
		importResult.setMetadata("width", textureSettings.getWidth());
		importResult.setMetadata("height", textureSettings.getHeight());
		importResult.setMetadata("channels", textureSettings.getChannels());
		importResult.setMetadata("wrapMode", textureSettings.getWrapMode().name());
		importResult.setMetadata("filterMode", textureSettings.getFilterMode().name());
		importResult.setMetadata("mipmapCount", textureSettings.getMipmapCount());
		importResult.setMetadata("colorSpace", textureSettings.getColorSpace().name());
		importResult.setMetadata("compressionType", textureSettings.getCompressionType().name());
		importResult.setMetadata("anisotropicLevel", textureSettings.getAnisotropicLevel());
		importResult.setMetadata("flags", Map.of(
			"generateMipmaps", textureSettings.isGenerateMipmaps(),
			"compressed", textureSettings.isCompressed(),
			"cubemap", textureSettings.isCubemap(),
			"sRGB", textureSettings.isSRGB(),
			"alpha", textureSettings.isAlpha()
		));

		return importResult;
	}

	@Override
	public ImportSettings getDefaultSettings() {
		TextureSettings settings = new TextureSettings.Builder()
			.type(TextureType.TEXTURE_2D)
			.format(TextureFormat.RGBA8)
			.width(1024)
			.height(1024)
			.channels(4)
			.wrapMode(TextureWrapMode.CLAMP_TO_EDGE)
			.filterMode(TextureFilterMode.LINEAR)
			.mipmapCount(0)
			.colorSpace(TextureColorSpace.LINEAR)
			.compressionType(TextureCompressionType.NONE)
			.anisotropicLevel(4)
			.generateMipmaps(true)
			.compressed(false)
			.cubemap(false)
			.sRGB(false)
			.alpha(true)
			.build();
		return new TextureAssetImportSettings(settings);
	}
}
