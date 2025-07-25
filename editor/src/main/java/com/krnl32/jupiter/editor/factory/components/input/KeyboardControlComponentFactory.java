package com.krnl32.jupiter.editor.factory.components.input;

import com.krnl32.jupiter.editor.factory.ComponentFactory;
import com.krnl32.jupiter.engine.components.input.KeyboardControlComponent;
import com.krnl32.jupiter.engine.input.devices.KeyCode;

public class KeyboardControlComponentFactory implements ComponentFactory<KeyboardControlComponent> {
	@Override
	public KeyboardControlComponent create() {
		return new KeyboardControlComponent(KeyCode.W, KeyCode.S, KeyCode.UNKNOWN, KeyCode.UNKNOWN, KeyCode.A, KeyCode.D, KeyCode.Q, KeyCode.E, KeyCode.SPACE, KeyCode.LEFT_SHIFT);
	}
}
