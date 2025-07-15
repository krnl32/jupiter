package com.krnl32.jupiter.serializer.components;

import com.krnl32.jupiter.components.UUIDComponent;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

import java.util.UUID;

public class UUIDComponentSerializer implements ComponentSerializer<UUIDComponent> {
	@Override
	public JSONObject serialize(UUIDComponent component) {
		return new JSONObject().put("uuid", component.uuid.toString());
	}

	@Override
	public UUIDComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new UUIDComponent(UUID.fromString(data.getString("uuid")));
	}

	@Override
	public UUIDComponent clone(UUIDComponent component) {
		return new UUIDComponent(component.uuid);
	}
}
