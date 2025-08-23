package com.krnl32.jupiter.engine.sceneserializer.jnative.components.effects;

import com.krnl32.jupiter.engine.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.model.Sprite;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JComponentSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * == DeathEffectComponent (size: 72 Bytes) ==
 * 4  Bytes	ParticleCount	uint32
 * 68 Bytes ParticleSprite	Sprite
 */
public class JDeathEffectComponentSerializer implements ComponentSerializer<DeathEffectComponent, byte[]> {
	private static final int SIZE = 72;
	private static final int SPRITE_SIZE = 68;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(DeathEffectComponent component) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeInt(buffer, component.particleCount);
		byte[] spriteData = JComponentSerializerUtility.serializeSprite(component.particleSprite);
		JSerializerUtility.serializeByteArray(buffer, spriteData);

		return buffer.array();
	}

	@Override
	public DeathEffectComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JDeathEffectComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		int particleCount = JSerializerUtility.deserializeInt(buffer);
		byte[] spriteData = JSerializerUtility.deserializeByteArray(buffer, SPRITE_SIZE);
		Sprite particleSprite = JComponentSerializerUtility.deserializeSprite(spriteData);

		if (particleSprite == null) {
			Logger.error("JDeathEffectComponentSerializer Deserialize Failed for ParticleSprite");
			return null;
		}

		return new DeathEffectComponent(particleCount, particleSprite);
	}
}
