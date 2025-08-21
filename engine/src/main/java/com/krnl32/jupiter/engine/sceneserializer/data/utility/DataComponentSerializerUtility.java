package com.krnl32.jupiter.engine.sceneserializer.data.utility;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.model.Sprite;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.script.ScriptInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataComponentSerializerUtility {
	public static Map<String, Object> serializeSprite(Sprite sprite) {
		Map<String, Object> map = new HashMap<>();
		map.put("index", sprite.getIndex());
		map.put("color", DataSerializerUtility.serializeVector4f(sprite.getColor()));
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
			DataSerializerUtility.deserializeVector4f((Map<String, Object>) data.get("color")),
			textureAssetId,
			DataSerializerUtility.toFloatArray(data.get("textureUV"))
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
