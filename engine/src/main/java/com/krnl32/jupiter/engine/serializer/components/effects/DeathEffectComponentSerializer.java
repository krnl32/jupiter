package com.krnl32.jupiter.engine.serializer.components.effects;

import com.krnl32.jupiter.engine.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.ComponentSerializerUtility;

import java.util.Map;

public class DeathEffectComponentSerializer implements ComponentSerializer<DeathEffectComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(DeathEffectComponent component) {
		return Map.of(
			"particleCount", component.particleCount,
			"particleSprite", ComponentSerializerUtility.serializeSprite(component.particleSprite)
		);
	}

	@Override
	public DeathEffectComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new DeathEffectComponent(
			(int) data.get("particleCount"),
			ComponentSerializerUtility.deserializeSprite((Map<String, Object>) data.get("particleSprite"))
		);
	}
}
