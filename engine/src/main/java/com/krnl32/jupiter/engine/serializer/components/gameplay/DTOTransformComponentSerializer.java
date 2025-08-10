package com.krnl32.jupiter.engine.serializer.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.DTOComponentSerializerUtility;

import java.util.Map;

public class DTOTransformComponentSerializer implements ComponentSerializer<TransformComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(TransformComponent component) {
		return Map.of(
			"translation", DTOComponentSerializerUtility.serializeVector3f(component.translation),
			"rotation", DTOComponentSerializerUtility.serializeVector3f(component.rotation),
			"scale", DTOComponentSerializerUtility.serializeVector3f(component.scale)
		);
	}

	@Override
	public TransformComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new TransformComponent(
			DTOComponentSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("translation")),
			DTOComponentSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("rotation")),
			DTOComponentSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("scale"))
		);
	}
}
