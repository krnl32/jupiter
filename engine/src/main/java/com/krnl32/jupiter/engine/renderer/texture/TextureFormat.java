package com.krnl32.jupiter.engine.renderer.texture;

public enum TextureFormat {
	// Uncompressed
	RGB8,
	RGBA8,
	RGB16F,
	RGBA16F,
	RGB32F,
	RGBA32F,

	// Luminance
	R8,
	R16F,
	R32F,
	RG8,
	RG16F,
	RG32F,

	// Integer
	RGBA8I,
	RGBA8UI,
	RGBA32I,
	RGBA32UI,

	// Depth/Stencil
	DEPTH16,
	DEPTH24,
	DEPTH32F,
	DEPTH24_STENCIL8,
	DEPTH32F_STENCIL8,

	// Compressed
	DXT1,
	DXT5,
	BC7,
	ETC2_RGB,
	ETC2_RGBA,
	ASTC_4x4,
	ASTC_8x8,
	PVRTC_RGB_4BPP,
	PVRTC_RGBA_4BPP,

	// sRGB
	SRGB8,
	SRGB8_ALPHA8,
	SRGB_DXT1,
	SRGB_DXT5,
	SRGB_BC7
}
