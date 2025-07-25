package com.krnl32.jupiter.engine.systems.ui;

import com.krnl32.jupiter.engine.components.ui.UIScrollComponent;
import com.krnl32.jupiter.engine.components.ui.UITransformComponent;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.input.InputDeviceSystem;
import com.krnl32.jupiter.engine.renderer.Renderer;
import com.krnl32.jupiter.engine.utility.UIUtils;
import org.joml.Vector2f;

public class UIScrollSystem implements System {
	private final Registry registry;

	public UIScrollSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		Vector2f mousePosition = InputDeviceSystem.getInstance().getMouseCursorPosition();

		for (Entity entity : registry.getEntitiesWith(UIScrollComponent.class, UITransformComponent.class)) {
			UIScrollComponent scroll = entity.getComponent(UIScrollComponent.class);
			UITransformComponent transform = entity.getComponent(UITransformComponent.class);

			if (!UIUtils.isMouseOver(mousePosition, UIUtils.getWorldPosition(entity), transform.scale))
				continue;

			if (scroll.scrollX) {
				scroll.offset.x -= InputDeviceSystem.getInstance().getMouseScrollOffset().y * 10.0f;
				scroll.offset.x = Math.max(0.0f, scroll.offset.x);
			}

			if (scroll.scrollY) {
				scroll.offset.y -= InputDeviceSystem.getInstance().getMouseScrollOffset().y * 10.0f;
				scroll.offset.y = Math.max(0.0f, scroll.offset.y);
			}
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
