package com.github.krnl32.jupiter.game;

import java.util.HashMap;
import java.util.Map;

public abstract class Actor {
	private final Map<Class<? extends Component>, Component> components = new HashMap<>();

	public void onUpdate(float dt) {
		for (var component: components.values())
			component.onUpdate(dt);
	}

	public void onRender(float dt) {
		for (var component: components.values())
			component.onRender(dt);
	}

	public void addComponent(Component component) {
		components.put(component.getClass(), component);
		component.setActor(this);
	}

	public void removeComponent(Class<? extends Component> component) {
		components.remove(component);
	}

	public boolean hasComponent(Class<? extends Component> component) {
		return components.containsKey(component);
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> T getComponent(Class<T> component) {
		return (T) components.get(component);
	}
}
