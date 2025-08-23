package com.krnl32.jupiter.engine.sceneserializer.jnative.components.effects;

import com.krnl32.jupiter.engine.components.effects.ParticleComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import org.joml.Vector3f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * == ParticleComponent (size: 20 Bytes) ==
 * 12 Bytes	Velocity		float32 x 3
 * 4 Bytes	Duration		float32
 * 4 Bytes	RemainingTime	float32
 */
public class JParticleComponentSerializer implements ComponentSerializer<ParticleComponent, byte[]> {
	private static final int SIZE = 20;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(ParticleComponent component) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeVector3f(buffer, component.velocity);
		JSerializerUtility.serializeFloat(buffer, component.duration);
		JSerializerUtility.serializeFloat(buffer, component.remainingTime);

		return buffer.array();
	}

	@Override
	public ParticleComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JParticleComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		Vector3f velocity = JSerializerUtility.deserializeVector3f(buffer);
		float duration = JSerializerUtility.deserializeFloat(buffer);
		float remainingTime = JSerializerUtility.deserializeFloat(buffer);

		return new ParticleComponent(
			velocity,
			duration,
			remainingTime
		);
	}
}
