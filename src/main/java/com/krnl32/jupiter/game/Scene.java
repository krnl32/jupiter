package com.krnl32.jupiter.game;

import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.renderer.Renderer;

public abstract class Scene {
	private final Registry registry = new Registry();
	private boolean initialized = false;

	public void onUpdate(float dt) {
		registry.onUpdate(dt);
	}

	public void onRender(float dt, Renderer renderer) {
		registry.onRender(dt, renderer);
	}

	public final void load() {
		if (!initialized) {
			onCreate();
			initialized = true;
		}
		onActivate();
	}

	public abstract void onCreate();
	public abstract void onActivate();
	public abstract void onUnload();

	public Registry getRegistry() {
		return registry;
	}

	public Entity createEntity() {
		return registry.createEntity();
	}

	public void destroyEntity(Entity entity) {
		registry.destroyEntity(entity);
	}

	public void addSystem(System system) {
		registry.addSystem(system);
	}

	public void addSystem(System system, int priority, boolean enabled) {
		registry.addSystem(system, priority, enabled);
	}

	public void removeSystem(Class<? extends System> system) {
		registry.removeSystem(system);
	}

	public void setSystemEnabled(Class<? extends System> system, boolean enabled) {
		registry.setSystemEnabled(system, enabled);
	}
}
