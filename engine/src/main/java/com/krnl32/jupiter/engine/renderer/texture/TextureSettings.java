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

	public TextureSettings() {
	}

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

	public static class Builder {
		private TextureType type = TextureType.TEXTURE_2D;
		private TextureFormat format = TextureFormat.RGBA8;
		private int width = 1024, height = 1024, channels = 4;
		private TextureWrapMode wrapMode = TextureWrapMode.CLAMP_TO_EDGE;
		private TextureFilterMode filterMode = TextureFilterMode.LINEAR;
		private int mipmapCount = 0;
		private TextureColorSpace colorSpace = TextureColorSpace.LINEAR;
		private TextureCompressionType compressionType = TextureCompressionType.NONE;
		private int anisotropicLevel = 1;
		private int flags = 0;

		public Builder type(TextureType type) {
			this.type = type;
			return this;
		}

		public Builder format(TextureFormat format) {
			this.format = format;
			return this;
		}

		public Builder width(int width) {
			this.width = width;
			return this;
		}

		public Builder height(int height) {
			this.height = height;
			return this;
		}

		public Builder channels(int channels) {
			this.channels = channels;
			return this;
		}

		public Builder wrapMode(TextureWrapMode wrapMode) {
			this.wrapMode = wrapMode;
			return this;
		}

		public Builder filterMode(TextureFilterMode filterMode) {
			this.filterMode = filterMode;
			return this;
		}

		public Builder mipmapCount(int mipmapCount) {
			this.mipmapCount = mipmapCount;
			return this;
		}

		public Builder colorSpace(TextureColorSpace colorSpace) {
			this.colorSpace = colorSpace;
			return this;
		}

		public Builder compressionType(TextureCompressionType compressionType) {
			this.compressionType = compressionType;
			return this;
		}

		public Builder anisotropicLevel(int level) {
			this.anisotropicLevel = level;
			return this;
		}

		public Builder setFlag(TextureFlag flag, boolean enabled) {
			if (enabled) {
				flags |= flag.getMask();
			} else {
				flags &= ~flag.getMask();
			}
			return this;
		}

		public Builder generateMipmaps(boolean enabled) {
			return setFlag(TextureFlag.GENERATE_MIPMAPS, enabled);
		}

		public Builder compressed(boolean enabled) {
			return setFlag(TextureFlag.COMPRESSED, enabled);
		}

		public Builder cubemap(boolean enabled) {
			return setFlag(TextureFlag.CUBEMAP, enabled);
		}

		public Builder sRGB(boolean enabled) {
			return setFlag(TextureFlag.SRGB, enabled);
		}

		public Builder alpha(boolean enabled) {
			return setFlag(TextureFlag.ALPHA, enabled);
		}

		public TextureSettings build() {
			return new TextureSettings(
				type, format, width, height, channels,
				wrapMode, filterMode, mipmapCount,
				colorSpace, compressionType,
				anisotropicLevel, flags
			);
		}
	}
}
