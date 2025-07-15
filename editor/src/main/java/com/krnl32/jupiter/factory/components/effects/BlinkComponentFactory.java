package com.krnl32.jupiter.factory.components.effects;

import com.krnl32.jupiter.components.effects.BlinkComponent;
import com.krnl32.jupiter.factory.ComponentFactory;

public class BlinkComponentFactory implements ComponentFactory<BlinkComponent> {
	@Override
	public BlinkComponent create() {
		return new BlinkComponent(0.3f, 0.05f);
	}
}
