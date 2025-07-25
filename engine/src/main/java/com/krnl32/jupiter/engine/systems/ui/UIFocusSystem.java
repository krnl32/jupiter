package com.krnl32.jupiter.engine.systems.ui;

import com.krnl32.jupiter.engine.components.ui.UIFocusComponent;
import com.krnl32.jupiter.engine.components.ui.UIRenderComponent;
import com.krnl32.jupiter.engine.components.ui.UITransformComponent;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.entity.EntityDestroyedEvent;
import com.krnl32.jupiter.engine.input.InputDeviceSystem;
import com.krnl32.jupiter.engine.input.devices.MouseCode;
import com.krnl32.jupiter.engine.renderer.Renderer;
import com.krnl32.jupiter.engine.utility.UIUtils;
import org.joml.Vector2f;

public class UIFocusSystem implements System {
	private final Registry registry;
	private Entity focusedEntity;

	public UIFocusSystem(Registry registry) {
		this.registry = registry;
		this.focusedEntity = null;

		EventBus.getInstance().register(EntityDestroyedEvent.class, event -> {
			if (event.getEntity() == focusedEntity) {
				UIFocusComponent focus = focusedEntity.getComponent(UIFocusComponent.class);
				if (focus != null) {
					focus.isFocused = false;
					if (focus.onBlur != null) {
						focus.onBlur.accept(focusedEntity);
					}
				}
				this.focusedEntity = null;
			}
		});
	}

	@Override
	public void onUpdate(float dt) {
		Vector2f mousePosition = InputDeviceSystem.getInstance().getMouseCursorPosition();

		boolean mouseInUI = false;
		for (Entity entity : registry.getEntitiesWith(UIFocusComponent.class, UITransformComponent.class)) {
			UIFocusComponent focus = entity.getComponent(UIFocusComponent.class);

			boolean inside = UIUtils.isMouseOver(mousePosition, entity);
			if (inside) {
				mouseInUI = true;
			}

			if (InputDeviceSystem.getInstance().isMouseButtonReleased(MouseCode.BUTTON_LEFT)) {
				if (inside && entity != focusedEntity) {
					// Blur old entity
					if (focusedEntity != null) {
						UIFocusComponent focusedFocus = focusedEntity.getComponent(UIFocusComponent.class);
						if (focusedFocus != null) {
							focusedFocus.isFocused = false;
							if (focusedFocus.onBlur != null) {
								focusedFocus.onBlur.accept(focusedEntity);
							}
						}
					}

					// Focus New One
					focus.isFocused = true;
					focusedEntity = entity;
					if (focus.onFocus != null) {
						focus.onFocus.accept(entity);
					}
				}
			}
		}

		// Handle Click Outside
		if (!mouseInUI && focusedEntity != null && InputDeviceSystem.getInstance().isMouseButtonReleased(MouseCode.BUTTON_LEFT)) {
			UIFocusComponent focusedFocus = focusedEntity.getComponent(UIFocusComponent.class);
			if (focusedFocus != null) {
				focusedFocus.isFocused = false;
				if (focusedFocus.onBlur != null) {
					focusedFocus.onBlur.accept(focusedEntity);
				}
			}
			focusedEntity = null;
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		for (Entity entity : registry.getEntitiesWith(UIFocusComponent.class, UIRenderComponent.class)) {
			UIFocusComponent focus = entity.getComponent(UIFocusComponent.class);
			UIRenderComponent renderComponent = entity.getComponent(UIRenderComponent.class);

			if(focus.isFocused) {
				renderComponent.color.set(0.392f, 0.710f, 0.965f, 1.0f);
			}
			else {
				renderComponent.color.set(1.0f, 1.0f, 1.0f, 1.0f);
			}
		}
	}
}
