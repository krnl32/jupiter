package com.krnl32.jupiter.engine.asset.model.jtexture;

import com.krnl32.jupiter.engine.renderer.texture.*;

import java.util.Objects;

public final class JTextureHeader {
	private final TextureType type;
	private final TextureFormat format;
	private final int width, height, channels;
	private final TextureWrapMode wrapMode;
	private final TextureFilterMode filterMode;
	private final int mipmapCount;
	private final TextureColorSpace colorSpace;
	private final TextureCompressionType compressionType;
	private final int anisotropicLevel;
	private final int flags;

	public JTextureHeader(TextureType type, TextureFormat format, int width, int height, int channels, TextureWrapMode wrapMode, TextureFilterMode filterMode, int mipmapCount, TextureColorSpace colorSpace, TextureCompressionType compressionType, int anisotropicLevel, int flags) {
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

	public TextureFormat getFormat() {
		return format;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getChannels() {
		return channels;
	}

	public TextureWrapMode getWrapMode() {
		return wrapMode;
	}

	public TextureFilterMode getFilterMode() {
		return filterMode;
	}

	public int getMipmapCount() {
		return mipmapCount;
	}

	public TextureColorSpace getColorSpace() {
		return colorSpace;
	}

	public TextureCompressionType getCompressionType() {
		return compressionType;
	}

	public int getAnisotropicLevel() {
		return anisotropicLevel;
	}

	public int getFlags() {
		return flags;
	}

	public boolean isGenerateMipmaps() {
		return (flags & TextureFlag.GENERATE_MIPMAPS.getMask()) != 0;
	}

	public boolean isCompressed() {
		return (flags & TextureFlag.COMPRESSED.getMask()) != 0;
	}

	public boolean isCubemap() {
		return (flags & TextureFlag.CUBEMAP.getMask()) != 0;
	}

	public boolean isSRGB() {
		return (flags & TextureFlag.SRGB.getMask()) != 0;
	}

	public boolean isAlpha() {
		return (flags & TextureFlag.ALPHA.getMask()) != 0;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		JTextureHeader that = (JTextureHeader) o;
		return width == that.width && height == that.height && channels == that.channels && mipmapCount == that.mipmapCount && anisotropicLevel == that.anisotropicLevel && flags == that.flags && type == that.type && format == that.format && wrapMode == that.wrapMode && filterMode == that.filterMode && colorSpace == that.colorSpace && compressionType == that.compressionType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, format, width, height, channels, wrapMode, filterMode, mipmapCount, colorSpace, compressionType, anisotropicLevel, flags);
	}

	@Override
	public String toString() {
		return "JTextureHeader{" +
			"type=" + type +
			", format=" + format +
			", width=" + width +
			", height=" + height +
			", channels=" + channels +
			", wrapMode=" + wrapMode +
			", filterMode=" + filterMode +
			", mipmapCount=" + mipmapCount +
			", colorSpace=" + colorSpace +
			", compressionType=" + compressionType +
			", anisotropicLevel=" + anisotropicLevel +
			", flags=" + flags +
			'}';
	}
}
