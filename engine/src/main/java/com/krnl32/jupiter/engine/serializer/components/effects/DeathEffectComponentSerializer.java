package com.krnl32.jupiter.engine.serializer.components.effects;

import com.krnl32.jupiter.engine.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;

import java.util.Map;

public class DeathEffectComponentSerializer implements ComponentSerializer<DeathEffectComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(DeathEffectComponent component) {
//		return new JSONObject()
//			.put("particleCount", component.particleCount)
//			.put("particleSprite", JupiterSerializerUtils.serializeSprite(component.particleSprite));
		return null;
	}

	@Override
	public DeathEffectComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
//		return new DeathEffectComponent(
//			data.getInt("particleCount"),
//			JupiterSerializerUtils.deserializeSprite(data.getJSONObject("particleSprite"))
//		);
		return null;
	}
}
