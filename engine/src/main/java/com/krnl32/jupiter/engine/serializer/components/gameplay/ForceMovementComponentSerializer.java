package com.krnl32.jupiter.engine.serializer.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.ForceMovementComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

public class ForceMovementComponentSerializer implements ComponentSerializer<ForceMovementComponent> {
	@Override
	public JSONObject serialize(ForceMovementComponent component) {
		return new JSONObject()
			.put("moveForce", component.moveForce)
			.put("sprintMultiplier", component.sprintMultiplier)
			.put("maxSpeed", component.maxSpeed)
			.put("rotationTorque", component.rotationTorque)
			.put("jumpImpulse", component.jumpImpulse);
	}

	@Override
	public ForceMovementComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new ForceMovementComponent(
			data.getFloat("moveForce"),
			data.getFloat("sprintMultiplier"),
			data.getFloat("maxSpeed"),
			data.getFloat("rotationTorque"),
			data.getFloat("jumpImpulse")
		);
	}

	@Override
	public ForceMovementComponent clone(ForceMovementComponent component) {
		return new ForceMovementComponent(
			component.moveForce,
			component.sprintMultiplier,
			component.maxSpeed,
			component.rotationTorque,
			component.jumpImpulse
		);
	}
}
