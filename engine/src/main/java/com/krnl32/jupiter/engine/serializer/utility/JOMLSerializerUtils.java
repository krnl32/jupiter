package com.krnl32.jupiter.engine.serializer.utility;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.json.JSONObject;

public class JOMLSerializerUtils {
	public static JSONObject serializeVector2f(Vector2f vec) {
		return new JSONObject().put("x", vec.x).put("y", vec.y);
	}

	public static Vector2f deserializeVector2f(JSONObject obj) {
		return new Vector2f(obj.getFloat("x"), obj.getFloat("y"));
	}

	public static JSONObject serializeVector3f(Vector3f vec) {
		return new JSONObject().put("x", vec.x).put("y", vec.y).put("z", vec.z);
	}

	public static Vector3f deserializeVector3f(JSONObject obj) {
		return new Vector3f(obj.getFloat("x"), obj.getFloat("y"), obj.getFloat("z"));
	}

	public static JSONObject serializeVector4f(Vector4f vec) {
		return new JSONObject().put("x", vec.x).put("y", vec.y).put("z", vec.z).put("w", vec.w);
	}

	public static Vector4f deserializeVector4f(JSONObject obj) {
		return new Vector4f(obj.getFloat("x"), obj.getFloat("y"), obj.getFloat("z"), obj.getFloat("w"));
	}
}
