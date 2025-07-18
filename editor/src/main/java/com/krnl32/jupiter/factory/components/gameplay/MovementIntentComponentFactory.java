package com.krnl32.jupiter.factory.components.gameplay;

import com.krnl32.jupiter.components.gameplay.MovementIntentComponent;
import com.krnl32.jupiter.factory.ComponentFactory;

public class MovementIntentComponentFactory implements ComponentFactory<MovementIntentComponent> {
	@Override
	public MovementIntentComponent create() {
		return new MovementIntentComponent();
	}
}
