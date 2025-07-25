package com.krnl32.jupiter.engine.systems.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.HealthComponent;
import com.krnl32.jupiter.engine.components.utility.DestroyComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.gameplay.EntityDeathEvent;
import com.krnl32.jupiter.engine.renderer.Renderer;

public class HealthSystem implements System {
	private final Registry registry;

	public HealthSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity: registry.getEntitiesWith(HealthComponent.class)) {
			HealthComponent health = entity.getComponent(HealthComponent.class);
			if (health.currentHealth <= 0 && !entity.hasComponent(DestroyComponent.class)) {
				EventBus.getInstance().emit(new EntityDeathEvent(entity));
				entity.addComponent(new DestroyComponent());
				Logger.info("Entity({}) Died!", entity.getTagOrId());
			}
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
