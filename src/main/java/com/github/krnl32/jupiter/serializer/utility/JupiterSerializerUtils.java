package com.github.krnl32.jupiter.serializer.utility;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.TextureAsset;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.model.Sprite;
import org.json.JSONObject;

public class JupiterSerializerUtils {
	public static JSONObject serializeSprite(Sprite sprite) {
		TextureAsset textureAsset = AssetManager.getInstance().getAsset(sprite.getTextureAssetID());
		if (textureAsset == null) {
			Logger.error("serializeSprite Failed, Invalid Texture AssetID({})", sprite.getTextureAssetID());
			return null;
		}

		return new JSONObject()
			.put("index", sprite.getIndex())
			.put("color", JOMLSerializerUtils.serializeVector4f(sprite.getColor()))
			.put("textureAssetID", textureAsset.getTextureAssetPath());
	}

	public static Sprite deserializeSprite(JSONObject data) {
		AssetID textureAssetID = AssetManager.getInstance().registerAndLoad(data.getString("textureAssetID"), () -> new TextureAsset(data.getString("textureAssetID")));
		if (textureAssetID == null) {
			Logger.error("deserializeSprite Failed, Invalid Texture textureAssetID Path({})", data.getString("textureAssetID"));
			return null;
		}

		return new Sprite(
			data.getInt("index"),
			JOMLSerializerUtils.deserializeVector4f(data.getJSONObject("color")),
			textureAssetID
		);
	}
}
