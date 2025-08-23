package com.krnl32.jupiter.engine.sceneserializer.jnative.components.physics;

import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.physics.BodyType;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import org.joml.Vector2f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * == RigidBody2DComponent (size: 27 Bytes) ==
 * 1 Byte	BodyType			uint8
 * 8 Bytes	Velocity			float32 x 2
 * 4 Bytes	LinearDamping		float32
 * 4 Bytes	AngularDamping		float32
 * 4 Bytes	Mass				float32
 * 4 Bytes	GravityScale		float32
 * 1 Byte	FixedRotation		uint8
 * 1 Byte	ContinuousCollision	uint8
 */
public class JRigidBody2DComponentSerializer implements ComponentSerializer<RigidBody2DComponent, byte[]> {
	private static final int SIZE = 27;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(RigidBody2DComponent component) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeByte(buffer, component.bodyType.getId());
		JSerializerUtility.serializeVector2f(buffer, component.velocity);
		JSerializerUtility.serializeFloat(buffer, component.linearDamping);
		JSerializerUtility.serializeFloat(buffer, component.angularDamping);
		JSerializerUtility.serializeFloat(buffer, component.mass);
		JSerializerUtility.serializeFloat(buffer, component.gravityScale);
		JSerializerUtility.serializeBool(buffer, component.fixedRotation);
		JSerializerUtility.serializeBool(buffer, component.continuousCollision);

		return buffer.array();
	}

	@Override
	public RigidBody2DComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JRigidBody2DComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		byte bodyTypeId = JSerializerUtility.deserializeByte(buffer);
		BodyType bodyType = BodyType.fromId(bodyTypeId);
		Vector2f velocity = JSerializerUtility.deserializeVector2f(buffer);
		float linearDamping = JSerializerUtility.deserializeFloat(buffer);
		float angularDamping = JSerializerUtility.deserializeFloat(buffer);
		float mass = JSerializerUtility.deserializeFloat(buffer);
		float gravityScale = JSerializerUtility.deserializeFloat(buffer);
		boolean fixedRotation = JSerializerUtility.deserializeBool(buffer);
		boolean continuousCollision = JSerializerUtility.deserializeBool(buffer);

		return new RigidBody2DComponent(
			bodyType,
			velocity,
			linearDamping,
			angularDamping,
			mass,
			gravityScale,
			fixedRotation,
			continuousCollision
		);
	}
}
