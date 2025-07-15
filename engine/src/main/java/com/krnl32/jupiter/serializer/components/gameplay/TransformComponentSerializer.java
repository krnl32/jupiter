package com.krnl32.jupiter.serializer.components.gameplay;

import com.krnl32.jupiter.components.gameplay.TransformComponent;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JOMLSerializerUtils;
import org.joml.Vector3f;
import org.json.JSONObject;

public class TransformComponentSerializer implements ComponentSerializer<TransformComponent> {
	@Override
	public JSONObject serialize(TransformComponent component) {
		return new JSONObject()
			.put("translation", JOMLSerializerUtils.serializeVector3f(component.translation))
			.put("rotation", JOMLSerializerUtils.serializeVector3f(component.rotation))
			.put("scale", JOMLSerializerUtils.serializeVector3f(component.scale));
	}

	@Override
	public TransformComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new TransformComponent(
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("translation")),
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("rotation")),
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("scale"))
		);
	}

	@Override
	public TransformComponent clone(TransformComponent component) {
		return new TransformComponent(
			new Vector3f(component.translation),
			new Vector3f(component.rotation),
			new Vector3f(component.scale)
		);
	}
}
