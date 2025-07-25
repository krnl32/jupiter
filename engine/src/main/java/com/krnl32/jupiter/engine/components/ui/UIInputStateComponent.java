package com.krnl32.jupiter.engine.components.ui;

import com.krnl32.jupiter.engine.ecs.Component;

public class UIInputStateComponent implements Component {
	public boolean isHovered;
	public boolean isPressed;
	public boolean isFocused;

	public UIInputStateComponent() {
		this.isHovered = false;
		this.isPressed = false;
		this.isFocused = false;
	}

	public UIInputStateComponent(boolean isHovered, boolean isPressed, boolean isFocused) {
		this.isHovered = isHovered;
		this.isPressed = isPressed;
		this.isFocused = isFocused;
	}
}
