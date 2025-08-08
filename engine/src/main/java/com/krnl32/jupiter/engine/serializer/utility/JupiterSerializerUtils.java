package com.krnl32.jupiter.engine.serializer.utility;

import com.krnl32.jupiter.engine.model.Sprite;
import com.krnl32.jupiter.engine.script.ScriptInstance;
import org.json.JSONObject;

public class JupiterSerializerUtils {
	public static JSONObject serializeSprite(Sprite sprite) {
		//TextureAsset textureAsset = sprite.getTextureAssetId() != null ? ProjectContext.getInstance().getAssetManager().getAsset(sprite.getTextureAssetId()) : null;

		//return new JSONObject()
		//	.put("index", sprite.getIndex())
		//	.put("color", JOMLSerializerUtils.serializeVector4f(sprite.getColor()))
			//.put("textureAssetId", (textureAsset != null ? textureAsset.getTexturePath() : JSONObject.NULL));

		return null;
	}

	public static Sprite deserializeSprite(JSONObject data) {
//		AssetId textureAssetId = null;
//		if (!data.isNull("textureAssetId")) {
//			textureAssetId = ProjectContext.getInstance().getAssetManager().registerAndLoad(data.getString("textureAssetId"), () -> new TextureAsset(data.getString("textureAssetId")));
//			if (textureAssetId == null) {
//				Logger.error("deserializeSprite Failed, Invalid Texture textureAssetId Path({})", data.getString("textureAssetId"));
//				return null;
//			}
//		}
//
//		return new Sprite(
//			data.getInt("index"),
//			JOMLSerializerUtils.deserializeVector4f(data.getJSONObject("color")),
//			textureAssetId
//		);
		return null;
	}

	public static JSONObject serializeScriptInstance(ScriptInstance scriptInstance) {
//		ScriptAsset scriptAsset = ProjectContext.getInstance().getAssetManager().getAsset(scriptInstance.getScriptAssetId());
//		if (scriptAsset == null) {
//			Logger.error("serializeScriptInstance Failed, Invalid Script AssetId({})", scriptInstance.getScriptAssetId());
//			return null;
//		}
//
//		return new JSONObject()
//			.put("scriptAssetId", scriptAsset.getRelativePath())
//			.put("disabled", scriptInstance.isDisabled());
		return null;
	}

	public static ScriptInstance deserializeScriptInstance(JSONObject data) {
//		AssetId scriptAssetId = ProjectContext.getInstance().getAssetManager().registerAndLoad(data.getString("scriptAssetId"), () -> new ScriptAsset(data.getString("scriptAssetId")));
//		if (scriptAssetId == null) {
//			Logger.error("deserializeScriptInstance Failed, Invalid Script AssetPath({})", data.getString("scriptAssetId"));
//			return null;
//		}
//
//		return new ScriptInstance(scriptAssetId, data.getBoolean("disabled"));
		return null;
	}
}
