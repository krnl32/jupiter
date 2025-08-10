package com.krnl32.jupiter.engine.serializer.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.MovementIntentComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.DTOComponentSerializerUtility;

import java.util.Map;

public class DTOMovementIntentComponentSerializer implements ComponentSerializer<MovementIntentComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(MovementIntentComponent component) {
		return Map.of(
			"translation", DTOComponentSerializerUtility.serializeVector3f(component.translation),
			"rotation", DTOComponentSerializerUtility.serializeVector3f(component.rotation),
			"jump", component.jump,
			"sprint", component.sprint
		);
	}

	@Override
	public MovementIntentComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new MovementIntentComponent(
			DTOComponentSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("translation")),
			DTOComponentSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("rotation")),
			(boolean) data.get("jump"),
			(boolean) data.get("sprint")
		);
	}
}
