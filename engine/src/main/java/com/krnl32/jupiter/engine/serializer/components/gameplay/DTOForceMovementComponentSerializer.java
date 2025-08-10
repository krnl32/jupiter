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
			DTOComponentSerializerUtility.convertToFloat(data.get("moveForce")),
			DTOComponentSerializerUtility.convertToFloat(data.get("sprintMultiplier")),
			DTOComponentSerializerUtility.convertToFloat(data.get("maxSpeed")),
			DTOComponentSerializerUtility.convertToFloat(data.get("rotationTorque")),
			DTOComponentSerializerUtility.convertToFloat(data.get("jumpImpulse"))
		);
	}
}
