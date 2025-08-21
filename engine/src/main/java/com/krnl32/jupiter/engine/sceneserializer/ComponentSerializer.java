package com.krnl32.jupiter.engine.sceneserializer;

import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

public interface ComponentSerializer<T extends Component, R> {
	R serialize(T component);
	T deserialize(R data, EntityResolver resolver);
}
