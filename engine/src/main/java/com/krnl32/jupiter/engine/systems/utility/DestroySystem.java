package com.krnl32.jupiter.engine.systems.utility;

import com.krnl32.jupiter.engine.components.utility.DestroyComponent;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.entity.EntityDestroyedEvent;
import com.krnl32.jupiter.engine.renderer.Renderer;

public class DestroySystem implements System {
	private final Registry registry;

	public DestroySystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity: registry.getEntitiesWith(DestroyComponent.class)) {
			EventBus.getInstance().emit(new EntityDestroyedEvent(entity));
			//Logger.info("Destroying Entity({})", entity.getTagOrId());
			registry.destroyEntity(entity);
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
