package com.krnl32.jupiter.engine.serializer.components.physics;

import com.krnl32.jupiter.engine.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.SerializerUtility;

import java.util.Map;

public class BoxCollider2DComponentSerializer implements ComponentSerializer<BoxCollider2DComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(BoxCollider2DComponent component) {
		return Map.of(
			"size", SerializerUtility.serializeVector2f(component.size),
			"offset", SerializerUtility.serializeVector2f(component.offset),
			"friction", component.friction,
			"density", component.density,
			"sensor", component.sensor
		);
	}

	@Override
	public BoxCollider2DComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new BoxCollider2DComponent(
			SerializerUtility.deserializeVector2f((Map<String, Object>) data.get("size")),
			SerializerUtility.deserializeVector2f((Map<String, Object>) data.get("offset")),
			SerializerUtility.toFloat(data.get("friction")),
			SerializerUtility.toFloat(data.get("density")),
			(boolean) data.get("sensor")
		);
	}
}
