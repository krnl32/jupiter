package com.krnl32.jupiter.engine.sceneserializer.jnative.components.projectile;

import com.krnl32.jupiter.engine.components.projectile.ProjectileComponent;
import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

/*
 * == ProjectileComponent (size: 21 Bytes) ==
 * 16 Bytes	Owner		UUID
 * 4 Bytes	Damage		float32
 * 1 Byte	CanHitOwner	uint8
 */
public class JProjectileComponentSerializer implements ComponentSerializer<ProjectileComponent, byte[]> {
	private static final int SIZE = 21;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(ProjectileComponent component) {
		UUID ownerUUID = new UUID(0L, 0L);

		if (component.owner != null) {
			UUIDComponent ownerUUIDComponent = component.owner.getComponent(UUIDComponent.class);

			if (ownerUUIDComponent == null) {
				Logger.error("JProjectileComponentSerializer Serialize Failed, Owner Entity({}) has No UUIDComponent", component.owner.getId());
				return null;
			}

			ownerUUID = ownerUUIDComponent.uuid;
		}

		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeUUID(buffer, ownerUUID);
		JSerializerUtility.serializeFloat(buffer, component.damage);
		JSerializerUtility.serializeBool(buffer, component.canHitOwner);

		return buffer.array();
	}

	@Override
	public ProjectileComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JProjectileComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		UUID ownerUUID = JSerializerUtility.deserializeUUID(buffer);
		float damage = JSerializerUtility.deserializeFloat(buffer);
		boolean canHitOwner = JSerializerUtility.deserializeBool(buffer);

		Entity ownerEntity = null;

		if (!ownerUUID.equals(new UUID(0L, 0L))) {
			ownerEntity = resolver.resolve(ownerUUID);
		}

		return new ProjectileComponent(ownerEntity, damage, canHitOwner);
	}
}
