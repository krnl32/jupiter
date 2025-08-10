package com.krnl32.jupiter.engine.serializer.components.projectile;

import com.krnl32.jupiter.engine.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;

import java.util.Map;

public class ProjectileEmitterComponentSerializer implements ComponentSerializer<ProjectileEmitterComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(ProjectileEmitterComponent component) {
//		return new JSONObject()
//			.put("shootKey", (component.shootKey != null ? component.shootKey.getCode() : JSONObject.NULL))
//			.put("fireRate", component.fireRate)
//			.put("projectileSpeed", component.projectileSpeed)
//			.put("lastEmissionTime", component.lastEmissionTime)
//			.put("sprite", JupiterSerializerUtils.serializeSprite(component.sprite));
		return null;
	}

	@Override
	public ProjectileEmitterComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
//		return new ProjectileEmitterComponent(
//			(data.isNull("shootKey") ? null : KeyCode.fromCode(data.getInt("shootKey"))),
//			data.getFloat("fireRate"),
//			data.getFloat("projectileSpeed"),
//			data.getFloat("lastEmissionTime"),
//			JupiterSerializerUtils.deserializeSprite(data.getJSONObject("sprite"))
//		);
		return null;
	}
}
