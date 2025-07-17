package com.krnl32.jupiter.serializer.components.effects;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.model.Sprite;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JupiterSerializerUtils;
import org.joml.Vector4f;
import org.json.JSONObject;

public class DeathEffectComponentSerializer implements ComponentSerializer<DeathEffectComponent> {
	@Override
	public JSONObject serialize(DeathEffectComponent component) {
		return new JSONObject()
			.put("particleCount", component.particleCount)
			.put("particleSprite", JupiterSerializerUtils.serializeSprite(component.particleSprite));
	}

	@Override
	public DeathEffectComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new DeathEffectComponent(
			data.getInt("particleCount"),
			JupiterSerializerUtils.deserializeSprite(data.getJSONObject("particleSprite"))
		);
	}

	@Override
	public DeathEffectComponent clone(DeathEffectComponent component) {
		return new DeathEffectComponent(
			component.particleCount,
			new Sprite(
				component.particleSprite.getIndex(),
				new Vector4f(component.particleSprite.getColor()),
				component.particleSprite.getTextureAssetID() != null ? new AssetID(component.particleSprite.getTextureAssetID().getId()) : null,
				component.particleSprite.getTextureUV().clone()
			)
		);
	}
}
