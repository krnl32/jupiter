package com.krnl32.jupiter.engine.sceneserializer.data.components.projectile;

import com.krnl32.jupiter.engine.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.engine.input.devices.KeyCode;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataComponentSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataSerializerUtility;

import java.util.HashMap;
import java.util.Map;

public class DataProjectileEmitterComponentSerializer implements ComponentSerializer<ProjectileEmitterComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(ProjectileEmitterComponent component) {
		Map<String, Object> map = new HashMap<>();
		map.put("shootKey", (component.shootKey != null) ? component.shootKey.getCode() : null);
		map.put("fireRate", component.fireRate);
		map.put("projectileSpeed", component.projectileSpeed);
		map.put("lastEmissionTime", component.lastEmissionTime);
		map.put("sprite", DataComponentSerializerUtility.serializeSprite(component.sprite));
		return map;
	}

	@Override
	public ProjectileEmitterComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		KeyCode shootKey = data.get("shootKey") != null ? KeyCode.fromCode((int) data.get("shootKey")) : null;
		return new ProjectileEmitterComponent(
			shootKey,
			DataSerializerUtility.toFloat(data.get("fireRate")),
			DataSerializerUtility.toFloat(data.get("projectileSpeed")),
			DataSerializerUtility.toFloat(data.get("lastEmissionTime")),
			DataComponentSerializerUtility.deserializeSprite((Map<String, Object>) data.get("sprite"))
		);
	}
}
