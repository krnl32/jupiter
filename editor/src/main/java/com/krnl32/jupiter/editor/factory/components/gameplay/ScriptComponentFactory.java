package com.krnl32.jupiter.editor.factory.components.gameplay;

import com.krnl32.jupiter.editor.factory.ComponentFactory;
import com.krnl32.jupiter.engine.components.gameplay.ScriptComponent;

public class ScriptComponentFactory implements ComponentFactory<ScriptComponent> {
	@Override
	public ScriptComponent create() {
		return new ScriptComponent();
	}
}
