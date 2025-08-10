package com.krnl32.jupiter.engine.serializer.components.effects;

import com.krnl32.jupiter.engine.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.DTOComponentSerializerUtility;

import java.util.Map;

public class DTODeathEffectComponentSerializer implements ComponentSerializer<DeathEffectComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(DeathEffectComponent component) {
		return Map.of(
			"particleCount", component.particleCount,
			"particleSprite", DTOComponentSerializerUtility.serializeSprite(component.particleSprite)
		);
	}

	@Override
	public DeathEffectComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new DeathEffectComponent(
			(int) data.get("particleCount"),
			DTOComponentSerializerUtility.deserializeSprite((Map<String, Object>) data.get("particleSprite"))
		);
	}
}
