package com.krnl32.jupiter.engine.components.ui;

import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.ecs.Entity;

import java.util.function.Consumer;

public class UIInputEventComponent implements Component {
	public Consumer<Entity> onClick;
	public Consumer<Entity> onHoverEnter;
	public Consumer<Entity> onHoverExit;
	public Consumer<Entity> onFocus;
	public Consumer<Entity> onBlur;

	public UIInputEventComponent(Consumer<Entity> onClick, Consumer<Entity> onHoverEnter, Consumer<Entity> onHoverExit, Consumer<Entity> onFocus, Consumer<Entity> onBlur) {
		this.onClick = onClick;
		this.onHoverEnter = onHoverEnter;
		this.onHoverExit = onHoverExit;
		this.onFocus = onFocus;
		this.onBlur = onBlur;
	}
}
