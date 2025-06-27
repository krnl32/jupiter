package com.krnl32.jupiter.systems;

import com.krnl32.jupiter.components.DestroyComponent;
import com.krnl32.jupiter.components.LifetimeComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.renderer.Renderer;

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
				entity.addComponent(new DestroyComponent());
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
