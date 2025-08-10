package com.krnl32.jupiter.engine.serializer.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.utility.ComponentDTOSerializerUtility;

import java.util.Map;

public class TransformComponentDTOSerializer implements ComponentSerializer<TransformComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(TransformComponent component) {
		return Map.of(
			"translation", ComponentDTOSerializerUtility.serializeVector3f(component.translation),
			"rotation", ComponentDTOSerializerUtility.serializeVector3f(component.rotation),
			"scale", ComponentDTOSerializerUtility.serializeVector3f(component.scale)
		);
	}

	@Override
	public TransformComponent deserialize(Map<String, Object> data) {
		return new TransformComponent(
			ComponentDTOSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("translation")),
			ComponentDTOSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("rotation")),
			ComponentDTOSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("scale"))
		);
	}
}
