package com.krnl32.jupiter.engine.sceneserializer.data.components.physics;

import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.physics.BodyType;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataSerializerUtility;

import java.util.Map;

public class DataRigidBody2DComponentSerializer implements ComponentSerializer<RigidBody2DComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(RigidBody2DComponent component) {
		return Map.of(
			"bodyType", component.bodyType.name(),
			"velocity", DataSerializerUtility.serializeVector2f(component.velocity),
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
			DataSerializerUtility.deserializeVector2f((Map<String, Object>) data.get("velocity")),
			DataSerializerUtility.toFloat(data.get("linearDamping")),
			DataSerializerUtility.toFloat(data.get("angularDamping")),
			DataSerializerUtility.toFloat(data.get("mass")),
			DataSerializerUtility.toFloat(data.get("gravityScale")),
			(boolean) data.get("fixedRotation"),
			(boolean) data.get("continuousCollision")
		);
	}
}
