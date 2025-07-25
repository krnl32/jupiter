package com.krnl32.jupiter.engine.serializer;

import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

public interface ComponentSerializer<T extends Component> {
	JSONObject serialize(T component);
	T deserialize(JSONObject data, EntityResolver resolver);
	T clone(T component);
}
