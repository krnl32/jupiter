package com.krnl32.jupiter.engine.serializer.utility;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;
import java.util.Map;

public class SerializerUtility {
	// JOML Math Types
	public static Map<String, Object> serializeVector2f(Vector2f vec) {
		return Map.of("x", vec.x, "y", vec.y);
	}

	public static Vector2f deserializeVector2f(Map<String, Object> vec) {
		return new Vector2f(toFloat(vec.get("x")), toFloat(vec.get("y")));
	}

	public static Map<String, Object> serializeVector3f(Vector3f vec) {
		return Map.of("x", vec.x, "y", vec.y, "z", vec.z);
	}

	public static Vector3f deserializeVector3f(Map<String, Object> vec) {
		return new Vector3f(toFloat(vec.get("x")), toFloat(vec.get("y")), toFloat(vec.get("z")));
	}

	public static Map<String, Object> serializeVector4f(Vector4f vec) {
		return Map.of("x", vec.x, "y", vec.y, "z", vec.z, "w", vec.w);
	}

	public static Vector4f deserializeVector4f(Map<String, Object> vec) {
		return new Vector4f(toFloat(vec.get("x")), toFloat(vec.get("y")), toFloat(vec.get("z")), toFloat(vec.get("w")));
	}

	// Primitive Types
	public static float toFloat(Object value) {
		return ((Number) value).floatValue();
	}

	public static int[] toIntArray(Object value) {
		List<?> list = (List<?>) value;
		int[] arr = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = ((Number) list.get(i)).intValue();
		}
		return arr;
	}

	public static float[] toFloatArray(Object value) {
		List<?> list = (List<?>) value;
		float[] arr = new float[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = ((Number) list.get(i)).floatValue();
		}
		return arr;
	}

	public static double[] toDoubleArray(Object value) {
		List<?> list = (List<?>) value;
		double[] arr = new double[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = ((Number) list.get(i)).doubleValue();
		}
		return arr;
	}
}
