package com.krnl32.jupiter.physics;

import com.krnl32.jupiter.ecs.Entity;

@FunctionalInterface
public interface CollisionRule {
	boolean shouldCollide(Entity entityA, Entity entityB);
}
