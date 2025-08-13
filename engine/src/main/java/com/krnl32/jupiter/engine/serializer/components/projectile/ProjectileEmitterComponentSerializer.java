package com.krnl32.jupiter.engine.serializer.components.projectile;

import com.krnl32.jupiter.engine.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.engine.input.devices.KeyCode;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.ComponentSerializerUtility;

import java.util.HashMap;
import java.util.Map;

public class ProjectileEmitterComponentSerializer implements ComponentSerializer<ProjectileEmitterComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(ProjectileEmitterComponent component) {
		Map<String, Object> map = new HashMap<>();
		map.put("shootKey", (component.shootKey != null) ? component.shootKey.getCode() : null);
		map.put("fireRate", component.fireRate);
		map.put("projectileSpeed", component.projectileSpeed);
		map.put("lastEmissionTime", component.lastEmissionTime);
		map.put("sprite", ComponentSerializerUtility.serializeSprite(component.sprite));
		return map;
	}

	@Override
	public ProjectileEmitterComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		KeyCode shootKey = data.get("shootKey") != null ? KeyCode.fromCode((int) data.get("shootKey")) : null;
		return new ProjectileEmitterComponent(
			shootKey,
			ComponentSerializerUtility.toFloat(data.get("fireRate")),
			ComponentSerializerUtility.toFloat(data.get("projectileSpeed")),
			ComponentSerializerUtility.toFloat(data.get("lastEmissionTime")),
			ComponentSerializerUtility.deserializeSprite((Map<String, Object>) data.get("sprite"))
		);
	}
}
