package com.krnl32.jupiter.engine.serializer.components.effects;

import com.krnl32.jupiter.engine.components.effects.BlinkComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.ComponentSerializerUtility;

import java.util.Map;

public class BlinkComponentSerializer implements ComponentSerializer<BlinkComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(BlinkComponent component) {
		return Map.of(
			"duration", component.duration,
			"interval", component.interval,
			"elapsedTime", component.elapsedTime,
			"blinkTime", component.blinkTime,
			"visible", component.visible
		);
	}

	@Override
	public BlinkComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new BlinkComponent(
			ComponentSerializerUtility.toFloat(data.get("duration")),
			ComponentSerializerUtility.toFloat(data.get("interval")),
			ComponentSerializerUtility.toFloat(data.get("elapsedTime")),
			ComponentSerializerUtility.toFloat(data.get("blinkTime")),
			(boolean) data.get("visible")
		);
	}
}
