package com.krnl32.jupiter.engine.asset.importsettings.types;

import com.krnl32.jupiter.engine.asset.importsettings.ImportSettings;
import com.krnl32.jupiter.engine.renderer.texture.*;

import java.util.HashMap;
import java.util.Map;

public class TextureAssetImportSettings implements ImportSettings {
	private TextureType type;
	private TextureFormat format;
	private int width, height, channels;
	private TextureWrapMode wrapMode;
	private TextureFilterMode filterMode;
	private int mipmapCount;
	private TextureColorSpace colorSpace;
	private TextureCompressionType compressionType;
	private int anisotropicLevel;
	private int flags;

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> data = new HashMap<>();
		data.put("type", type.name());
		data.put("format", format.name());
		data.put("width", width);
		data.put("height", height);
		data.put("channels", channels);
		data.put("wrapMode", wrapMode.name());
		data.put("filterMode", filterMode.name());
		data.put("mipmapCount", mipmapCount);
		data.put("colorSpace", colorSpace.name());
		data.put("compressionType", compressionType.name());
		data.put("anisotropicLevel", anisotropicLevel);

		Map<String, Object> dataFlags = new HashMap<>();
		dataFlags.put("generateMipmaps", ((flags & TextureFlag.GENERATE_MIPMAPS.getMask()) != 0));
		dataFlags.put("compressed", ((flags & TextureFlag.COMPRESSED.getMask()) != 0));
		dataFlags.put("cubemap", ((flags & TextureFlag.CUBEMAP.getMask()) != 0));
		dataFlags.put("sRGB", ((flags & TextureFlag.SRGB.getMask()) != 0));
		dataFlags.put("alpha", ((flags & TextureFlag.ALPHA.getMask()) != 0));
		data.put("flags", dataFlags);
		return data;
	}

	@Override
	public void fromMap(Map<String, Object> data) {
		type = TextureType.valueOf(data.get("type").toString());
		format = TextureFormat.valueOf(data.get("format").toString());
		width = (int) data.get("width");
		height = (int) data.get("height");
		channels = (int) data.get("channels");
		wrapMode = TextureWrapMode.valueOf(data.get("wrapMode").toString());
		filterMode = TextureFilterMode.valueOf(data.get("filterMode").toString());
		mipmapCount = (int) data.get("mipmapCount");
		colorSpace = TextureColorSpace.valueOf(data.get("colorSpace").toString());
		compressionType = TextureCompressionType.valueOf(data.get("compressionType").toString());
		anisotropicLevel = (int) data.get("anisotropicLevel");
		flags = 0;

		boolean generateMipmaps = (boolean) ((Map<String, Object>) data.get("flags")).get("generateMipmaps");
		boolean compressed = (boolean) ((Map<String, Object>) data.get("flags")).get("compressed");
		boolean cubemap = (boolean) ((Map<String, Object>) data.get("flags")).get("cubemap");
		boolean sRGB = (boolean) ((Map<String, Object>) data.get("flags")).get("sRGB");
		boolean alpha = (boolean) ((Map<String, Object>) data.get("flags")).get("alpha");

		if (generateMipmaps) {
			flags |= TextureFlag.GENERATE_MIPMAPS.getMask();
		} else {
			flags &= ~TextureFlag.GENERATE_MIPMAPS.getMask();
		}
		if (compressed) {
			flags |= TextureFlag.COMPRESSED.getMask();
		} else {
			flags &= ~TextureFlag.COMPRESSED.getMask();
		}
		if (cubemap) {
			flags |= TextureFlag.CUBEMAP.getMask();
		} else {
			flags &= ~TextureFlag.CUBEMAP.getMask();
		}
		if (sRGB) {
			flags |= TextureFlag.SRGB.getMask();
		} else {
			flags &= ~TextureFlag.SRGB.getMask();
		}
		if (alpha) {
			flags |= TextureFlag.ALPHA.getMask();
		} else {
			flags &= ~TextureFlag.ALPHA.getMask();
		}
	}

	public TextureType getType() {
		return type;
	}

	public void setType(TextureType type) {
		this.type = type;
	}

	public TextureFormat getFormat() {
		return format;
	}

	public void setFormat(TextureFormat format) {
		this.format = format;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getChannels() {
		return channels;
	}

	public void setChannels(int channels) {
		this.channels = channels;
	}

	public TextureWrapMode getWrapMode() {
		return wrapMode;
	}

	public void setWrapMode(TextureWrapMode wrapMode) {
		this.wrapMode = wrapMode;
	}

	public TextureFilterMode getFilterMode() {
		return filterMode;
	}

	public void setFilterMode(TextureFilterMode filterMode) {
		this.filterMode = filterMode;
	}

	public int getMipmapCount() {
		return mipmapCount;
	}

	public void setMipmapCount(int mipmapCount) {
		this.mipmapCount = mipmapCount;
	}

	public TextureColorSpace getColorSpace() {
		return colorSpace;
	}

	public void setColorSpace(TextureColorSpace colorSpace) {
		this.colorSpace = colorSpace;
	}

	public TextureCompressionType getCompressionType() {
		return compressionType;
	}

	public void setCompressionType(TextureCompressionType compressionType) {
		this.compressionType = compressionType;
	}

	public int getAnisotropicLevel() {
		return anisotropicLevel;
	}

	public void setAnisotropicLevel(int anisotropicLevel) {
		this.anisotropicLevel = anisotropicLevel;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public boolean isGenerateMipmaps() {
		return (flags & TextureFlag.GENERATE_MIPMAPS.getMask()) != 0;
	}

	public void setGenerateMipmaps(boolean generateMipmaps) {
		if (generateMipmaps) {
			flags |= TextureFlag.GENERATE_MIPMAPS.getMask();
		} else {
			flags &= ~TextureFlag.GENERATE_MIPMAPS.getMask();
		}
	}

	public boolean isCompressed() {
		return (flags & TextureFlag.COMPRESSED.getMask()) != 0;
	}

	public void setCompressed(boolean compressed) {
		if (compressed) {
			flags |= TextureFlag.COMPRESSED.getMask();
		} else {
			flags &= ~TextureFlag.COMPRESSED.getMask();
		}
	}

	public boolean isCubemap() {
		return (flags & TextureFlag.CUBEMAP.getMask()) != 0;
	}

	public void setCubemap(boolean cubemap) {
		if (cubemap) {
			flags |= TextureFlag.CUBEMAP.getMask();
		} else {
			flags &= ~TextureFlag.CUBEMAP.getMask();
		}
	}

	public boolean isSRGB() {
		return (flags & TextureFlag.SRGB.getMask()) != 0;
	}

	public void setSRGB(boolean sRGB) {
		if (sRGB) {
			flags |= TextureFlag.SRGB.getMask();
		} else {
			flags &= ~TextureFlag.SRGB.getMask();
		}
	}

	public boolean isAlpha() {
		return (flags & TextureFlag.ALPHA.getMask()) != 0;
	}

	public void setAlpha(boolean alpha) {
		if (alpha) {
			flags |= TextureFlag.ALPHA.getMask();
		} else {
			flags &= ~TextureFlag.ALPHA.getMask();
		}
	}
}
