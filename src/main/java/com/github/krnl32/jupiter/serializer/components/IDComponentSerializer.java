package com.github.krnl32.jupiter.serializer.components;

import com.github.krnl32.jupiter.components.IDComponent;
import com.github.krnl32.jupiter.serializer.ComponentSerializer;
import com.github.krnl32.jupiter.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

import java.util.UUID;

public class IDComponentSerializer implements ComponentSerializer<IDComponent> {
	@Override
	public JSONObject serialize(IDComponent component) {
		return new JSONObject().put("id", component.id.toString());
	}

	@Override
	public IDComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new IDComponent(UUID.fromString(data.getString("id")));
	}
}
