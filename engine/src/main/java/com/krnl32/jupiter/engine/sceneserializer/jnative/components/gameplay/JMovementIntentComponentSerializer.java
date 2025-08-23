package com.krnl32.jupiter.engine.sceneserializer.jnative.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.MovementIntentComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import org.joml.Vector3f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * == MovementIntentComponent (size: 26 Bytes) ==
 * 12 Bytes	Translation		float32 x 3
 * 12 Bytes	Rotation		float32 x 3
 * 1  Byte	Jump			uint8
 * 1  Byte	Sprint			uint8
 */
public class JMovementIntentComponentSerializer implements ComponentSerializer<MovementIntentComponent, byte[]> {
	private static final int SIZE = 26;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(MovementIntentComponent component) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeVector3f(buffer, component.translation);
		JSerializerUtility.serializeVector3f(buffer, component.rotation);
		JSerializerUtility.serializeBool(buffer, component.jump);
		JSerializerUtility.serializeBool(buffer, component.sprint);

		return buffer.array();
	}

	@Override
	public MovementIntentComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JMovementIntentComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		Vector3f translation = JSerializerUtility.deserializeVector3f(buffer);
		Vector3f rotation = JSerializerUtility.deserializeVector3f(buffer);
		boolean jump = JSerializerUtility.deserializeBool(buffer);
		boolean sprint = JSerializerUtility.deserializeBool(buffer);

		return new MovementIntentComponent(translation, rotation, jump, sprint);
	}
}
