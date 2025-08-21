package com.krnl32.jupiter.engine.sceneserializer.data.components.utility;

import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

import java.util.Map;
import java.util.UUID;

public class DataUUIDComponentSerializer implements ComponentSerializer<UUIDComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(UUIDComponent component) {
		return Map.of("uuid", component.uuid.toString());
	}

	@Override
	public UUIDComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new UUIDComponent(UUID.fromString(data.get("uuid").toString()));
	}
}
