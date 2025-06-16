package com.github.krnl32.jupiter.systems;

import com.github.krnl32.jupiter.components.LifetimeComponent;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.ecs.Registry;
import com.github.krnl32.jupiter.ecs.System;
import com.github.krnl32.jupiter.renderer.Renderer;

public class LifetimeSystem implements System {
	private final Registry registry;

	public LifetimeSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity : registry.getEntitiesWith(LifetimeComponent.class)) {
			LifetimeComponent lifetime = entity.getComponent(LifetimeComponent.class);
			lifetime.remainingTime -= dt;

			if (lifetime.remainingTime <= 0)
				registry.destroyEntity(entity);
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
