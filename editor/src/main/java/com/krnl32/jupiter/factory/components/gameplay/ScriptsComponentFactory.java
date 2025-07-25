package com.krnl32.jupiter.factory.components.gameplay;

import com.krnl32.jupiter.components.gameplay.ScriptsComponent;
import com.krnl32.jupiter.factory.ComponentFactory;

public class ScriptsComponentFactory implements ComponentFactory<ScriptsComponent> {
	@Override
	public ScriptsComponent create() {
		return new ScriptsComponent();
	}
}
