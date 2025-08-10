package com.krnl32.jupiter.engine.serializer.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.ForceMovementComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.DTOComponentSerializerUtility;

import java.util.Map;

public class DTOForceMovementComponentSerializer implements ComponentSerializer<ForceMovementComponent, Map<String, Object>> {
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
			DTOComponentSerializerUtility.toFloat(data.get("moveForce")),
			DTOComponentSerializerUtility.toFloat(data.get("sprintMultiplier")),
			DTOComponentSerializerUtility.toFloat(data.get("maxSpeed")),
			DTOComponentSerializerUtility.toFloat(data.get("rotationTorque")),
			DTOComponentSerializerUtility.toFloat(data.get("jumpImpulse"))
		);
	}
}
