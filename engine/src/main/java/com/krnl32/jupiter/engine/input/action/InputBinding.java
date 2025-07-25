package com.krnl32.jupiter.engine.input.action;

import com.krnl32.jupiter.engine.input.InputDeviceSystem;
import com.krnl32.jupiter.engine.input.devices.KeyCode;
import com.krnl32.jupiter.engine.input.devices.MouseCode;

public class InputBinding {
	private final InputType inputType;
	private final KeyCode keyCode;
	private final MouseCode mouseCode;

	public InputBinding(KeyCode keyCode) {
		this.inputType = InputType.KEYBOARD_KEY;
		this.keyCode = keyCode;
		this.mouseCode = null;
	}

	public InputBinding(MouseCode mouseCode) {
		this.inputType = InputType.MOUSE_BUTTON;
		this.keyCode = null;
		this.mouseCode = mouseCode;
	}

	protected boolean isDown() {
		return switch (inputType) {
			case KEYBOARD_KEY -> InputDeviceSystem.getInstance().isKeyDown(keyCode);
			case MOUSE_BUTTON -> InputDeviceSystem.getInstance().isMouseButtonDown(mouseCode);
		};
	}

	protected boolean isPressed() {
		return switch (inputType) {
			case KEYBOARD_KEY -> InputDeviceSystem.getInstance().isKeyPressed(keyCode);
			case MOUSE_BUTTON -> InputDeviceSystem.getInstance().isMouseButtonPressed(mouseCode);
		};
	}

	protected boolean isReleased() {
		return switch (inputType) {
			case KEYBOARD_KEY -> InputDeviceSystem.getInstance().isKeyReleased(keyCode);
			case MOUSE_BUTTON -> InputDeviceSystem.getInstance().isMouseButtonReleased(mouseCode);
		};
	}
}
