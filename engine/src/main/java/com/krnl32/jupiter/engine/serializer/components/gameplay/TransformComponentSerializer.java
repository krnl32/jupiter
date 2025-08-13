package com.krnl32.jupiter.engine.serializer.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.ComponentSerializerUtility;

import java.util.Map;

public class TransformComponentSerializer implements ComponentSerializer<TransformComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(TransformComponent component) {
		return Map.of(
			"translation", ComponentSerializerUtility.serializeVector3f(component.translation),
			"rotation", ComponentSerializerUtility.serializeVector3f(component.rotation),
			"scale", ComponentSerializerUtility.serializeVector3f(component.scale)
		);
	}

	@Override
	public TransformComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new TransformComponent(
			ComponentSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("translation")),
			ComponentSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("rotation")),
			ComponentSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("scale"))
		);
	}
}
