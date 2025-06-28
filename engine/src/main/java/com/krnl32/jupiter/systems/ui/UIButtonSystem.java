package com.krnl32.jupiter.systems.ui;

import com.krnl32.jupiter.components.ui.UIButtonComponent;
import com.krnl32.jupiter.components.ui.UITransformComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.input.Input;
import com.krnl32.jupiter.input.MouseCode;
import com.krnl32.jupiter.renderer.Renderer;
import org.joml.Vector2f;

public class UIButtonSystem implements System {
	private final Registry registry;

	public UIButtonSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		Vector2f mousePosition = Input.getInstance().getMouseCursorPosition();

		for (Entity entity : registry.getEntitiesWith(UITransformComponent.class, UIButtonComponent.class)) {
			UITransformComponent transformComponent = entity.getComponent(UITransformComponent.class);
			UIButtonComponent buttonComponent = entity.getComponent(UIButtonComponent.class);

			boolean inside = isMouseInside(mousePosition, transformComponent);

			// Handle Hovering
			if(inside && !buttonComponent.isHovered) {
				buttonComponent.isHovered = true;
			} else if(!inside && buttonComponent.isHovered) {
				buttonComponent.isHovered = false;
			}

			// Handle Click
			if (inside && Input.getInstance().isMouseButtonDown(MouseCode.BUTTON_LEFT)) {
				buttonComponent.isPressed = true;
			}

			if (buttonComponent.isPressed && Input.getInstance().isMouseButtonReleased(MouseCode.BUTTON_LEFT)) {
				buttonComponent.isPressed = false;
				if (inside && buttonComponent.onClick != null) {
					buttonComponent.onClick.run();
				}
			}
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}

	private boolean isMouseInside(Vector2f mousePosition, UITransformComponent transformComponent) {
		return (
			mousePosition.x >= transformComponent.translation.x &&
				mousePosition.y >= transformComponent.translation.y &&
				mousePosition.x <= transformComponent.translation.x + transformComponent.scale.x &&
				mousePosition.y <= transformComponent.translation.y + transformComponent.scale.y
		);
	}
}
