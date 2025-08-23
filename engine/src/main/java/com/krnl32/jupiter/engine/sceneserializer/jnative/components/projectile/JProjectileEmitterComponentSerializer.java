package com.krnl32.jupiter.engine.sceneserializer.jnative.components.projectile;

import com.krnl32.jupiter.engine.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.input.devices.KeyCode;
import com.krnl32.jupiter.engine.model.Sprite;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JComponentSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * == ProjectileEmitterComponent (size: 84 Bytes) ==
 * 4 Bytes	ShootKey			uint32
 * 4 Bytes	FireRate			float32
 * 4 Bytes	ProjectileSpeed		float32
 * 4 Bytes	LastEmissionTime	float32
 * 68 Bytes Sprite				Sprite
 */
public class JProjectileEmitterComponentSerializer implements ComponentSerializer<ProjectileEmitterComponent, byte[]> {
	private static final int SIZE = 84;
	private static final int SPRITE_SIZE = 68;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(ProjectileEmitterComponent component) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeInt(buffer, component.shootKey.getCode());
		JSerializerUtility.serializeFloat(buffer, component.fireRate);
		JSerializerUtility.serializeFloat(buffer, component.projectileSpeed);
		JSerializerUtility.serializeFloat(buffer, component.lastEmissionTime);

		byte[] spriteData = JComponentSerializerUtility.serializeSprite(component.sprite);
		JSerializerUtility.serializeByteArray(buffer, spriteData);

		return buffer.array();
	}

	@Override
	public ProjectileEmitterComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JProjectileEmitterComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		int shootKeyCode = JSerializerUtility.deserializeInt(buffer);
		KeyCode shootKey = KeyCode.fromCode(shootKeyCode);

		float fireRate = JSerializerUtility.deserializeFloat(buffer);
		float projectileSpeed = JSerializerUtility.deserializeFloat(buffer);
		float lastEmissionTime = JSerializerUtility.deserializeFloat(buffer);

		byte[] spriteData = JSerializerUtility.deserializeByteArray(buffer, SPRITE_SIZE);
		Sprite sprite = JComponentSerializerUtility.deserializeSprite(spriteData);

		if (sprite == null) {
			Logger.error("JProjectileEmitterComponentSerializer Deserialize Failed for ParticleSprite");
			return null;
		}

		return new ProjectileEmitterComponent(
			shootKey,
			fireRate,
			projectileSpeed,
			lastEmissionTime,
			sprite
		);
	}
}
