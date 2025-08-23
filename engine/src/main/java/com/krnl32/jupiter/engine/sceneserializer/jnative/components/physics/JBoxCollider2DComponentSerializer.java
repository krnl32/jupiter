package com.krnl32.jupiter.engine.sceneserializer.jnative.components.physics;

import com.krnl32.jupiter.engine.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import org.joml.Vector2f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * == BoxCollider2DComponent (size: 25 Bytes) ==
 * 8 Bytes	Size		float32 x 2
 * 8 Bytes	Offset		float32 x 2
 * 4 Bytes	Friction	float32
 * 4 Bytes	Density		float32
 * 1 Byte	Sensor		uint8
 */
public class JBoxCollider2DComponentSerializer implements ComponentSerializer<BoxCollider2DComponent, byte[]> {
	private static final int SIZE = 25;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(BoxCollider2DComponent component) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeVector2f(buffer, component.size);
		JSerializerUtility.serializeVector2f(buffer, component.offset);
		JSerializerUtility.serializeFloat(buffer, component.friction);
		JSerializerUtility.serializeFloat(buffer, component.density);
		JSerializerUtility.serializeBool(buffer, component.sensor);

		return buffer.array();
	}

	@Override
	public BoxCollider2DComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JBoxCollider2DComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		Vector2f size = JSerializerUtility.deserializeVector2f(buffer);
		Vector2f offset = JSerializerUtility.deserializeVector2f(buffer);
		float friction = JSerializerUtility.deserializeFloat(buffer);
		float density = JSerializerUtility.deserializeFloat(buffer);
		boolean sensor = JSerializerUtility.deserializeBool(buffer);

		return new BoxCollider2DComponent(
			size,
			offset,
			friction,
			density,
			sensor
		);
	}
}
