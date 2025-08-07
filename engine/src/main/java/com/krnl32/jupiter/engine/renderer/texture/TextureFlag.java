package com.krnl32.jupiter.engine.renderer.texture;

public enum TextureFlag {
	GENERATE_MIPMAPS(0x01),
	COMPRESSED(0x02),
	CUBEMAP(0x04),
	SRGB(0x08),
	ALPHA(0x10);

	private final int value;

	TextureFlag(int value) {
		this.value = value;
	}

	public int getMask() {
		return value;
	}
}
