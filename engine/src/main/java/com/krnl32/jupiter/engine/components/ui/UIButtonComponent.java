package com.krnl32.jupiter.engine.components.ui;

import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.ecs.Entity;

import java.util.function.Consumer;

public class UIButtonComponent implements Component {
	public boolean isHovered;
	public boolean isPressed;
	public Consumer<Entity> onClick;

	public UIButtonComponent(Consumer<Entity> onClick) {
		this.isHovered = false;
		this.isPressed = false;
		this.onClick = onClick;
	}

	public UIButtonComponent(boolean isHovered, boolean isPressed, Consumer<Entity> onClick) {
		this.isHovered = isHovered;
		this.isPressed = isPressed;
		this.onClick = onClick;
	}
}
