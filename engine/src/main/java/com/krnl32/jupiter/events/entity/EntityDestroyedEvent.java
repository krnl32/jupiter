package com.krnl32.jupiter.events.entity;

import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.event.Event;

public class EntityDestroyedEvent implements Event {
	private final Entity entity;

	public EntityDestroyedEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
