package com.krnl32.jupiter.systems.ui;

import com.krnl32.jupiter.components.ui.UIButtonComponent;
import com.krnl32.jupiter.components.ui.UIRenderComponent;
import com.krnl32.jupiter.components.ui.UITransformComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.input.Input;
import com.krnl32.jupiter.input.MouseCode;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.utility.UIUtils;
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

			boolean inside = UIUtils.isMouseOver(mousePosition, UIUtils.getWorldPosition(entity), transformComponent.scale);

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
					buttonComponent.onClick.accept(entity);
				}
			}
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		for (Entity entity : registry.getEntitiesWith(UIButtonComponent.class, UIRenderComponent.class)) {
			UIButtonComponent buttonComponent = entity.getComponent(UIButtonComponent.class);
			UIRenderComponent renderComponent = entity.getComponent(UIRenderComponent.class);

			if (buttonComponent.isPressed) {
				renderComponent.color.set(0.122f, 0.380f, 0.553f, 1.0f);
			}
			else if (buttonComponent.isHovered) {
				renderComponent.color.set(0.161f, 0.502f, 0.725f, 1.0f);
			}
			else {
				renderComponent.color.set(1.0f, 1.0f, 1.0f, 1.0f);
			}
		}
	}
}
