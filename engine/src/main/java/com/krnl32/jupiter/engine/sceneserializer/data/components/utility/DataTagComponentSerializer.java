package com.krnl32.jupiter.engine.sceneserializer.data.components.utility;

import com.krnl32.jupiter.engine.components.utility.TagComponent;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

import java.util.Map;

public class DataTagComponentSerializer implements ComponentSerializer<TagComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(TagComponent component) {
		return Map.of("tag", component.tag);
	}

	@Override
	public TagComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new TagComponent(data.get("tag").toString());
	}
}
