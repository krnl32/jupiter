package com.krnl32.jupiter.engine.systems.input;

import com.krnl32.jupiter.engine.components.gameplay.MovementIntentComponent;
import com.krnl32.jupiter.engine.components.input.KeyboardControlComponent;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.input.InputDeviceSystem;
import com.krnl32.jupiter.engine.renderer.Renderer;

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

			if (InputDeviceSystem.getInstance().isKeyDown(keyboardControl.upKey))
				movementIntent.translation.y += 1;

			if (InputDeviceSystem.getInstance().isKeyDown(keyboardControl.downKey))
				movementIntent.translation.y -= 1;

			if (InputDeviceSystem.getInstance().isKeyDown(keyboardControl.leftKey))
				movementIntent.translation.x -= 1;

			if (InputDeviceSystem.getInstance().isKeyDown(keyboardControl.rightKey))
				movementIntent.translation.x += 1;

			if (movementIntent.translation.lengthSquared() > 0.001f)
				movementIntent.translation.normalize();

			if (InputDeviceSystem.getInstance().isKeyDown(keyboardControl.rotateLeftKey))
				movementIntent.rotation.z += 1;

			if (InputDeviceSystem.getInstance().isKeyDown(keyboardControl.rotateRightKey))
				movementIntent.rotation.z -= 1;

			if (InputDeviceSystem.getInstance().isKeyDown(keyboardControl.jumpKey))
				movementIntent.jump = true;

			if (InputDeviceSystem.getInstance().isKeyDown(keyboardControl.sprintKey))
				movementIntent.sprint = true;
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
