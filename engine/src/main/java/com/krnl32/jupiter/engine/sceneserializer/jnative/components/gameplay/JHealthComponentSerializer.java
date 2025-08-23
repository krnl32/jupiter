package com.krnl32.jupiter.engine.sceneserializer.jnative.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.HealthComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * == HealthComponent (size: 8 Bytes) ==
 * 4 Bytes	MaxHealth		float32
 * 4 Bytes	CurrentHealth	float32
 */
public class JHealthComponentSerializer implements ComponentSerializer<HealthComponent, byte[]> {
	private static final int SIZE = 8;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(HealthComponent component) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeFloat(buffer, component.maxHealth);
		JSerializerUtility.serializeFloat(buffer, component.currentHealth);

		return buffer.array();
	}

	@Override
	public HealthComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JHealthComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		float maxHealth = JSerializerUtility.deserializeFloat(buffer);
		float currentHealth = JSerializerUtility.deserializeFloat(buffer);

		return new HealthComponent(maxHealth, currentHealth);
	}
}
