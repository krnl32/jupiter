package com.krnl32.jupiter.engine.serializer.components.effects;

import com.krnl32.jupiter.engine.components.effects.BlinkComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.DTOComponentSerializerUtility;

import java.util.Map;

public class DTOBlinkComponentSerializer implements ComponentSerializer<BlinkComponent, Map<String, Object>> {
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
			DTOComponentSerializerUtility.convertToFloat(data.get("duration")),
			DTOComponentSerializerUtility.convertToFloat(data.get("interval")),
			DTOComponentSerializerUtility.convertToFloat(data.get("elapsedTime")),
			DTOComponentSerializerUtility.convertToFloat(data.get("blinkTime")),
			(boolean) data.get("visible")
		);
	}
}
