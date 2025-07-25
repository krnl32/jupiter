package com.krnl32.jupiter.engine.systems.utility;

import com.krnl32.jupiter.engine.components.utility.DestroyComponent;
import com.krnl32.jupiter.engine.components.utility.LifetimeComponent;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.renderer.Renderer;

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
