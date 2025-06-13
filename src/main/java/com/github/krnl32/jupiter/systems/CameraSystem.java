package com.github.krnl32.jupiter.systems;

import com.github.krnl32.jupiter.components.CameraComponent;
import com.github.krnl32.jupiter.components.TransformComponent;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.ecs.Registry;
import com.github.krnl32.jupiter.ecs.System;
import com.github.krnl32.jupiter.renderer.Renderer;

public class CameraSystem extends System {
	public CameraSystem(Registry registry) {
		super(registry);
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity: getRegistry().getEntitiesWith(TransformComponent.class, CameraComponent.class)) {
			TransformComponent transform = entity.getComponent(TransformComponent.class);
			CameraComponent camera = entity.getComponent(CameraComponent.class);

			camera.camera.setPosition(transform.translation);
			camera.camera.onUpdate(dt);
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		for (Entity entity: getRegistry().getEntitiesWith(CameraComponent.class)) {
			CameraComponent camera = entity.getComponent(CameraComponent.class);
			if (camera.primary)
				renderer.setActiveCamera(camera.camera);
		}
	}
}
