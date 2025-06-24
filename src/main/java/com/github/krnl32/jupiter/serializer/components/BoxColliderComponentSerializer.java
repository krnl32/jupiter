package com.github.krnl32.jupiter.serializer.components;

import com.github.krnl32.jupiter.components.BoxColliderComponent;
import com.github.krnl32.jupiter.serializer.ComponentSerializer;
import com.github.krnl32.jupiter.serializer.utility.JOMLSerializerUtils;
import org.json.JSONObject;

public class BoxColliderComponentSerializer implements ComponentSerializer<BoxColliderComponent> {
	@Override
	public JSONObject serialize(BoxColliderComponent component) {
		return new JSONObject()
			.put("size", JOMLSerializerUtils.serializeVector3f(component.size))
			.put("offset", JOMLSerializerUtils.serializeVector3f(component.offset));
	}

	@Override
	public BoxColliderComponent deserialize(JSONObject data) {
		return new BoxColliderComponent(
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("size")),
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("offset"))
		);
	}
}
