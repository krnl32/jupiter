package com.github.krnl32.jupiter.serializer.components;

import com.github.krnl32.jupiter.components.UUIDComponent;
import com.github.krnl32.jupiter.components.ProjectileComponent;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.serializer.ComponentSerializer;
import com.github.krnl32.jupiter.serializer.resolvers.EntityResolver;
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
}
