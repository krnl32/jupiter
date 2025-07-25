package com.krnl32.jupiter.factory.components.gameplay;

import com.krnl32.jupiter.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.factory.ComponentFactory;

public class ScriptComponentFactory implements ComponentFactory<ScriptComponent> {
	@Override
	public ScriptComponent create() {
		return new ScriptComponent();
	}
}
