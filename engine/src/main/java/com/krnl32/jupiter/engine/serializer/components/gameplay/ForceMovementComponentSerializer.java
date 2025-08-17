package com.krnl32.jupiter.engine.serializer.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.ForceMovementComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.SerializerUtility;

import java.util.Map;

public class ForceMovementComponentSerializer implements ComponentSerializer<ForceMovementComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(ForceMovementComponent component) {
		return Map.of(
			"moveForce", component.moveForce,
			"sprintMultiplier", component.sprintMultiplier,
			"maxSpeed", component.maxSpeed,
			"rotationTorque", component.rotationTorque,
			"jumpImpulse", component.jumpImpulse
		);
	}

	@Override
	public ForceMovementComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new ForceMovementComponent(
			SerializerUtility.toFloat(data.get("moveForce")),
			SerializerUtility.toFloat(data.get("sprintMultiplier")),
			SerializerUtility.toFloat(data.get("maxSpeed")),
			SerializerUtility.toFloat(data.get("rotationTorque")),
			SerializerUtility.toFloat(data.get("jumpImpulse"))
		);
	}
}
