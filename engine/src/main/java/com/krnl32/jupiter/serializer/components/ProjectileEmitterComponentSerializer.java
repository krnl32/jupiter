package com.krnl32.jupiter.serializer.components;

import com.krnl32.jupiter.components.ProjectileEmitterComponent;
import com.krnl32.jupiter.input.KeyCode;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JupiterSerializerUtils;
import org.json.JSONObject;

public class ProjectileEmitterComponentSerializer implements ComponentSerializer<ProjectileEmitterComponent> {
	@Override
	public JSONObject serialize(ProjectileEmitterComponent component) {
		return new JSONObject()
			.put("shootKey", (component.shootKey != null ? component.shootKey.getCode() : JSONObject.NULL))
			.put("fireRate", component.fireRate)
			.put("projectileSpeed", component.projectileSpeed)
			.put("lastEmissionTime", component.lastEmissionTime)
			.put("sprite", JupiterSerializerUtils.serializeSprite(component.sprite));
	}

	@Override
	public ProjectileEmitterComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new ProjectileEmitterComponent(
			(data.isNull("shootKey") ? null : KeyCode.fromCode(data.getInt("shootKey"))),
			data.getFloat("fireRate"),
			data.getFloat("projectileSpeed"),
			data.getFloat("lastEmissionTime"),
			JupiterSerializerUtils.deserializeSprite(data.getJSONObject("sprite"))
		);
	}
}
