package com.krnl32.jupiter.serializer.utility;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.types.TextureAsset;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.model.Sprite;
import org.json.JSONObject;

public class JupiterSerializerUtils {
	public static JSONObject serializeSprite(Sprite sprite) {
		TextureAsset textureAsset = sprite.getTextureAssetID() != null ? AssetManager.getInstance().getAsset(sprite.getTextureAssetID()) : null;

		return new JSONObject()
			.put("index", sprite.getIndex())
			.put("color", JOMLSerializerUtils.serializeVector4f(sprite.getColor()))
			.put("textureAssetID", (textureAsset != null ? textureAsset.getTexturePath() : JSONObject.NULL));
	}

	public static Sprite deserializeSprite(JSONObject data) {
		AssetID textureAssetID = null;
		if (!data.isNull("textureAssetID")) {
			textureAssetID = AssetManager.getInstance().registerAndLoad(data.getString("textureAssetID"), () -> new TextureAsset(data.getString("textureAssetID")));
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
}
