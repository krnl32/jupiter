package com.github.krnl32.jupiter.serializer.components;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.TextureAsset;
import com.github.krnl32.jupiter.components.SpriteRendererComponent;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.serializer.ComponentSerializer;
import com.github.krnl32.jupiter.serializer.JSONSerializerUtils;
import org.json.JSONObject;

public class SpriteRendererComponentSerializer implements ComponentSerializer<SpriteRendererComponent> {
	@Override
	public JSONObject serialize(SpriteRendererComponent component) {
		TextureAsset textureAsset = AssetManager.getInstance().getAsset(component.textureAssetID);
		if (textureAsset == null) {
			Logger.error("SpriteRendererComponentSerializer Serialize Failed, Invalid Texture AssetID({})", component.textureAssetID);
			return null;
		}

		return new JSONObject()
			.put("index", component.index)
			.put("color", JSONSerializerUtils.serializeVector4f(component.color))
			.put("textureAssetID", textureAsset.getTextureFileName());
	}

	@Override
	public SpriteRendererComponent deserialize(JSONObject data) {
		AssetID textureAssetID = AssetManager.getInstance().registerAndLoad(data.getString("textureAssetID"), () -> new TextureAsset(data.getString("textureAssetID")));
		if (textureAssetID == null) {
			Logger.error("SpriteRendererComponentSerializer Deserialize Failed, Invalid Texture textureAssetID Path({})", data.getString("textureAssetID"));
			return null;
		}

		return new SpriteRendererComponent(
			data.getInt("index"),
			JSONSerializerUtils.deserializeVector4f(data.getJSONObject("color")),
			textureAssetID
		);
	}
}
