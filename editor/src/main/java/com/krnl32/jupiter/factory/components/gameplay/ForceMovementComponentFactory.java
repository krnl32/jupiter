package com.krnl32.jupiter.factory.components.gameplay;

import com.krnl32.jupiter.components.gameplay.ForceMovementComponent;
import com.krnl32.jupiter.factory.ComponentFactory;

public class ForceMovementComponentFactory implements ComponentFactory<ForceMovementComponent> {
	@Override
	public ForceMovementComponent create() {
		return new ForceMovementComponent();
	}
}
