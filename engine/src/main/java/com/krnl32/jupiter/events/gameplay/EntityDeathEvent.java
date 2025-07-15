package com.krnl32.jupiter.events.gameplay;

import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.event.Event;

public class EntityDeathEvent implements Event {
	private final Entity entity;

	public EntityDeathEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
