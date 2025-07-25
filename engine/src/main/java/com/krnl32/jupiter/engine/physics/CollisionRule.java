package com.krnl32.jupiter.engine.physics;

import com.krnl32.jupiter.engine.ecs.Entity;

@FunctionalInterface
public interface CollisionRule {
	boolean shouldCollide(Entity entityA, Entity entityB);
}
