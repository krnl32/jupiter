package com.krnl32.jupiter.engine.serializer.components.physics;

import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.physics.BodyType;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.SerializerUtility;

import java.util.Map;

public class RigidBody2DComponentSerializer implements ComponentSerializer<RigidBody2DComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(RigidBody2DComponent component) {
		return Map.of(
			"bodyType", component.bodyType.name(),
			"velocity", SerializerUtility.serializeVector2f(component.velocity),
			"linearDamping", component.linearDamping,
			"angularDamping", component.angularDamping,
			"mass", component.mass,
			"gravityScale", component.gravityScale,
			"fixedRotation", component.fixedRotation,
			"continuousCollision", component.continuousCollision
		);
	}

	@Override
	public RigidBody2DComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new RigidBody2DComponent(
			BodyType.valueOf(data.get("bodyType").toString()),
			SerializerUtility.deserializeVector2f((Map<String, Object>) data.get("velocity")),
			SerializerUtility.toFloat(data.get("linearDamping")),
			SerializerUtility.toFloat(data.get("angularDamping")),
			SerializerUtility.toFloat(data.get("mass")),
			SerializerUtility.toFloat(data.get("gravityScale")),
			(boolean) data.get("fixedRotation"),
			(boolean) data.get("continuousCollision")
		);
	}
}
