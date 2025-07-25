package com.krnl32.jupiter.editor.factory.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.ForceMovementComponent;
import com.krnl32.jupiter.editor.factory.ComponentFactory;

public class ForceMovementComponentFactory implements ComponentFactory<ForceMovementComponent> {
	@Override
	public ForceMovementComponent create() {
		return new ForceMovementComponent();
	}
}
