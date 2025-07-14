package com.krnl32.jupiter.events.scene;

import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.event.Event;

public class EntitySelectedEvent implements Event {
	private final Entity entity;

	public EntitySelectedEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
