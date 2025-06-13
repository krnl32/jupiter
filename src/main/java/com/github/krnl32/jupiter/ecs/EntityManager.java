package com.github.krnl32.jupiter.ecs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EntityManager {
	private int nextId = 0;
	private final Set<Integer> entities = new HashSet<>();

	public Entity create(Registry registry) {
		int id = nextId++;
		entities.add(id);
		return new Entity(id, registry);
	}

	public void remove(Entity entity) {
		entities.remove(entity.getId());
	}

	public boolean has(Entity entity) {
		return entities.contains(entity.getId());
	}

	public Set<Integer> getAllEntityIds() {
		return Collections.unmodifiableSet(entities);
	}
}
