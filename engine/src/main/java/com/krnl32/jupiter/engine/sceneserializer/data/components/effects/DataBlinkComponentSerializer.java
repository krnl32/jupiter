package com.krnl32.jupiter.engine.sceneserializer.data.components.effects;

import com.krnl32.jupiter.engine.components.effects.BlinkComponent;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataSerializerUtility;

import java.util.Map;

public class DataBlinkComponentSerializer implements ComponentSerializer<BlinkComponent, Map<String, Object>> {
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
			DataSerializerUtility.toFloat(data.get("duration")),
			DataSerializerUtility.toFloat(data.get("interval")),
			DataSerializerUtility.toFloat(data.get("elapsedTime")),
			DataSerializerUtility.toFloat(data.get("blinkTime")),
			(boolean) data.get("visible")
		);
	}
}
