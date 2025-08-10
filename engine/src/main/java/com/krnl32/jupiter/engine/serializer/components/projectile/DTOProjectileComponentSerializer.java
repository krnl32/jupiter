package com.krnl32.jupiter.engine.serializer.components.projectile;

import com.krnl32.jupiter.engine.components.projectile.ProjectileComponent;
import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.DTOComponentSerializerUtility;

import java.util.Map;
import java.util.UUID;

public class DTOProjectileComponentSerializer implements ComponentSerializer<ProjectileComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(ProjectileComponent component) {
		UUIDComponent ownerUUIDComponent = component.owner.getComponent(UUIDComponent.class);
		if (ownerUUIDComponent == null) {
			Logger.error("DTOProjectileComponentSerializer Serialize Failed, Owner Entity({}) has No UUIDComponent", component.owner.getId());
			return null;
		}

		return Map.of(
			"owner", ownerUUIDComponent.uuid.toString(),
			"damage", component.damage,
			"canHitOwner", component.canHitOwner
		);
	}

	@Override
	public ProjectileComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new ProjectileComponent(
			resolver.resolve(UUID.fromString(data.get("owner").toString())),
			DTOComponentSerializerUtility.toFloat(data.get("damage")),
			(boolean) data.get("canHitOwner")
		);
	}
}
