package com.krnl32.jupiter.engine.sceneserializer.jnative.components.effects;

import com.krnl32.jupiter.engine.components.effects.BlinkComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * == BlinkComponent (size: 17 Bytes) ==
 * 4 Bytes	Duration	float32
 * 4 Bytes	Interval	float32
 * 4 Bytes	ElapsedTime	float32
 * 4 Bytes	BlinkTime	float32
 * 1 Byte	visible		uint8
 */
public class JBlinkComponentSerializer implements ComponentSerializer<BlinkComponent, byte[]> {
	private static final int SIZE = 17;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(BlinkComponent component) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeFloat(buffer, component.duration);
		JSerializerUtility.serializeFloat(buffer, component.interval);
		JSerializerUtility.serializeFloat(buffer, component.elapsedTime);
		JSerializerUtility.serializeFloat(buffer, component.blinkTime);
		JSerializerUtility.serializeBool(buffer, component.visible);

		return buffer.array();
	}

	@Override
	public BlinkComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JBlinkComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		float duration = JSerializerUtility.deserializeFloat(buffer);
		float interval = JSerializerUtility.deserializeFloat(buffer);
		float elapsedTime = JSerializerUtility.deserializeFloat(buffer);
		float blinkTime = JSerializerUtility.deserializeFloat(buffer);
		boolean visible = JSerializerUtility.deserializeBool(buffer);

		return new BlinkComponent(
			duration,
			interval,
			elapsedTime,
			blinkTime,
			visible
		);
	}
}
