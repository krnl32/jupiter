package com.krnl32.jupiter.engine.events.physics;

import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.event.Event;

public class EndCollisionEvent implements Event {
	private final Entity entityA;
	private final Entity entityB;

	public EndCollisionEvent(Entity entityA, Entity entityB) {
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
