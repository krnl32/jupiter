package com.krnl32.jupiter.systems.input;

import com.krnl32.jupiter.components.input.KeyboardControlComponent;
import com.krnl32.jupiter.components.gameplay.MovementIntentComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.input.Input;
import com.krnl32.jupiter.renderer.Renderer;

public class KeyboardControlSystem implements System {
	private final Registry registry;

	public KeyboardControlSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity: registry.getEntitiesWith(KeyboardControlComponent.class, MovementIntentComponent.class)) {
			KeyboardControlComponent keyboardControl = entity.getComponent(KeyboardControlComponent.class);
			MovementIntentComponent movementIntent = entity.getComponent(MovementIntentComponent.class);

			movementIntent.translation.set(0.0f, 0.0f, 0.0f);
			movementIntent.rotation.set(0.0f, 0.0f, 0.0f);
			movementIntent.jump = false;
			movementIntent.sprint = false;

			if (Input.getInstance().isKeyDown(keyboardControl.upKey))
				movementIntent.translation.y += 1;

			if (Input.getInstance().isKeyDown(keyboardControl.downKey))
				movementIntent.translation.y -= 1;

			if (Input.getInstance().isKeyDown(keyboardControl.leftKey))
				movementIntent.translation.x -= 1;

			if (Input.getInstance().isKeyDown(keyboardControl.rightKey))
				movementIntent.translation.x += 1;

			if (movementIntent.translation.lengthSquared() > 0.001f)
				movementIntent.translation.normalize();

			if (Input.getInstance().isKeyDown(keyboardControl.rotateLeftKey))
				movementIntent.rotation.z += 1;

			if (Input.getInstance().isKeyDown(keyboardControl.rotateRightKey))
				movementIntent.rotation.z -= 1;

			if (Input.getInstance().isKeyDown(keyboardControl.jumpKey))
				movementIntent.jump = true;

			if (Input.getInstance().isKeyDown(keyboardControl.sprintKey))
				movementIntent.sprint = true;
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
