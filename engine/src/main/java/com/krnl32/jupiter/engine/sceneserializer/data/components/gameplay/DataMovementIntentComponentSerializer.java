package com.krnl32.jupiter.engine.sceneserializer.data.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.MovementIntentComponent;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataSerializerUtility;

import java.util.Map;

public class DataMovementIntentComponentSerializer implements ComponentSerializer<MovementIntentComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(MovementIntentComponent component) {
		return Map.of(
			"translation", DataSerializerUtility.serializeVector3f(component.translation),
			"rotation", DataSerializerUtility.serializeVector3f(component.rotation),
			"jump", component.jump,
			"sprint", component.sprint
		);
	}

	@Override
	public MovementIntentComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new MovementIntentComponent(
			DataSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("translation")),
			DataSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("rotation")),
			(boolean) data.get("jump"),
			(boolean) data.get("sprint")
		);
	}
}
