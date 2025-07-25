package com.krnl32.jupiter.editor.factory.components.effects;

import com.krnl32.jupiter.engine.components.effects.BlinkComponent;
import com.krnl32.jupiter.editor.factory.ComponentFactory;

public class BlinkComponentFactory implements ComponentFactory<BlinkComponent> {
	@Override
	public BlinkComponent create() {
		return new BlinkComponent(0.3f, 0.05f);
	}
}
