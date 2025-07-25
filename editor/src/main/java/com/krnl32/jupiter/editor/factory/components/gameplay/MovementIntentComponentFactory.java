package com.krnl32.jupiter.editor.factory.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.MovementIntentComponent;
import com.krnl32.jupiter.editor.factory.ComponentFactory;

public class MovementIntentComponentFactory implements ComponentFactory<MovementIntentComponent> {
	@Override
	public MovementIntentComponent create() {
		return new MovementIntentComponent();
	}
}
