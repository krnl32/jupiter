package com.krnl32.jupiter.engine.sceneserializer.data.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataSerializerUtility;

import java.util.Map;

public class DataTransformComponentSerializer implements ComponentSerializer<TransformComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(TransformComponent component) {
		return Map.of(
			"translation", DataSerializerUtility.serializeVector3f(component.translation),
			"rotation", DataSerializerUtility.serializeVector3f(component.rotation),
			"scale", DataSerializerUtility.serializeVector3f(component.scale)
		);
	}

	@Override
	public TransformComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new TransformComponent(
			DataSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("translation")),
			DataSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("rotation")),
			DataSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("scale"))
		);
	}
}
