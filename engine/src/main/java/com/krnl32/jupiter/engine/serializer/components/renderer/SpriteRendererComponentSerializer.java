package com.krnl32.jupiter.engine.serializer.components.renderer;

import com.krnl32.jupiter.engine.asset.AssetID;
import com.krnl32.jupiter.engine.asset.AssetManager;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.JOMLSerializerUtils;
import org.joml.Vector4f;
import org.json.JSONObject;

public class SpriteRendererComponentSerializer implements ComponentSerializer<SpriteRendererComponent> {
	@Override
	public JSONObject serialize(SpriteRendererComponent component) {
		TextureAsset textureAsset = component.textureAssetID != null ? AssetManager.getInstance().getAsset(component.textureAssetID) : null;

		return new JSONObject()
			.put("index", component.index)
			.put("color", JOMLSerializerUtils.serializeVector4f(component.color))
			.put("textureAssetID", (textureAsset != null ? textureAsset.getTexturePath() : JSONObject.NULL))
			.put("textureUV", component.textureUV);
	}

	@Override
	public SpriteRendererComponent deserialize(JSONObject data, EntityResolver resolver) {
		AssetID textureAssetID = null;
		if (!data.isNull("textureAssetID")) {
			textureAssetID = AssetManager.getInstance().registerAndLoad(data.getString("textureAssetID"), () -> new TextureAsset(data.getString("textureAssetID")));
			if (textureAssetID == null) {
				Logger.error("SpriteRendererComponentSerializer Failed, Invalid Texture textureAssetID Path({})", data.getString("textureAssetID"));
				return null;
			}
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

	@Override
	public SpriteRendererComponent clone(SpriteRendererComponent component) {
		return new SpriteRendererComponent(
			component.index,
			new Vector4f(component.color),
			component.textureAssetID != null ? new AssetID(component.textureAssetID.getId()) : null,
			component.textureUV.clone()
		);
	}
}
