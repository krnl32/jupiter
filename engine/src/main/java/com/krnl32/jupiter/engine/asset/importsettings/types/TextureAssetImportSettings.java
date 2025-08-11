package com.krnl32.jupiter.engine.asset.importsettings.types;

import com.krnl32.jupiter.engine.asset.importsettings.ImportSettings;
import com.krnl32.jupiter.engine.renderer.texture.*;

import java.util.HashMap;
import java.util.Map;

public class TextureAssetImportSettings implements ImportSettings {
	private final TextureSettings settings;

	public TextureAssetImportSettings(TextureSettings settings) {
		this.settings = settings;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> data = new HashMap<>();
		data.put("type", settings.getType().name());
		data.put("format", settings.getFormat().name());
		data.put("width", settings.getWidth());
		data.put("height", settings.getHeight());
		data.put("channels", settings.getChannels());
		data.put("wrapMode", settings.getWrapMode().name());
		data.put("filterMode", settings.getFilterMode().name());
		data.put("mipmapCount", settings.getMipmapCount());
		data.put("colorSpace", settings.getColorSpace().name());
		data.put("compressionType", settings.getCompressionType().name());
		data.put("anisotropicLevel", settings.getAnisotropicLevel());

		Map<String, Object> dataFlags = new HashMap<>();
		dataFlags.put("generateMipmaps", ((settings.getFlags() & TextureFlag.GENERATE_MIPMAPS.getMask()) != 0));
		dataFlags.put("compressed", ((settings.getFlags() & TextureFlag.COMPRESSED.getMask()) != 0));
		dataFlags.put("cubemap", ((settings.getFlags() & TextureFlag.CUBEMAP.getMask()) != 0));
		dataFlags.put("sRGB", ((settings.getFlags() & TextureFlag.SRGB.getMask()) != 0));
		dataFlags.put("alpha", ((settings.getFlags() & TextureFlag.ALPHA.getMask()) != 0));
		data.put("flags", dataFlags);
		return data;
	}

	@Override
	public void fromMap(Map<String, Object> data) {
		settings.setType(TextureType.valueOf(data.get("type").toString()));
		settings.setFormat(TextureFormat.valueOf(data.get("format").toString()));
		settings.setWidth((int) data.get("width"));
		settings.setHeight((int) data.get("height"));
		settings.setChannels((int) data.get("channels"));
		settings.setWrapMode(TextureWrapMode.valueOf(data.get("wrapMode").toString()));
		settings.setFilterMode(TextureFilterMode.valueOf(data.get("filterMode").toString()));
		settings.setMipmapCount((int) data.get("mipmapCount"));
		settings.setColorSpace(TextureColorSpace.valueOf(data.get("colorSpace").toString()));
		settings.setCompressionType(TextureCompressionType.valueOf(data.get("compressionType").toString()));
		settings.setAnisotropicLevel((int) data.get("anisotropicLevel"));
		settings.setGenerateMipmaps((boolean) ((Map<String, Object>) data.get("flags")).get("generateMipmaps"));
		settings.setCompressed((boolean) ((Map<String, Object>) data.get("flags")).get("compressed"));
		settings.setCubemap((boolean) ((Map<String, Object>) data.get("flags")).get("cubemap"));
		settings.setSRGB((boolean) ((Map<String, Object>) data.get("flags")).get("sRGB"));
		settings.setAlpha((boolean) ((Map<String, Object>) data.get("flags")).get("alpha"));
	}

	public TextureSettings getSettings() {
		return settings;
	}
}
