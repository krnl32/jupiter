package com.krnl32.jupiter.serializer.components.physics;

import com.krnl32.jupiter.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JOMLSerializerUtils;
import org.joml.Vector2f;
import org.json.JSONObject;

public class BoxCollider2DComponentSerializer implements ComponentSerializer<BoxCollider2DComponent> {
	@Override
	public JSONObject serialize(BoxCollider2DComponent component) {
		return new JSONObject()
			.put("size", JOMLSerializerUtils.serializeVector2f(component.size))
			.put("offset", JOMLSerializerUtils.serializeVector2f(component.offset))
			.put("friction", component.friction)
			.put("density", component.density)
			.put("sensor", component.sensor);
	}

	@Override
	public BoxCollider2DComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new BoxCollider2DComponent(
			JOMLSerializerUtils.deserializeVector2f(data.getJSONObject("size")),
			JOMLSerializerUtils.deserializeVector2f(data.getJSONObject("offset")),
			data.getFloat("friction"),
			data.getFloat("density"),
			data.getBoolean("sensor")
		);
	}

	@Override
	public BoxCollider2DComponent clone(BoxCollider2DComponent component) {
		return new BoxCollider2DComponent(
			new Vector2f(component.size),
			new Vector2f(component.offset),
			component.friction,
			component.density,
			component.sensor
		);
	}
}
