package com.krnl32.jupiter.engine.serializer.components.projectile;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.engine.input.devices.KeyCode;
import com.krnl32.jupiter.engine.model.Sprite;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.JupiterSerializerUtils;
import org.joml.Vector4f;
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

	@Override
	public ProjectileEmitterComponent clone(ProjectileEmitterComponent component) {

		return new	ProjectileEmitterComponent(
			component.shootKey,
			component.fireRate,
			component.projectileSpeed,
			new Sprite(
				component.sprite.getIndex(),
				new Vector4f(component.sprite.getColor()),
				component.sprite.getTextureAssetId() != null ? new AssetId(component.sprite.getTextureAssetId().getId()) : null,
				component.sprite.getTextureUV().clone()
			)
		);
	}
}
