package com.krnl32.jupiter.engine.sceneserializer.data.components.utility;

import com.krnl32.jupiter.engine.components.utility.LifetimeComponent;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataSerializerUtility;

import java.util.Map;

public class DataLifetimeComponentSerializer implements ComponentSerializer<LifetimeComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(LifetimeComponent component) {
		return Map.of("remainingTime", component.remainingTime);
	}

	@Override
	public LifetimeComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new LifetimeComponent(DataSerializerUtility.toFloat(data.get("remainingTime")));
	}
}
