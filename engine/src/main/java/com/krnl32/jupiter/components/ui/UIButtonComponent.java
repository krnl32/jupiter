package com.krnl32.jupiter.components.ui;

import com.krnl32.jupiter.ecs.Component;

public class UIButtonComponent implements Component {
	public boolean isHovered;
	public boolean isPressed;
	public Runnable onClick;

	public UIButtonComponent(Runnable onClick) {
		this.isHovered = false;
		this.isPressed = false;
		this.onClick = onClick;
	}

	public UIButtonComponent(boolean isHovered, boolean isPressed, Runnable onClick) {
		this.isHovered = isHovered;
		this.isPressed = isPressed;
		this.onClick = onClick;
	}
}
