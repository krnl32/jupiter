package com.krnl32.jupiter.physics;

import com.krnl32.jupiter.ecs.Entity;
import org.jbox2d.callbacks.ContactFilter;
import org.jbox2d.dynamics.Fixture;

public class DelegatingContactFilter extends ContactFilter {
	private final CollisionRule collisionRule;

	public DelegatingContactFilter(CollisionRule collisionRule) {
		this.collisionRule = collisionRule;
	}

	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		Object aData = fixtureA.getUserData();
		Object bData = fixtureB.getUserData();
		if (!(aData instanceof Entity a) || !(bData instanceof Entity b))
			return true;
		return collisionRule.shouldCollide(a, b);
	}
}
