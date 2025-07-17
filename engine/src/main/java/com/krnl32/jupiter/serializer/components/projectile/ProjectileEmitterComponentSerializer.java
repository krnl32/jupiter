package com.krnl32.jupiter.serializer.components.projectile;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.input.devices.KeyCode;
import com.krnl32.jupiter.model.Sprite;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JupiterSerializerUtils;
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
				component.sprite.getTextureAssetID() != null ? new AssetID(component.sprite.getTextureAssetID().getId()) : null,
				component.sprite.getTextureUV().clone()
			)
		);
	}
}
