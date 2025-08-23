package com.krnl32.jupiter.engine.sceneserializer.jnative.components.utility;

import com.krnl32.jupiter.engine.components.utility.LifetimeComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

/*
 * == LifetimeComponent (size: 4 Bytes) ==
 * 4 Bytes	RemainingTime	float32
 */
public class JLifetimeComponentSerializer implements ComponentSerializer<LifetimeComponent, byte[]> {
	private static final int SIZE = 4;

	@Override
	public byte[] serialize(LifetimeComponent component) {
		return JSerializerUtility.serializeFloat(component.remainingTime);
	}

	@Override
	public LifetimeComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JLifetimeComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		float remainingTime = JSerializerUtility.deserializeFloat(data);

		return new LifetimeComponent(remainingTime);
	}
}
