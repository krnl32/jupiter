package com.krnl32.jupiter.serializer.resolvers;

import com.krnl32.jupiter.ecs.Entity;

import java.util.UUID;

@FunctionalInterface
public interface EntityResolver {
	Entity resolve(UUID uuid);
}
