package com.krnl32.jupiter.engine.events.gameplay;

import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.event.Event;

public class EntityDeathEvent implements Event {
	private final Entity entity;

	public EntityDeathEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
