package com.krnl32.jupiter.ecs;

import java.lang.reflect.Array;

public class ComponentPool<T extends Component> {
	private final T[] components;

	@SuppressWarnings("unchecked")
	public ComponentPool(Class<T> component, int maxEntities) {
		this.components = (T[])Array.newInstance(component, maxEntities);
	}

	public void set(Entity entity, T component) {
		components[entity.getId()] = component;
	}

	public T get(Entity entity) {
		return components[entity.getId()];
	}

	public void remove(Entity entity) {
		components[entity.getId()] = null;
	}

	public boolean has(Entity entity) {
		return components[entity.getId()] != null;
	}

	public T[] getComponents() {
		return components;
	}
}
