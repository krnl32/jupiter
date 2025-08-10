package com.krnl32.jupiter.engine.serializer.utility;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.model.Sprite;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.script.ScriptInstance;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DTOComponentSerializerUtility {
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

	// Custom Types
	public static Map<String, Object> serializeSprite(Sprite sprite) {
		Map<String, Object> map = new HashMap<>();
		map.put("index", sprite.getIndex());
		map.put("color", DTOComponentSerializerUtility.serializeVector4f(sprite.getColor()));
		map.put("textureAssetId", (sprite.getTextureAssetId() != null ? sprite.getTextureAssetId().getId() : null));
		map.put("textureUV", sprite.getTextureUV());
		return map;
	}

	public static Sprite deserializeSprite(Map<String, Object> data) {
		AssetId textureAssetId = null;
		if (data.get("textureAssetId") != null) {
			textureAssetId = new AssetId(UUID.fromString(data.get("textureAssetId").toString()));
			if (!ProjectContext.getInstance().getAssetManager().isAssetRegistered(textureAssetId)) {
				Logger.error("DTOComponentSerializerUtility deserializeSprite Failed to Load Texture({}) Not Registered", textureAssetId);
				return null;
			}
		}

		return new Sprite(
			(int) data.get("index"),
			DTOComponentSerializerUtility.deserializeVector4f((Map<String, Object>) data.get("color")),
			textureAssetId,
			DTOComponentSerializerUtility.toFloatArray(data.get("textureUV"))
		);
	}

	public static Map<String, Object> serializeScriptInstance(ScriptInstance scriptInstance) {
		if (!ProjectContext.getInstance().getAssetManager().isAssetRegistered(scriptInstance.getScriptAssetId())) {
			Logger.error("DTOComponentSerializerUtility serializeScriptInstance Failed, Script AssetId({}) Not Registered", scriptInstance.getScriptAssetId());
			return null;
		}

		return Map.of(
			"scriptAssetId", scriptInstance.getScriptAssetId().getId(),
			"disabled", scriptInstance.isDisabled()
		);
	}

	public static ScriptInstance deserializeScriptInstance(Map<String, Object> data) {
		if (data.get("scriptAssetId") == null) {
			Logger.error("DTOComponentSerializerUtility deserializeScriptInstance Failed, Invalid Script AssetId({})", data.get("scriptAssetId"));
			return null;
		}

		AssetId scriptAssetId = new AssetId(UUID.fromString(data.get("scriptAssetId").toString()));
		if (!ProjectContext.getInstance().getAssetManager().isAssetRegistered(scriptAssetId)) {
			Logger.error("DTOComponentSerializerUtility serializeScriptInstance Failed, Script AssetId({}) Not Registered", scriptAssetId);
			return null;
		}

		return new ScriptInstance(scriptAssetId, (boolean) data.get("disabled"));
	}
}
