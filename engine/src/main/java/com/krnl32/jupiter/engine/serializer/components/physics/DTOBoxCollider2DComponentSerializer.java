package com.krnl32.jupiter.engine.serializer.components.physics;

import com.krnl32.jupiter.engine.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.DTOComponentSerializerUtility;

import java.util.Map;

public class DTOBoxCollider2DComponentSerializer implements ComponentSerializer<BoxCollider2DComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(BoxCollider2DComponent component) {
		return Map.of(
			"size", DTOComponentSerializerUtility.serializeVector2f(component.size),
			"offset", DTOComponentSerializerUtility.serializeVector2f(component.offset),
			"friction", component.friction,
			"density", component.density,
			"sensor", component.sensor
		);
	}

	@Override
	public BoxCollider2DComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new BoxCollider2DComponent(
			DTOComponentSerializerUtility.deserializeVector2f((Map<String, Object>) data.get("size")),
			DTOComponentSerializerUtility.deserializeVector2f((Map<String, Object>) data.get("offset")),
			DTOComponentSerializerUtility.convertToFloat(data.get("friction")),
			DTOComponentSerializerUtility.convertToFloat(data.get("density")),
			(boolean) data.get("sensor")
		);
	}
}
