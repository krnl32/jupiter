package com.krnl32.jupiter.engine.serializer.components.utility;

import com.krnl32.jupiter.engine.components.utility.LifetimeComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

public class LifetimeComponentSerializer implements ComponentSerializer<LifetimeComponent> {
	@Override
	public JSONObject serialize(LifetimeComponent component) {
		return new JSONObject().put("remainingTime", component.remainingTime);
	}

	@Override
	public LifetimeComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new LifetimeComponent(data.getFloat("remainingTime"));
	}

	@Override
	public LifetimeComponent clone(LifetimeComponent component) {
		return new LifetimeComponent(component.remainingTime);
	}
}
