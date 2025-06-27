package com.krnl32.jupiter.serializer.components;

import com.krnl32.jupiter.components.HealthComponent;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

public class HealthComponentSerializer implements ComponentSerializer<HealthComponent> {
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
}
