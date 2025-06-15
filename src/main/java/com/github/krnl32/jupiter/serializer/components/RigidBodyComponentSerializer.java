package com.github.krnl32.jupiter.serializer.components;

import com.github.krnl32.jupiter.components.RigidBodyComponent;
import com.github.krnl32.jupiter.serializer.ComponentSerializer;
import com.github.krnl32.jupiter.serializer.JSONSerializerUtils;
import org.json.JSONObject;

public class RigidBodyComponentSerializer implements ComponentSerializer<RigidBodyComponent> {
	@Override
	public JSONObject serialize(RigidBodyComponent component) {
		return new JSONObject().put("velocity", JSONSerializerUtils.serializeVector3f(component.velocity));
	}

	@Override
	public RigidBodyComponent deserialize(JSONObject data) {
		return new RigidBodyComponent(JSONSerializerUtils.deserializeVector3f(data.getJSONObject("velocity")));
	}
}
