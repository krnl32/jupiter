package com.krnl32.jupiter.serializer.components.effects;

import com.krnl32.jupiter.components.effects.BlinkComponent;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

public class BlinkComponentSerializer implements ComponentSerializer<BlinkComponent> {
	@Override
	public JSONObject serialize(BlinkComponent component) {
		return new JSONObject()
			.put("duration", component.duration)
			.put("interval", component.interval)
			.put("elapsedTime", component.elapsedTime)
			.put("blinkTime", component.blinkTime)
			.put("visible", component.visible);
	}

	@Override
	public BlinkComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new BlinkComponent(
			data.getFloat("duration"),
			data.getFloat("interval"),
			data.getFloat("elapsedTime"),
			data.getFloat("blinkTime"),
			data.getBoolean("visible")
		);
	}

	@Override
	public BlinkComponent clone(BlinkComponent component) {
		return new BlinkComponent(
			component.duration,
			component.interval,
			component.elapsedTime,
			component.blinkTime,
			component.visible
		);
	}
}
