package com.krnl32.jupiter.serializer.components.projectile;

import com.krnl32.jupiter.components.utility.UUIDComponent;
import com.krnl32.jupiter.components.projectile.ProjectileComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

import java.util.UUID;

public class ProjectileComponentSerializer implements ComponentSerializer<ProjectileComponent> {
	@Override
	public JSONObject serialize(ProjectileComponent component) {
		UUIDComponent ownerUUIDComponent = component.owner.getComponent(UUIDComponent.class);
		if (ownerUUIDComponent == null) {
			Logger.error("ProjectileComponentSerializer Serialize Failed, Owner Entity({}) has No UUIDComponent", component.owner.getId());
			return null;
		}

		return new JSONObject()
			.put("owner", ownerUUIDComponent.uuid.toString())
			.put("damage", component.damage)
			.put("canHitOwner", component.canHitOwner);
	}

	@Override
	public ProjectileComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new ProjectileComponent(
			resolver.resolve(UUID.fromString(data.getString("owner"))),
			data.getFloat("damage"),
			data.getBoolean("canHitOwner")
		);
	}

	@Override
	public ProjectileComponent clone(ProjectileComponent component) {
		return new ProjectileComponent(
			new Entity(component.owner.getId(), component.owner.getRegistry()),
			component.damage,
			component.canHitOwner
		);
	}
}
