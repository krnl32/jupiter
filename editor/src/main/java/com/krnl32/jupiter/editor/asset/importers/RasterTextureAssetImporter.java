package com.krnl32.jupiter.editor.asset.importers;

import com.krnl32.jupiter.engine.asset.importer.AssetImporter;
import com.krnl32.jupiter.engine.asset.importer.ImportRequest;
import com.krnl32.jupiter.engine.asset.importer.ImportResult;
import com.krnl32.jupiter.engine.asset.importsettings.ImportSettings;
import com.krnl32.jupiter.engine.asset.importsettings.types.TextureAssetImportSettings;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.renderer.texture.*;
import com.krnl32.jupiter.engine.utility.FileIO;

import java.nio.file.Path;
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
		TextureAssetImportSettings importSettings = (TextureAssetImportSettings)
			(request.getImportSettings() == null ? getDefaultSettings() : request.getImportSettings());

		TextureAsset textureAsset = new TextureAsset(importSettings.getSettings(), request.getData());
		return new ImportResult<>(textureAsset, importSettings.toMap(), getClass().getSimpleName());
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
