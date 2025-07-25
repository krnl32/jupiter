package com.krnl32.jupiter.editor.events.scene;

import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.event.Event;

public class EntitySelectedEvent implements Event {
	private final Entity entity;

	public EntitySelectedEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
