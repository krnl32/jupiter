package com.krnl32.jupiter.engine.sceneserializer.data.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.HealthComponent;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataSerializerUtility;

import java.util.Map;

public class DataHealthComponentSerializer implements ComponentSerializer<HealthComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(HealthComponent component) {
		return Map.of(
			"maxHealth", component.maxHealth,
			"currentHealth", component.currentHealth
		);
	}

	@Override
	public HealthComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new HealthComponent(
			DataSerializerUtility.toFloat(data.get("maxHealth")),
			DataSerializerUtility.toFloat(data.get("currentHealth"))
		);
	}
}
