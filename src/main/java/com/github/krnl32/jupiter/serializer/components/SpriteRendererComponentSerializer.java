package com.github.krnl32.jupiter.serializer.components;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.TextureAsset;
import com.github.krnl32.jupiter.components.SpriteRendererComponent;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.serializer.ComponentSerializer;
import com.github.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.github.krnl32.jupiter.serializer.utility.JOMLSerializerUtils;
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
			.put("color", JOMLSerializerUtils.serializeVector4f(component.color))
			.put("textureAssetID", textureAsset.getTextureAssetPath())
			.put("textureUV", component.textureUV);
	}

	@Override
	public SpriteRendererComponent deserialize(JSONObject data, EntityResolver resolver) {
		AssetID textureAssetID = AssetManager.getInstance().registerAndLoad(data.getString("textureAssetID"), () -> new TextureAsset(data.getString("textureAssetID")));
		if (textureAssetID == null) {
			Logger.error("SpriteRendererComponentSerializer Deserialize Failed, Invalid Texture textureAssetID Path({})", data.getString("textureAssetID"));
			return null;
		}

		return new SpriteRendererComponent(
			data.getInt("index"),
			JOMLSerializerUtils.deserializeVector4f(data.getJSONObject("color")),
			textureAssetID,
			new float[] {
				data.getJSONArray("textureUV").getFloat(0),
				data.getJSONArray("textureUV").getFloat(1),
				data.getJSONArray("textureUV").getFloat(2),
				data.getJSONArray("textureUV").getFloat(3),
				data.getJSONArray("textureUV").getFloat(4),
				data.getJSONArray("textureUV").getFloat(5),
				data.getJSONArray("textureUV").getFloat(6),
				data.getJSONArray("textureUV").getFloat(7),
			}
		);
	}
}
