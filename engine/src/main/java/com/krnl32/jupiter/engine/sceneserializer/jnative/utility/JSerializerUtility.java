package com.krnl32.jupiter.engine.sceneserializer.jnative.utility;

import org.joml.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class JSerializerUtility {
	// JOML Math Types
	public static byte[] serializeVector2f(Vector2fc vec) {
		ByteBuffer buffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putFloat(vec.x());
		buffer.putFloat(vec.y());
		return buffer.array();
	}

	public static void serializeVector2f(ByteBuffer buffer, Vector2fc vec) {
		buffer.putFloat(vec.x());
		buffer.putFloat(vec.y());
	}

	public static Vector2f deserializeVector2f(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		return new Vector2f(x, y);
	}

	public static Vector2f deserializeVector2f(ByteBuffer buffer) {
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		return new Vector2f(x, y);
	}

	public static byte[] serializeVector3f(Vector3fc vec) {
		ByteBuffer buffer = ByteBuffer.allocate(12).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putFloat(vec.x());
		buffer.putFloat(vec.y());
		buffer.putFloat(vec.z());
		return buffer.array();
	}

	public static void serializeVector3f(ByteBuffer buffer, Vector3fc vec) {
		buffer.putFloat(vec.x());
		buffer.putFloat(vec.y());
		buffer.putFloat(vec.z());
	}

	public static Vector3f deserializeVector3f(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		float z = buffer.getFloat();
		return new Vector3f(x, y, z);
	}

	public static Vector3f deserializeVector3f(ByteBuffer buffer) {
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		float z = buffer.getFloat();
		return new Vector3f(x, y, z);
	}

	public static byte[] serializeVector4f(Vector4fc vec) {
		ByteBuffer buffer = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putFloat(vec.x());
		buffer.putFloat(vec.y());
		buffer.putFloat(vec.z());
		buffer.putFloat(vec.w());
		return buffer.array();
	}

	public static void serializeVector4f(ByteBuffer buffer, Vector4fc vec) {
		buffer.putFloat(vec.x());
		buffer.putFloat(vec.y());
		buffer.putFloat(vec.z());
		buffer.putFloat(vec.w());
	}

	public static Vector4f deserializeVector4f(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		float z = buffer.getFloat();
		float w = buffer.getFloat();
		return new Vector4f(x, y, z, w);
	}

	public static Vector4f deserializeVector4f(ByteBuffer buffer) {
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		float z = buffer.getFloat();
		float w = buffer.getFloat();
		return new Vector4f(x, y, z, w);
	}

	// UUID
	public static byte[] serializeUUID(UUID uuid) {
		ByteBuffer buffer = ByteBuffer.allocate(16);
		buffer.putLong(uuid.getMostSignificantBits());
		buffer.putLong(uuid.getLeastSignificantBits());
		return buffer.array();
	}

	public static void serializeUUID(ByteBuffer buffer, UUID uuid) {
		buffer.putLong(uuid.getMostSignificantBits());
		buffer.putLong(uuid.getLeastSignificantBits());
	}

	public static UUID deserializeUUID(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		long mostSignificantBits = buffer.getLong();
		long leastSignificantBits = buffer.getLong();
		return new UUID(mostSignificantBits, leastSignificantBits);
	}

	public static UUID deserializeUUID(ByteBuffer buffer) {
		long mostSignificantBits = buffer.getLong();
		long leastSignificantBits = buffer.getLong();
		return new UUID(mostSignificantBits, leastSignificantBits);
	}
}
