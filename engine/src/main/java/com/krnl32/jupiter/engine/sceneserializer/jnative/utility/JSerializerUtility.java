package com.krnl32.jupiter.engine.sceneserializer.jnative.utility;

import org.joml.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class JSerializerUtility {
	// Primitive

	// Byte
	public static byte[] serializeByte(byte value) {
		ByteBuffer buffer = ByteBuffer.allocate(1).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(value);
		return buffer.array();
	}

	public static byte deserializeByte(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		return buffer.get();
	}

	public static void serializeByte(ByteBuffer buffer, byte value) {
		buffer.put(value);
	}

	public static byte deserializeByte(ByteBuffer buffer) {
		return buffer.get();
	}

	// Bool
	public static byte[] serializeBool(boolean value) {
		ByteBuffer buffer = ByteBuffer.allocate(1).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put((byte) (value ? 1 : 0));
		return buffer.array();
	}

	public static boolean deserializeBool(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		return buffer.get() != 0;
	}

	public static void serializeBool(ByteBuffer buffer, boolean value) {
		buffer.put((byte) (value ? 1 : 0));
	}

	public static boolean deserializeBool(ByteBuffer buffer) {
		return buffer.get() != 0;
	}

	// Int
	public static byte[] serializeInt(int value) {
		ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(value);
		return buffer.array();
	}

	public static int deserializeInt(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		return buffer.getInt();
	}

	public static void serializeInt(ByteBuffer buffer, int value) {
		buffer.putInt(value);
	}

	public static int deserializeInt(ByteBuffer buffer) {
		return buffer.getInt();
	}

	// Long
	public static byte[] serializeLong(long value) {
		ByteBuffer buffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(value);
		return buffer.array();
	}

	public static long deserializeLong(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		return buffer.getLong();
	}

	public static void serializeLong(ByteBuffer buffer, long value) {
		buffer.putLong(value);
	}

	public static long deserializeLong(ByteBuffer buffer) {
		return buffer.getLong();
	}

	// Float
	public static byte[] serializeFloat(float value) {
		ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putFloat(value);
		return buffer.array();
	}

	public static float deserializeFloat(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		return buffer.getFloat();
	}

	public static void serializeFloat(ByteBuffer buffer, float value) {
		buffer.putFloat(value);
	}

	public static float deserializeFloat(ByteBuffer buffer) {
		return buffer.getFloat();
	}

	// Byte Array
	public static void serializeByteArray(ByteBuffer buffer, byte[] values) {
		buffer.put(values);
	}

	public static byte[] deserializeByteArray(ByteBuffer buffer, int length) {
		byte[] values = new byte[length];
		buffer.get(values);
		return values;
	}

	// Int Array
	public static byte[] serializeIntArray(int[] values) {
		ByteBuffer buffer = ByteBuffer.allocate(4 * values.length).order(ByteOrder.LITTLE_ENDIAN);
		for (int value : values) {
			buffer.putInt(value);
		}
		return buffer.array();
	}

	public static int[] deserializeIntArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		int[] values = new int[data.length / 4];
		for (int i = 0; i < values.length; i++) {
			values[i] = buffer.getInt();
		}
		return values;
	}

	public static void serializeIntArray(ByteBuffer buffer, int[] values) {
		for (int value : values) {
			buffer.putInt(value);
		}
	}

	public static int[] deserializeIntArray(ByteBuffer buffer, int length) {
		int[] values = new int[length];
		for (int i = 0; i < length; i++) {
			values[i] = buffer.getInt();
		}
		return values;
	}

	// Float Array
	public static byte[] serializeFloatArray(float[] values) {
		ByteBuffer buffer = ByteBuffer.allocate(4 * values.length).order(ByteOrder.LITTLE_ENDIAN);
		for (float value : values) {
			buffer.putFloat(value);
		}
		return buffer.array();
	}

	public static float[] deserializeFloatArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		float[] values = new float[data.length / 4];
		for (int i = 0; i < values.length; i++) {
			values[i] = buffer.getFloat();
		}
		return values;
	}

	public static void serializeFloatArray(ByteBuffer buffer, float[] values) {
		for (float value : values) {
			buffer.putFloat(value);
		}
	}

	public static float[] deserializeFloatArray(ByteBuffer buffer, int length) {
		float[] values = new float[length];
		for (int i = 0; i < length; i++) {
			values[i] = buffer.getFloat();
		}
		return values;
	}

	// JOML Math Types
	public static byte[] serializeVector2f(Vector2fc vec) {
		ByteBuffer buffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putFloat(vec.x());
		buffer.putFloat(vec.y());
		return buffer.array();
	}

	public static Vector2f deserializeVector2f(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		return new Vector2f(x, y);
	}

	public static void serializeVector2f(ByteBuffer buffer, Vector2fc vec) {
		buffer.putFloat(vec.x());
		buffer.putFloat(vec.y());
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

	public static Vector3f deserializeVector3f(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		float z = buffer.getFloat();
		return new Vector3f(x, y, z);
	}

	public static void serializeVector3f(ByteBuffer buffer, Vector3fc vec) {
		buffer.putFloat(vec.x());
		buffer.putFloat(vec.y());
		buffer.putFloat(vec.z());
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

	public static Vector4f deserializeVector4f(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		float z = buffer.getFloat();
		float w = buffer.getFloat();
		return new Vector4f(x, y, z, w);
	}

	public static void serializeVector4f(ByteBuffer buffer, Vector4fc vec) {
		buffer.putFloat(vec.x());
		buffer.putFloat(vec.y());
		buffer.putFloat(vec.z());
		buffer.putFloat(vec.w());
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

	public static UUID deserializeUUID(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		long mostSignificantBits = buffer.getLong();
		long leastSignificantBits = buffer.getLong();
		return new UUID(mostSignificantBits, leastSignificantBits);
	}

	public static void serializeUUID(ByteBuffer buffer, UUID uuid) {
		buffer.putLong(uuid.getMostSignificantBits());
		buffer.putLong(uuid.getLeastSignificantBits());
	}

	public static UUID deserializeUUID(ByteBuffer buffer) {
		long mostSignificantBits = buffer.getLong();
		long leastSignificantBits = buffer.getLong();
		return new UUID(mostSignificantBits, leastSignificantBits);
	}
}
