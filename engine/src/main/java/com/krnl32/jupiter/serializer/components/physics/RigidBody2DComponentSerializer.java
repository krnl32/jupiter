package com.krnl32.jupiter.serializer.components.physics;

import com.krnl32.jupiter.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.physics.BodyType;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JOMLSerializerUtils;
import org.joml.Vector2f;
import org.json.JSONObject;

public class RigidBody2DComponentSerializer implements ComponentSerializer<RigidBody2DComponent> {
	@Override
	public JSONObject serialize(RigidBody2DComponent component) {
		return new JSONObject()
			.put("bodyType", component.bodyType)
			.put("velocity", JOMLSerializerUtils.serializeVector2f(component.velocity))
			.put("linearDamping", component.linearDamping)
			.put("angularDamping", component.angularDamping)
			.put("mass", component.mass)
			.put("gravityScale", component.gravityScale)
			.put("fixedRotation", component.fixedRotation)
			.put("continuousCollision", component.continuousCollision);
	}

	@Override
	public RigidBody2DComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new RigidBody2DComponent(
			BodyType.valueOf(data.getString("bodyType")),
			JOMLSerializerUtils.deserializeVector2f(data.getJSONObject("velocity")),
			data.getFloat("linearDamping"),
			data.getFloat("angularDamping"),
			data.getFloat("mass"),
			data.getFloat("gravityScale"),
			data.getBoolean("fixedRotation"),
			data.getBoolean("continuousCollision")
		);
	}

	@Override
	public RigidBody2DComponent clone(RigidBody2DComponent component) {
		return new RigidBody2DComponent(
			component.bodyType,
			new Vector2f(component.velocity),
			component.linearDamping,
			component.angularDamping,
			component.mass,
			component.gravityScale,
			component.fixedRotation,
			component.continuousCollision
		);
	}
}
