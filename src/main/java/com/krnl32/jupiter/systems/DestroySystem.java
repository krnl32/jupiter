package com.krnl32.jupiter.systems;

import com.krnl32.jupiter.components.DestroyComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.renderer.Renderer;

public class DestroySystem implements System {
	private final Registry registry;

	public DestroySystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity: registry.getEntitiesWith(DestroyComponent.class)) {
			//Logger.info("Destroying Entity({})", entity.getTagOrId());
			entity.destroy();
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {

	}
}
