package com.krnl32.jupiter.engine.sceneserializer.data.components.physics;

import com.krnl32.jupiter.engine.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataSerializerUtility;

import java.util.Map;

public class DataBoxCollider2DComponentSerializer implements ComponentSerializer<BoxCollider2DComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(BoxCollider2DComponent component) {
		return Map.of(
			"size", DataSerializerUtility.serializeVector2f(component.size),
			"offset", DataSerializerUtility.serializeVector2f(component.offset),
			"friction", component.friction,
			"density", component.density,
			"sensor", component.sensor
		);
	}

	@Override
	public BoxCollider2DComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new BoxCollider2DComponent(
			DataSerializerUtility.deserializeVector2f((Map<String, Object>) data.get("size")),
			DataSerializerUtility.deserializeVector2f((Map<String, Object>) data.get("offset")),
			DataSerializerUtility.toFloat(data.get("friction")),
			DataSerializerUtility.toFloat(data.get("density")),
			(boolean) data.get("sensor")
		);
	}
}
