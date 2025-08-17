package com.krnl32.jupiter.engine.physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Physics2D {
	private Vec2 gravity;
	private World world;
	private float physicsTime;
	private float physicsTimeStep;
	private int velocityIterations;
	private int positionIterations;

	public Physics2D(PhysicsSettings settings) {
		this.gravity = new Vec2(settings.getGravity().x(), settings.getGravity().y());
		this.world = new World(gravity);
		this.physicsTime = 0.0f;
		this.physicsTimeStep = 1.0f / 60.0f;
		this.velocityIterations = 8;
		this.positionIterations = 3;
		world.setContactListener(new ContactListener());
	}

	public void onUpdate(float dt) {
		physicsTime += dt;
		while (physicsTime >= physicsTimeStep) {
			world.step(physicsTimeStep, velocityIterations, positionIterations);
			physicsTime -= physicsTimeStep;
		}
	}

	public World getWorld() {
		return world;
	}
}
