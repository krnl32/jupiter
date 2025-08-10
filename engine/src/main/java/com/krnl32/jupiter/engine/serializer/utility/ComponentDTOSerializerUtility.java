package com.krnl32.jupiter.engine.serializer.utility;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Map;

public class ComponentDTOSerializerUtility {
	public static Map<String, Object> serializeVector2f(Vector2f vec) {
		return Map.of("x", vec.x, "y", vec.y);
	}

	public static Vector2f deserializeVector2f(Map<String, Object> vec) {
		return new Vector2f(convertToFloat(vec.get("x")), convertToFloat(vec.get("y")));
	}

	public static Map<String, Object> serializeVector3f(Vector3f vec) {
		return Map.of("x", vec.x, "y", vec.y, "z", vec.z);
	}

	public static Vector3f deserializeVector3f(Map<String, Object> vec) {
		return new Vector3f(convertToFloat(vec.get("x")), convertToFloat(vec.get("y")), convertToFloat(vec.get("z")));
	}

	public static Map<String, Object> serializeVector4f(Vector4f vec) {
		return Map.of("x", vec.x, "y", vec.y, "z", vec.z, "w", vec.w);
	}

	public static Vector4f deserializeVector4f(Map<String, Object> vec) {
		return new Vector4f(convertToFloat(vec.get("x")), convertToFloat(vec.get("y")), convertToFloat(vec.get("z")), convertToFloat(vec.get("w")));
	}

	public static float convertToFloat(Object value) {
		return ((Number) value).floatValue();
	}
}
