package com.github.krnl32.jupiter.serializer;

import com.github.krnl32.jupiter.ecs.Component;
import com.github.krnl32.jupiter.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

public interface ComponentSerializer<T extends Component> {
	JSONObject serialize(T component);
	T deserialize(JSONObject data, EntityResolver resolver);
}
