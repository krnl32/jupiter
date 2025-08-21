package com.krnl32.jupiter.engine.sceneserializer.data.components.effects;

import com.krnl32.jupiter.engine.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataComponentSerializerUtility;

import java.util.Map;

public class DataDeathEffectComponentSerializer implements ComponentSerializer<DeathEffectComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(DeathEffectComponent component) {
		return Map.of(
			"particleCount", component.particleCount,
			"particleSprite", DataComponentSerializerUtility.serializeSprite(component.particleSprite)
		);
	}

	@Override
	public DeathEffectComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new DeathEffectComponent(
			(int) data.get("particleCount"),
			DataComponentSerializerUtility.deserializeSprite((Map<String, Object>) data.get("particleSprite"))
		);
	}
}
