package com.krnl32.jupiter.serializer.components.physics;

import com.krnl32.jupiter.components.physics.CircleCollider2DComponent;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JOMLSerializerUtils;
import org.joml.Vector2f;
import org.json.JSONObject;

public class CircleCollider2DComponentSerializer implements ComponentSerializer<CircleCollider2DComponent> {
	@Override
	public JSONObject serialize(CircleCollider2DComponent component) {
		return new JSONObject()
			.put("radius", component.radius)
			.put("offset", JOMLSerializerUtils.serializeVector2f(component.offset))
			.put("friction", component.friction)
			.put("density", component.density)
			.put("sensor", component.sensor);
	}

	@Override
	public CircleCollider2DComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new CircleCollider2DComponent(
			data.getFloat("radius"),
			JOMLSerializerUtils.deserializeVector2f(data.getJSONObject("offset")),
			data.getFloat("friction"),
			data.getFloat("density"),
			data.getBoolean("sensor")
		);
	}

	@Override
	public CircleCollider2DComponent clone(CircleCollider2DComponent component) {
		return new CircleCollider2DComponent(
			component.radius,
			new Vector2f(component.offset),
			component.friction,
			component.density,
			component.sensor
		);
	}
}
