package com.krnl32.jupiter.engine.sceneserializer.jnative.components.utility;

import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

import java.util.UUID;

/*
 * == UUIDComponent (size: 16 Bytes) ==
 * 16 Bytes	UUID
 */
public class JUUIDComponentSerializer implements ComponentSerializer<UUIDComponent, byte[]> {
	private static final int SIZE = 16;

	@Override
	public byte[] serialize(UUIDComponent component) {
		return JSerializerUtility.serializeUUID(component.uuid);
	}

	@Override
	public UUIDComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JUUIDComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		UUID uuid = JSerializerUtility.deserializeUUID(data);

		return new UUIDComponent(uuid);
	}
}
