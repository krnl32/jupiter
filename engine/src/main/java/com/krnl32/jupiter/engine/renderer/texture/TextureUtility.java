package com.krnl32.jupiter.engine.renderer.texture;

import static org.lwjgl.opengl.ARBTextureCompressionBPTC.*;
import static org.lwjgl.opengl.EXTTextureCompressionS3TC.*;
import static org.lwjgl.opengl.EXTTextureSRGB.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.opengl.KHRTextureCompressionASTCLDR.*;

public class TextureUtility {
	public static int TextureWrapModeToGL(TextureWrapMode textureWrapMode) {
		return switch (textureWrapMode) {
			case REPEAT -> GL_REPEAT;
			case CLAMP_TO_EDGE -> GL_CLAMP_TO_EDGE;
			case CLAMP_TO_BORDER -> GL_CLAMP_TO_BORDER;
			case MIRRORED_REPEAT -> GL_MIRRORED_REPEAT;
			case MIRROR_CLAMP_TO_EDGE -> GL_MIRROR_CLAMP_TO_EDGE;
		};
	}

	public static int TextureFilterModeToGL(TextureFilterMode textureFilterMode) {
		return switch (textureFilterMode) {
			case NEAREST -> GL_NEAREST;
			case LINEAR -> GL_LINEAR;
			case NEAREST_MIPMAP_NEAREST -> GL_NEAREST_MIPMAP_NEAREST;
			case LINEAR_MIPMAP_NEAREST -> GL_LINEAR_MIPMAP_NEAREST;
			case NEAREST_MIPMAP_LINEAR -> GL_NEAREST_MIPMAP_LINEAR;
			case LINEAR_MIPMAP_LINEAR -> GL_LINEAR_MIPMAP_LINEAR;
		};
	}

	public static int TextureFormatToGL(TextureFormat textureFormat) {
		return switch (textureFormat) {
			// Uncompressed
			case RGB8 -> GL_RGB8;
			case RGBA8 -> GL_RGBA8;
			case RGB16F -> GL_RGB16F;
			case RGBA16F -> GL_RGBA16F;
			case RGB32F -> GL_RGB32F;
			case RGBA32F -> GL_RGBA32F;

			// Luminance
			case R8 -> GL_R8;
			case R16F -> GL_R16F;
			case R32F -> GL_R32F;
			case RG8 -> GL_RG8;
			case RG16F -> GL_RG16F;
			case RG32F -> GL_RG32F;

			// Integer
			case RGBA8I -> GL_RGBA8I;
			case RGBA8UI -> GL_RGBA8UI;
			case RGBA32I -> GL_RGBA32I;
			case RGBA32UI -> GL_RGBA32UI;

			// Depth/Stencil
			case DEPTH16 -> GL_DEPTH_COMPONENT16;
			case DEPTH24 -> GL_DEPTH_COMPONENT24;
			case DEPTH32F -> GL_DEPTH_COMPONENT32F;
			case DEPTH24_STENCIL8 -> GL_DEPTH24_STENCIL8;
			case DEPTH32F_STENCIL8 -> GL_DEPTH32F_STENCIL8;

			// Compressed
			case DXT1 -> GL_COMPRESSED_RGB_S3TC_DXT1_EXT;
			case DXT5 -> GL_COMPRESSED_RGBA_S3TC_DXT5_EXT;
			case BC7 -> GL_COMPRESSED_RGBA_BPTC_UNORM_ARB;
			case ETC2_RGB -> GL_COMPRESSED_RGB8_ETC2;
			case ETC2_RGBA -> GL_COMPRESSED_RGBA8_ETC2_EAC;
			case ASTC_4x4 -> GL_COMPRESSED_RGBA_ASTC_4x4_KHR;
			case ASTC_8x8 -> GL_COMPRESSED_RGBA_ASTC_8x8_KHR;
			case PVRTC_RGB_4BPP -> 0x8C00;
			case PVRTC_RGBA_4BPP -> 0x8C02;

			// sRGB
			case SRGB8 -> GL_SRGB8;
			case SRGB8_ALPHA8 -> GL_SRGB8_ALPHA8;
			case SRGB_DXT1 -> GL_COMPRESSED_SRGB_S3TC_DXT1_EXT;
			case SRGB_DXT5 -> GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT5_EXT;
			case SRGB_BC7 -> GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM;
		};
	}
}
