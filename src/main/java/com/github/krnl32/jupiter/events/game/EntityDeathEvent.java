package com.github.krnl32.jupiter.events.game;

import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.event.Event;

public class EntityDeathEvent implements Event {
	private final Entity entity;

	public EntityDeathEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
