package com.krnl32.jupiter.engine.serializer.components.utility;

import com.krnl32.jupiter.engine.components.utility.TagComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
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

	@Override
	public TagComponent clone(TagComponent component) {
		return new TagComponent(component.tag);
	}
}
