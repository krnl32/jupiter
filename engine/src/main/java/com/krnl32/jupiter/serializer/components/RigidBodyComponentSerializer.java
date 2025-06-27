package com.krnl32.jupiter.serializer.components;

import com.krnl32.jupiter.components.RigidBodyComponent;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JOMLSerializerUtils;
import org.json.JSONObject;

public class RigidBodyComponentSerializer implements ComponentSerializer<RigidBodyComponent> {
	@Override
	public JSONObject serialize(RigidBodyComponent component) {
		return new JSONObject()
			.put("velocity", JOMLSerializerUtils.serializeVector3f(component.velocity))
			.put("angularVelocity", JOMLSerializerUtils.serializeVector3f(component.angularVelocity));
	}

	@Override
	public RigidBodyComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new RigidBodyComponent(
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("velocity")),
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("angularVelocity"))
		);
	}
}
