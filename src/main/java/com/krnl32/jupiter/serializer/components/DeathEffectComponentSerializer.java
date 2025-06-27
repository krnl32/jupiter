package com.krnl32.jupiter.serializer.components;

import com.krnl32.jupiter.components.DeathEffectComponent;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JupiterSerializerUtils;
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
}
