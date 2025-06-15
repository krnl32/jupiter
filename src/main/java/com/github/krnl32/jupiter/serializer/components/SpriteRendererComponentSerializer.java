package com.github.krnl32.jupiter.serializer.components;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.components.SpriteRendererComponent;
import com.github.krnl32.jupiter.renderer.Sprite;
import com.github.krnl32.jupiter.serializer.ComponentSerializer;
import com.github.krnl32.jupiter.serializer.JSONSerializerUtils;
import org.json.JSONObject;

import java.util.UUID;

public class SpriteRendererComponentSerializer implements ComponentSerializer<SpriteRendererComponent> {
	@Override
	public JSONObject serialize(SpriteRendererComponent component) {
		return new JSONObject()
			.put("width", component.sprite.getWidth())
			.put("height", component.sprite.getHeight())
			.put("index", component.sprite.getIndex())
			.put("color", JSONSerializerUtils.serializeVector4f(component.sprite.getColor()))
			.put("textureAssetID", component.sprite.getTextureAssetID().getId().toString());
	}

	@Override
	public SpriteRendererComponent deserialize(JSONObject data) {
		return new SpriteRendererComponent(new Sprite(
			data.getInt("width"),
			data.getInt("height"),
			data.getInt("index"),
			JSONSerializerUtils.deserializeVector4f(data.getJSONObject("color")),
			new AssetID(UUID.fromString(data.getString("textureAssetID")))
		));
	}
}
