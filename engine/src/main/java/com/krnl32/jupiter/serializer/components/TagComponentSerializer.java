package com.krnl32.jupiter.serializer.components;

import com.krnl32.jupiter.components.TagComponent;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

public class TagComponentSerializer implements ComponentSerializer<TagComponent> {
	@Override
	public JSONObject serialize(TagComponent component) {
		return new JSONObject().put("tag", component.tag);
	}

	@Override
	public TagComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new TagComponent(data.getString("tag"));
	}
}
