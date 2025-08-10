package com.krnl32.jupiter.engine.serializer;

import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;

public interface ComponentSerializer<T extends Component, R> {
	R serialize(T component);
	T deserialize(R data, EntityResolver resolver);
}
