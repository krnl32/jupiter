package com.krnl32.jupiter.engine.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ByteUtils {
	public static void writeUInt32LE(ByteArrayOutputStream stream, int value) {
		stream.write(value & 0xFF);
		stream.write((value >> 8) & 0xFF);
		stream.write((value >> 16) & 0xFF);
		stream.write((value >> 24) & 0xFF);
	}

	public static int readUint32LE(ByteArrayInputStream stream) {
		int b1 = stream.read();
		int b2 = stream.read();
		int b3 = stream.read();
		int b4 = stream.read();
		return (b1) | (b2 << 8) | (b3 << 16) | (b4 << 24);
	}

	public static void writeUint16LE(ByteArrayOutputStream stream, int value) {
		stream.write(value & 0xFF);
		stream.write((value >> 8) & 0xFF);
	}

	public static int readUint16LE(ByteArrayInputStream stream) {
		int b1 = stream.read();
		int b2 = stream.read();
		return (b1 & 0xFF) | ((b2 & 0xFF) << 8);
	}
}
