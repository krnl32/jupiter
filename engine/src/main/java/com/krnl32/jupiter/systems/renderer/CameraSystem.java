package com.krnl32.jupiter.systems.renderer;

import com.krnl32.jupiter.components.renderer.CameraComponent;
import com.krnl32.jupiter.components.gameplay.TransformComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.renderer.Renderer;

public class CameraSystem implements System {
	private final Registry registry;

	public CameraSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity: registry.getEntitiesWith(TransformComponent.class, CameraComponent.class)) {
			TransformComponent transform = entity.getComponent(TransformComponent.class);
			CameraComponent camera = entity.getComponent(CameraComponent.class);

			camera.camera.setPosition(transform.translation);
			camera.camera.onUpdate(dt);
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		for (Entity entity: registry.getEntitiesWith(CameraComponent.class)) {
			CameraComponent camera = entity.getComponent(CameraComponent.class);
			if (camera.primary)
				renderer.setActiveCamera(camera.camera);
		}
	}
}
