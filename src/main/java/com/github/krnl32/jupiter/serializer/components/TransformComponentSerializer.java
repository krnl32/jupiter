package com.github.krnl32.jupiter.serializer.components;

import com.github.krnl32.jupiter.components.TransformComponent;
import com.github.krnl32.jupiter.serializer.ComponentSerializer;
import com.github.krnl32.jupiter.serializer.JSONSerializerUtils;
import org.json.JSONObject;

public class TransformComponentSerializer implements ComponentSerializer<TransformComponent> {
	@Override
	public JSONObject serialize(TransformComponent component) {
		return new JSONObject()
			.put("translation", JSONSerializerUtils.serializeVector3f(component.translation))
			.put("rotation", JSONSerializerUtils.serializeVector3f(component.rotation))
			.put("scale", JSONSerializerUtils.serializeVector3f(component.scale));
	}

	@Override
	public TransformComponent deserialize(JSONObject data) {
		return new TransformComponent(
			JSONSerializerUtils.deserializeVector3f(data.getJSONObject("translation")),
			JSONSerializerUtils.deserializeVector3f(data.getJSONObject("rotation")),
			JSONSerializerUtils.deserializeVector3f(data.getJSONObject("scale")));
	}
}
