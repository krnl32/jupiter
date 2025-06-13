package com.github.krnl32.jupiter.systems;

import com.github.krnl32.jupiter.components.RigidBodyComponent;
import com.github.krnl32.jupiter.components.TransformComponent;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.ecs.Registry;
import com.github.krnl32.jupiter.ecs.System;
import com.github.krnl32.jupiter.renderer.Renderer;

public class MovementSystem extends System {
	public MovementSystem(Registry registry) {
		super(registry);
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity: getRegistry().getEntitiesWith(TransformComponent.class, RigidBodyComponent.class)) {
			TransformComponent transform = entity.getComponent(TransformComponent.class);
			RigidBodyComponent rigidBody = entity.getComponent(RigidBodyComponent.class);

			transform.translation.add(rigidBody.velocity.x * dt, rigidBody.velocity.y * dt, rigidBody.velocity.z * dt);
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
