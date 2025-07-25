package com.krnl32.jupiter.engine.systems.renderer;

import com.krnl32.jupiter.engine.components.renderer.CameraComponent;
import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.renderer.SwitchRendererCameraEvent;
import com.krnl32.jupiter.engine.renderer.Renderer;

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

			if (camera.primary)
				EventBus.getInstance().emit(new SwitchRendererCameraEvent(camera.camera));
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
