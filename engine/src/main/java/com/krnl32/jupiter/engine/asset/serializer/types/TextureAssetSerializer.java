package com.krnl32.jupiter.engine.asset.serializer.types;

import com.krnl32.jupiter.engine.asset.serializer.AssetSerializer;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.renderer.texture.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Jupiter .jtexture Binary Format (v1.0)
 *
 * HEADER (size: 27+5 bytes)
 * ------------------------------------------
 * 4 Bytes  Magic				"JTEX"
 * 1 Byte   Version				0x01
 * 1 Byte   Type				(0=2D, 1=3D, 2=CUBEMAP)
 * 1 Byte   Format				enum (TextureFormat enum index)
 * 4 Bytes  Width				uint32
 * 4 Bytes  Height				uint32
 * 4 Bytes  Channels			uint32 (3=RGB, 4=RGBA)
 * 1 Byte   WrapMode			enum (TextureWrapMode enum Index)
 * 1 Byte   FilterMode			enum (TextureFilterMode enum Index)
 * 1 Byte	MipmapCount			uint8
 * 1 Byte	ColorSpace			enum (TextureColorSpace enum Index)
 * 1 Byte	CompressionType		enum (TextureCompressionType enum Index)
 * 1 Byte	AnisotropicLevel	uint8
 * 2 Bytes	Flags				uint8 (0x01=Generate Mipmaps, 0x02=Compressed, 0x04=Cubemap, 0x08=sRGB, 0x10=ALPHA)
 * 5 Bytes	RESERVED
 * ------------------------------------------
 * DATA
 * Followed by raw or compressed image data
 */
public class TextureAssetSerializer implements AssetSerializer<TextureAsset> {
	private static final byte[] MAGIC = new byte[]{'J', 'T', 'E', 'X'};
	private static final byte VERSION = 0x1;

	@Override
	public byte[] serialize(TextureAsset asset) {
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		try {
			dataStream.write(MAGIC);
			dataStream.write(VERSION);
			dataStream.write(asset.getSettings().getType().ordinal());
			dataStream.write(asset.getSettings().getFormat().ordinal());
			writeUInt32(dataStream, asset.getSettings().getWidth());
			writeUInt32(dataStream, asset.getSettings().getHeight());
			writeUInt32(dataStream, asset.getSettings().getChannels());
			dataStream.write(asset.getSettings().getWrapMode().ordinal());
			dataStream.write(asset.getSettings().getFilterMode().ordinal());
			dataStream.write(asset.getSettings().getMipmapCount());
			dataStream.write(asset.getSettings().getColorSpace().ordinal());
			dataStream.write(asset.getSettings().getCompressionType().ordinal());
			dataStream.write(asset.getSettings().getAnisotropicLevel());
			writeUint16(dataStream, asset.getSettings().getFlags());
			dataStream.write(new byte[5]);
			dataStream.write(asset.getData());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return dataStream.toByteArray();
	}

	@Override
	public TextureAsset deserialize(byte[] inputData) {
		ByteArrayInputStream inputDataStream = new ByteArrayInputStream(inputData);

		// Validate Magic
		byte[] magic = new byte[4];
		inputDataStream.read(magic, 0, 4);
		if (!Arrays.equals(magic, MAGIC)) {
			Logger.error("TextureAssetSerializer Failed to Deserialize Invalid Magic Header");
			return null;
		}

		// Validate Version
		int version = inputDataStream.read();
		if (version > VERSION) {
			Logger.error("TextureAssetSerializer Failed to Deserialize Invalid Version");
			return null;
		}

		TextureType type = TextureType.values()[inputDataStream.read()];
		TextureFormat format = TextureFormat.values()[inputDataStream.read()];
		int width = readUint32(inputDataStream);
		int height = readUint32(inputDataStream);
		int channels = readUint32(inputDataStream);
		TextureWrapMode wrapMode = TextureWrapMode.values()[inputDataStream.read()];
		TextureFilterMode filterMode = TextureFilterMode .values()[inputDataStream.read()];
		int mipmapCount = inputDataStream.read();
		TextureColorSpace colorSpace = TextureColorSpace.values()[inputDataStream.read()];
		TextureCompressionType compressionType = TextureCompressionType.values()[inputDataStream.read()];
		int anisotropicLevel = inputDataStream.read();
		int flags = readUint16(inputDataStream);
		inputDataStream.skip(5);
		byte[] data = inputDataStream.readAllBytes();

		TextureSettings settings = new TextureSettings(type, format, width, height, channels,
			wrapMode, filterMode, mipmapCount, colorSpace, compressionType, anisotropicLevel, flags);

		return new TextureAsset(settings, data);
	}

	private void writeUInt32(ByteArrayOutputStream stream, int value) {
		stream.write(value & 0xFF);
		stream.write((value >> 8) & 0xFF);
		stream.write((value >> 16) & 0xFF);
		stream.write((value >> 24) & 0xFF);
	}

	private int readUint32(ByteArrayInputStream stream) {
		int b1 = stream.read();
		int b2 = stream.read();
		int b3 = stream.read();
		int b4 = stream.read();
		return (b1) | (b2 << 8) | (b3 << 16) | (b4 << 24);
	}

	private void writeUint16(ByteArrayOutputStream stream, int value) {
		stream.write(value & 0xFF);
		stream.write((value >> 8) & 0xFF);
	}

	private int readUint16(ByteArrayInputStream stream) {
		int b1 = stream.read();
		int b2 = stream.read();
		return (b1 & 0xFF) | ((b2 & 0xFF) << 8);
	}
}
