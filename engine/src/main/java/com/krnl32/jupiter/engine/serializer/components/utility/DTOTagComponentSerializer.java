package com.krnl32.jupiter.engine.serializer.components.utility;

import com.krnl32.jupiter.engine.components.utility.TagComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;

import java.util.Map;

public class DTOTagComponentSerializer implements ComponentSerializer<TagComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(TagComponent component) {
		return Map.of("tag", component.tag);
	}

	@Override
	public TagComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new TagComponent(data.get("tag").toString());
	}
}
