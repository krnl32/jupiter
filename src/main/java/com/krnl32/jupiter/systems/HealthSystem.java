package com.krnl32.jupiter.systems;

import com.krnl32.jupiter.components.DestroyComponent;
import com.krnl32.jupiter.components.HealthComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.game.EntityDeathEvent;
import com.krnl32.jupiter.renderer.Renderer;

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
