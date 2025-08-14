package com.krnl32.jupiter.engine.asset.importer.importers;

import com.krnl32.jupiter.engine.asset.importer.AssetImporter;
import com.krnl32.jupiter.engine.asset.importer.ImportRequest;
import com.krnl32.jupiter.engine.asset.importer.ImportResult;
import com.krnl32.jupiter.engine.asset.importsettings.ImportSettings;
import com.krnl32.jupiter.engine.asset.importsettings.types.TextureAssetImportSettings;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.renderer.texture.*;
import com.krnl32.jupiter.engine.utility.FileIO;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class RasterTextureAssetImporter implements AssetImporter<TextureAsset> {
	private static final Set<String> SUPPORTED_FORMATS = Set.of(
		"png", "jpg", "jpeg", "tga", "gif", "psd",
		"hdr", "pic", "ppm", "pgm", "bmp"
	);

	@Override
	public boolean supports(ImportRequest request) {
		String fileExtension = FileIO.getFileExtension(Path.of(request.getSource()));
		return SUPPORTED_FORMATS.contains(fileExtension);
	}

	@Override
	public ImportResult<TextureAsset> importAsset(ImportRequest request) {
		TextureAssetImportSettings textureImportSettings = (TextureAssetImportSettings)
			(request.getImportSettings() == null ? getDefaultSettings() : request.getImportSettings());

		TextureSettings textureSettings = textureImportSettings.getSettings();
		TextureAsset textureAsset = new TextureAsset(textureSettings, request.getData());

		ImportResult<TextureAsset> importResult = new ImportResult<>(textureAsset, getClass().getSimpleName());
		importResult.setImportSettings("type", textureSettings.getType().name());
		importResult.setImportSettings("format", textureSettings.getFormat().name());
		importResult.setImportSettings("width", textureSettings.getWidth());
		importResult.setImportSettings("height", textureSettings.getHeight());
		importResult.setImportSettings("channels", textureSettings.getChannels());
		importResult.setImportSettings("wrapMode", textureSettings.getWrapMode().name());
		importResult.setImportSettings("filterMode", textureSettings.getFilterMode().name());
		importResult.setImportSettings("mipmapCount", textureSettings.getMipmapCount());
		importResult.setImportSettings("colorSpace", textureSettings.getColorSpace().name());
		importResult.setImportSettings("compressionType", textureSettings.getCompressionType().name());
		importResult.setImportSettings("anisotropicLevel", textureSettings.getAnisotropicLevel());
		importResult.setImportSettings("flags", Map.of(
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
