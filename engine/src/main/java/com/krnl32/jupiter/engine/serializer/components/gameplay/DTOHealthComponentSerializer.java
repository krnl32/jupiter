package com.krnl32.jupiter.engine.serializer.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.HealthComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.DTOComponentSerializerUtility;

import java.util.Map;

public class DTOHealthComponentSerializer implements ComponentSerializer<HealthComponent, Map<String, Object>> {
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
			DTOComponentSerializerUtility.convertToFloat(data.get("maxHealth")),
			DTOComponentSerializerUtility.convertToFloat(data.get("currentHealth"))
		);
	}
}
