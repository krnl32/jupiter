package com.krnl32.jupiter.systems.ui;

import com.krnl32.jupiter.components.ui.UIInputEventComponent;
import com.krnl32.jupiter.components.ui.UIInputStateComponent;
import com.krnl32.jupiter.components.ui.UIRenderComponent;
import com.krnl32.jupiter.components.ui.UITransformComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.entity.EntityDestroyedEvent;
import com.krnl32.jupiter.input.Input;
import com.krnl32.jupiter.input.MouseCode;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.utility.UIUtils;
import org.joml.Vector2f;

public class UIInputSystem implements System {
	private final Registry registry;
	private Entity focusedEntity;

	public UIInputSystem(Registry registry) {
		this.registry = registry;
		this.focusedEntity = null;

		EventBus.getInstance().register(EntityDestroyedEvent.class, event -> {
			if (event.getEntity() == focusedEntity) {
				UIInputStateComponent inputState = focusedEntity.getComponent(UIInputStateComponent.class);
				UIInputEventComponent inputEvent = focusedEntity.getComponent(UIInputEventComponent.class);
				if (inputState != null) {
					inputState.isFocused = false;
					if (inputEvent != null && inputEvent.onBlur != null) {
						inputEvent.onBlur.accept(focusedEntity);
					}
				}
				focusedEntity = null;
			}
		});
	}

	@Override
	public void onUpdate(float dt) {
		Vector2f mousePosition = Input.getInstance().getMouseCursorPosition();

		boolean mouseInUI = false;
		for (Entity entity : registry.getEntitiesWith(UIInputStateComponent.class, UIInputEventComponent.class, UITransformComponent.class)) {
			UIInputStateComponent inputState = entity.getComponent(UIInputStateComponent.class);
			UIInputEventComponent inputEvent = entity.getComponent(UIInputEventComponent.class);
			UITransformComponent transformComponent = entity.getComponent(UITransformComponent.class);

			boolean inside = UIUtils.isMouseOver(mousePosition, transformComponent);
			if (inside) {
				mouseInUI = true;
			}

			// Handle Hovering
			if(inside && !inputState.isHovered) {
				inputState.isHovered = true;
				if (inputEvent.onHoverEnter != null) {
					inputEvent.onHoverEnter.accept(entity);
				}
			} else if(!inside && inputState.isHovered) {
				inputState.isHovered = false;
				if (inputEvent.onHoverExit != null) {
					inputEvent.onHoverExit.accept(entity);
				}
			}

			// Handle Click
			if (inside && Input.getInstance().isMouseButtonDown(MouseCode.BUTTON_LEFT)) {
				inputState.isPressed = true;
			}

			// Handle Release
			if (inputState.isPressed && Input.getInstance().isMouseButtonReleased(MouseCode.BUTTON_LEFT)) {
				inputState.isPressed = false;

				if (inside && inputEvent.onClick != null) {
					inputEvent.onClick.accept(entity);
				}

				// Handle Focus
				if (inside && entity != focusedEntity) {
					// Blur old entity
					if (focusedEntity != null) {
						UIInputStateComponent focusedInputState = focusedEntity.getComponent(UIInputStateComponent.class);
						UIInputEventComponent focusedInputEvent = focusedEntity.getComponent(UIInputEventComponent.class);
						if (focusedInputState != null) {
							focusedInputState.isFocused = false;
							if (focusedInputEvent.onBlur != null) {
								focusedInputEvent.onBlur.accept(focusedEntity);
							}
						}
					}

					// Focus New One
					inputState.isFocused = true;
					focusedEntity = entity;
					if (inputEvent.onFocus != null) {
						inputEvent.onFocus.accept(entity);
					}
				}
			}
		}

		// Handle Click Outside
		if (!mouseInUI && focusedEntity != null && Input.getInstance().isMouseButtonReleased(MouseCode.BUTTON_LEFT)) {
			UIInputStateComponent focusedInputState = focusedEntity.getComponent(UIInputStateComponent.class);
			UIInputEventComponent focusedInputEvent = focusedEntity.getComponent(UIInputEventComponent.class);
			if (focusedInputState != null) {
				focusedInputState.isFocused = false;
				if (focusedInputEvent.onBlur != null) {
					focusedInputEvent.onBlur.accept(focusedEntity);
				}
			}
			focusedEntity = null;
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		for (Entity entity : registry.getEntitiesWith(UIInputStateComponent.class, UIRenderComponent.class)) {
			UIInputStateComponent inputState = entity.getComponent(UIInputStateComponent.class);
			UIRenderComponent renderComponent = entity.getComponent(UIRenderComponent.class);

			if (inputState.isPressed) {
				renderComponent.color.set(0.122f, 0.380f, 0.553f, 1.0f);
			}
			else if (inputState.isHovered) {
				renderComponent.color.set(0.161f, 0.502f, 0.725f, 1.0f);
			}
			else if(inputState.isFocused) {
				renderComponent.color.set(0.392f, 0.710f, 0.965f, 1.0f);
			}
			else {
				renderComponent.color.set(1.0f, 1.0f, 1.0f, 1.0f);
			}
		}
	}
}
