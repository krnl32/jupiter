package com.krnl32.jupiter.engine.serializer.components.physics;

import com.krnl32.jupiter.engine.components.physics.CircleCollider2DComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.ComponentSerializerUtility;

import java.util.Map;

public class CircleCollider2DComponentSerializer implements ComponentSerializer<CircleCollider2DComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(CircleCollider2DComponent component) {
		return Map.of(
			"radius", component.radius,
			"offset", ComponentSerializerUtility.serializeVector2f(component.offset),
			"friction", component.friction,
			"density", component.density,
			"sensor", component.sensor
		);
	}

	@Override
	public CircleCollider2DComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new CircleCollider2DComponent(
			ComponentSerializerUtility.toFloat(data.get("radius")),
			ComponentSerializerUtility.deserializeVector2f((Map<String, Object>) data.get("offset")),
			ComponentSerializerUtility.toFloat(data.get("friction")),
			ComponentSerializerUtility.toFloat(data.get("density")),
			(boolean) data.get("sensor")
		);
	}
}
