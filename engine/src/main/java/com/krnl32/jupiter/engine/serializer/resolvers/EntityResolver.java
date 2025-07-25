package com.krnl32.jupiter.engine.serializer.resolvers;

import com.krnl32.jupiter.engine.ecs.Entity;

import java.util.UUID;

@FunctionalInterface
public interface EntityResolver {
	Entity resolve(UUID uuid);
}
