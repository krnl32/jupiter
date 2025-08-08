package com.krnl32.jupiter.engine.renderer.texture;

public class TextureSettings {
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

	public TextureSettings(TextureType type, TextureWrapMode wrapMode, TextureFilterMode filterMode, boolean generateMipmaps) {
		this.type = type;
		this.wrapMode = wrapMode;
		this.filterMode = filterMode;
		setGenerateMipmaps(generateMipmaps);
	}

	public TextureSettings(TextureType type, TextureFormat format, int width, int height, int channels, TextureWrapMode wrapMode, TextureFilterMode filterMode, boolean generateMipmaps) {
		this.type = type;
		this.format = format;
		this.width = width;
		this.height = height;
		this.channels = channels;
		this.wrapMode = wrapMode;
		this.filterMode = filterMode;
		setGenerateMipmaps(generateMipmaps);
	}

	public TextureSettings(TextureType type, TextureFormat format, int width, int height, int channels, TextureWrapMode wrapMode, TextureFilterMode filterMode, int mipmapCount, TextureColorSpace colorSpace, TextureCompressionType compressionType, int anisotropicLevel, int flags) {
		this.type = type;
		this.format = format;
		this.width = width;
		this.height = height;
		this.channels = channels;
		this.wrapMode = wrapMode;
		this.filterMode = filterMode;
		this.mipmapCount = mipmapCount;
		this.colorSpace = colorSpace;
		this.compressionType = compressionType;
		this.anisotropicLevel = anisotropicLevel;
		this.flags = flags;
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
