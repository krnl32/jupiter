package com.krnl32.jupiter.events.physics;

import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.event.Event;

public class CollisionEvent implements Event {
	private final Entity entityA;
	private final Entity entityB;

	public CollisionEvent(Entity entityA, Entity entityB) {
		this.entityA = entityA;
		this.entityB = entityB;
	}

	public Entity getEntityA() {
		return entityA;
	}

	public Entity getEntityB() {
		return entityB;
	}
}
