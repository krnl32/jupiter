package com.github.krnl32.jupiter.serializer.components;

import com.github.krnl32.jupiter.components.LifetimeComponent;
import com.github.krnl32.jupiter.serializer.ComponentSerializer;
import org.json.JSONObject;

public class LifetimeComponentSerializer implements ComponentSerializer<LifetimeComponent> {
	@Override
	public JSONObject serialize(LifetimeComponent component) {
		return new JSONObject().put("remainingTime", component.remainingTime);
	}

	@Override
	public LifetimeComponent deserialize(JSONObject data) {
		return new LifetimeComponent(data.getFloat("remainingTime"));
	}
}
