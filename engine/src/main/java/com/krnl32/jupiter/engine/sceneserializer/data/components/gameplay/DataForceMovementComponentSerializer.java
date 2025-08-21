package com.krnl32.jupiter.engine.sceneserializer.data.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.ForceMovementComponent;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataSerializerUtility;

import java.util.Map;

public class DataForceMovementComponentSerializer implements ComponentSerializer<ForceMovementComponent, Map<String, Object>> {
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
			DataSerializerUtility.toFloat(data.get("moveForce")),
			DataSerializerUtility.toFloat(data.get("sprintMultiplier")),
			DataSerializerUtility.toFloat(data.get("maxSpeed")),
			DataSerializerUtility.toFloat(data.get("rotationTorque")),
			DataSerializerUtility.toFloat(data.get("jumpImpulse"))
		);
	}
}
