package com.krnl32.jupiter.engine.serializer.utility;

import com.krnl32.jupiter.engine.asset.AssetID;
import com.krnl32.jupiter.engine.asset.types.ScriptAsset;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.model.Sprite;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.script.ScriptInstance;
import org.json.JSONObject;

public class JupiterSerializerUtils {
	public static JSONObject serializeSprite(Sprite sprite) {
		TextureAsset textureAsset = sprite.getTextureAssetID() != null ? ProjectContext.getAssetManager().getAsset(sprite.getTextureAssetID()) : null;

		return new JSONObject()
			.put("index", sprite.getIndex())
			.put("color", JOMLSerializerUtils.serializeVector4f(sprite.getColor()))
			.put("textureAssetID", (textureAsset != null ? textureAsset.getTexturePath() : JSONObject.NULL));
	}

	public static Sprite deserializeSprite(JSONObject data) {
		AssetID textureAssetID = null;
		if (!data.isNull("textureAssetID")) {
			textureAssetID = ProjectContext.getAssetManager().registerAndLoad(data.getString("textureAssetID"), () -> new TextureAsset(data.getString("textureAssetID")));
			if (textureAssetID == null) {
				Logger.error("deserializeSprite Failed, Invalid Texture textureAssetID Path({})", data.getString("textureAssetID"));
				return null;
			}
		}

		return new Sprite(
			data.getInt("index"),
			JOMLSerializerUtils.deserializeVector4f(data.getJSONObject("color")),
			textureAssetID
		);
	}

	public static JSONObject serializeScriptInstance(ScriptInstance scriptInstance) {
		ScriptAsset scriptAsset = ProjectContext.getAssetManager().getAsset(scriptInstance.getScriptAssetID());
		if (scriptAsset == null) {
			Logger.error("serializeScriptInstance Failed, Invalid Script AssetID({})", scriptInstance.getScriptAssetID());
			return null;
		}

		return new JSONObject()
			.put("scriptAssetID", scriptAsset.getRelativePath())
			.put("disabled", scriptInstance.isDisabled());
	}

	public static ScriptInstance deserializeScriptInstance(JSONObject data) {
		AssetID scriptAssetID = ProjectContext.getAssetManager().registerAndLoad(data.getString("scriptAssetID"), () -> new ScriptAsset(data.getString("scriptAssetID")));
		if (scriptAssetID == null) {
			Logger.error("deserializeScriptInstance Failed, Invalid Script AssetPath({})", data.getString("scriptAssetID"));
			return null;
		}

		return new ScriptInstance(scriptAssetID, data.getBoolean("disabled"));
	}
}
