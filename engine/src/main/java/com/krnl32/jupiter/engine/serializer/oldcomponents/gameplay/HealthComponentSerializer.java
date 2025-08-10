package com.krnl32.jupiter.engine.serializer.oldcomponents.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.HealthComponent;
import com.krnl32.jupiter.engine.serializer.OldComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

public class HealthComponentSerializer implements OldComponentSerializer<HealthComponent> {
	@Override
	public JSONObject serialize(HealthComponent component) {
		return new JSONObject()
			.put("maxHealth", component.maxHealth)
			.put("currentHealth", component.currentHealth);
	}

	@Override
	public HealthComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new HealthComponent(
			data.getFloat("maxHealth"),
			data.getFloat("currentHealth")
		);
	}

	@Override
	public HealthComponent clone(HealthComponent component) {
		return new HealthComponent(
			component.maxHealth,
			component.currentHealth
		);
	}
}
