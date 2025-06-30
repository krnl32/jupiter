package com.krnl32.jupiter.components.ui;

import com.krnl32.jupiter.ecs.Component;
import com.krnl32.jupiter.ecs.Entity;

import java.util.function.Consumer;

public class UIFocusComponent implements Component {
	public boolean isFocused;
	public Consumer<Entity> onFocus;
	public Consumer<Entity> onBlur;

	public UIFocusComponent(Consumer<Entity> onFocus, Consumer<Entity> onBlur) {
		this.isFocused = false;
		this.onFocus = onFocus;
		this.onBlur = onBlur;
	}

	public UIFocusComponent(boolean isFocused, Consumer<Entity> onFocus, Consumer<Entity> onBlur) {
		this.isFocused = isFocused;
		this.onFocus = onFocus;
		this.onBlur = onBlur;
	}
}
