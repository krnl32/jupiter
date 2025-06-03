package com.github.krnl32.jupiter.game;

import com.github.krnl32.jupiter.renderer.Renderer;

import java.util.HashMap;
import java.util.Map;

public abstract class GameObject {
	private final Map<Class<? extends Component>, Component> components = new HashMap<>();

	public void onUpdate(float dt) {
		for (var component: components.values())
			component.onUpdate(dt);
	}

	public void onRender(float dt, Renderer renderer) {
		for (var component: components.values())
			component.onRender(dt, renderer);
	}

	public void addComponent(Component component) {
		components.put(component.getClass(), component);
		component.setGameObject(this);
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
