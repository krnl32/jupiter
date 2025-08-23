package com.krnl32.jupiter.engine.sceneserializer.jnative.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import org.joml.Vector3f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * == TransformComponent (size: 36 Bytes) ==
 * 12 Bytes	Translation		float32 x 3
 * 12 Bytes	Rotation		float32 x 3
 * 12 Bytes	Scale			float32 x 3
 */
public class JTransformComponentSerializer implements ComponentSerializer<TransformComponent, byte[]> {
	private static final int SIZE = 36;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(TransformComponent component) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeVector3f(buffer, component.translation);
		JSerializerUtility.serializeVector3f(buffer, component.rotation);
		JSerializerUtility.serializeVector3f(buffer, component.scale);

		return buffer.array();
	}

	@Override
	public TransformComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JTransformComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		Vector3f translation = JSerializerUtility.deserializeVector3f(buffer);
		Vector3f rotation = JSerializerUtility.deserializeVector3f(buffer);
		Vector3f scale = JSerializerUtility.deserializeVector3f(buffer);

		return new TransformComponent(translation, rotation, scale);
	}
}
