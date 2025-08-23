package com.krnl32.jupiter.engine.sceneserializer.jnative.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.ForceMovementComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * == ForceMovementComponent (size: 20 Bytes) ==
 * 4 Bytes	MoveForce			float32
 * 4 Bytes	SprintMultiplier	float32
 * 4 Bytes	MaxSpeed			float32
 * 4 Bytes	RotationTorque		float32
 * 4 Bytes	JumpImpulse			float32
 */
public class JForceMovementComponentSerializer implements ComponentSerializer<ForceMovementComponent, byte[]> {
	private static final int SIZE = 20;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(ForceMovementComponent component) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeFloat(buffer, component.moveForce);
		JSerializerUtility.serializeFloat(buffer, component.sprintMultiplier);
		JSerializerUtility.serializeFloat(buffer, component.maxSpeed);
		JSerializerUtility.serializeFloat(buffer, component.rotationTorque);
		JSerializerUtility.serializeFloat(buffer, component.jumpImpulse);

		return buffer.array();
	}

	@Override
	public ForceMovementComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JForceMovementComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		float moveForce = JSerializerUtility.deserializeFloat(buffer);
		float sprintMultiplier = JSerializerUtility.deserializeFloat(buffer);
		float maxSpeed = JSerializerUtility.deserializeFloat(buffer);
		float rotationTorque = JSerializerUtility.deserializeFloat(buffer);
		float jumpImpulse = JSerializerUtility.deserializeFloat(buffer);

		return new ForceMovementComponent(moveForce, sprintMultiplier, maxSpeed, rotationTorque, jumpImpulse);
	}
}
