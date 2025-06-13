package com.github.krnl32.jupiter.systems;

import com.github.krnl32.jupiter.components.KeyboardMovementComponent;
import com.github.krnl32.jupiter.components.TransformComponent;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.ecs.Registry;
import com.github.krnl32.jupiter.ecs.System;
import com.github.krnl32.jupiter.input.Input;
import com.github.krnl32.jupiter.renderer.Renderer;

public class KeyboardMovementSystem extends System {
	public KeyboardMovementSystem(Registry registry) {
		super(registry);
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity: getRegistry().getEntitiesWith(TransformComponent.class, KeyboardMovementComponent.class)) {
			TransformComponent transform = entity.getComponent(TransformComponent.class);
			KeyboardMovementComponent keyboardMovement = entity.getComponent(KeyboardMovementComponent.class);

			float velocity = keyboardMovement.moveSpeed * dt;
			if (Input.getInstance().isKeyDown(keyboardMovement.upKey))
				transform.translation.y += velocity;

			if (Input.getInstance().isKeyDown(keyboardMovement.downKey))
				transform.translation.y -= velocity;

			if (Input.getInstance().isKeyDown(keyboardMovement.forwardKey))
				transform.translation.z -= velocity;

			if(Input.getInstance().isKeyDown(keyboardMovement.backwardKey))
				transform.translation.z += velocity;

			if(Input.getInstance().isKeyDown(keyboardMovement.leftKey))
				transform.translation.x -= velocity;

			if(Input.getInstance().isKeyDown(keyboardMovement.rightKey))
				transform.translation.x += velocity;
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
