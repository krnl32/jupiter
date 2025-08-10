package com.krnl32.jupiter.engine.serializer.oldcomponents.utility;

import com.krnl32.jupiter.engine.components.utility.TagComponent;
import com.krnl32.jupiter.engine.serializer.OldComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

public class TagComponentSerializer implements OldComponentSerializer<TagComponent> {
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
