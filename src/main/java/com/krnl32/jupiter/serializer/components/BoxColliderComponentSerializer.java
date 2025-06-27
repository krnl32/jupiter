package com.krnl32.jupiter.serializer.components;

import com.krnl32.jupiter.components.BoxColliderComponent;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.serializer.utility.JOMLSerializerUtils;
import org.json.JSONObject;

public class BoxColliderComponentSerializer implements ComponentSerializer<BoxColliderComponent> {
	@Override
	public JSONObject serialize(BoxColliderComponent component) {
		return new JSONObject()
			.put("size", JOMLSerializerUtils.serializeVector3f(component.size))
			.put("offset", JOMLSerializerUtils.serializeVector3f(component.offset));
	}

	@Override
	public BoxColliderComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new BoxColliderComponent(
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("size")),
			JOMLSerializerUtils.deserializeVector3f(data.getJSONObject("offset"))
		);
	}
}
