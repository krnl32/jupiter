package com.github.krnl32.jupiter.systems;

import com.github.krnl32.jupiter.components.KeyboardControlComponent;
import com.github.krnl32.jupiter.components.RigidBodyComponent;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.ecs.Registry;
import com.github.krnl32.jupiter.ecs.System;
import com.github.krnl32.jupiter.input.Input;
import com.github.krnl32.jupiter.renderer.Renderer;

public class KeyboardControlSystem implements System {
	private final Registry registry;

	public KeyboardControlSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity: registry.getEntitiesWith(KeyboardControlComponent.class, RigidBodyComponent.class)) {
			KeyboardControlComponent keyboardControl = entity.getComponent(KeyboardControlComponent.class);
			RigidBodyComponent rigidBody = entity.getComponent(RigidBodyComponent.class);

			rigidBody.velocity.set(0, 0, 0);
			rigidBody.angularVelocity.set(0, 0, 0);

			if (Input.getInstance().isKeyDown(keyboardControl.upKey))
				rigidBody.velocity.y += keyboardControl.moveSpeed;

			if (Input.getInstance().isKeyDown(keyboardControl.downKey))
				rigidBody.velocity.y -= keyboardControl.moveSpeed;

			if (Input.getInstance().isKeyDown(keyboardControl.forwardKey))
				rigidBody.velocity.z -= keyboardControl.moveSpeed;

			if (Input.getInstance().isKeyDown(keyboardControl.backwardKey))
				rigidBody.velocity.z += keyboardControl.moveSpeed;

			if (Input.getInstance().isKeyDown(keyboardControl.leftKey))
				rigidBody.velocity.x -= keyboardControl.moveSpeed;

			if (Input.getInstance().isKeyDown(keyboardControl.rightKey))
				rigidBody.velocity.x += keyboardControl.moveSpeed;

			if (Input.getInstance().isKeyDown(keyboardControl.rotateLeftKey))
				rigidBody.angularVelocity.z += keyboardControl.rotationSpeed;

			if (Input.getInstance().isKeyDown(keyboardControl.rotateRightKey))
				rigidBody.angularVelocity.z -= keyboardControl.rotationSpeed;
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
