package com.krnl32.jupiter.engine.sceneserializer.data.components.physics;

import com.krnl32.jupiter.engine.components.physics.CircleCollider2DComponent;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataSerializerUtility;

import java.util.Map;

public class DataCircleCollider2DComponentSerializer implements ComponentSerializer<CircleCollider2DComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(CircleCollider2DComponent component) {
		return Map.of(
			"radius", component.radius,
			"offset", DataSerializerUtility.serializeVector2f(component.offset),
			"friction", component.friction,
			"density", component.density,
			"sensor", component.sensor
		);
	}

	@Override
	public CircleCollider2DComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new CircleCollider2DComponent(
			DataSerializerUtility.toFloat(data.get("radius")),
			DataSerializerUtility.deserializeVector2f((Map<String, Object>) data.get("offset")),
			DataSerializerUtility.toFloat(data.get("friction")),
			DataSerializerUtility.toFloat(data.get("density")),
			(boolean) data.get("sensor")
		);
	}
}
