package com.krnl32.jupiter.engine.input;

import com.krnl32.jupiter.engine.input.devices.KeyCode;
import com.krnl32.jupiter.engine.input.devices.Keyboard;
import com.krnl32.jupiter.engine.input.devices.Mouse;
import com.krnl32.jupiter.engine.input.devices.MouseCode;
import org.joml.Vector2f;

public class InputDeviceSystem {
	private static InputDeviceSystem instance;
	private final Keyboard keyboard = new Keyboard();
	private final Mouse mouse = new Mouse();

	private InputDeviceSystem() {
	}

	public static InputDeviceSystem getInstance() {
		if (instance == null)
			instance = new InputDeviceSystem();
		return instance;
	}

	public void reset() {
		keyboard.reset();
		mouse.reset();
	}

	public boolean isKeyPressed(KeyCode keyCode) {
		return keyboard.isKeyPressed(keyCode);
	}

	public boolean isKeyReleased(KeyCode keyCode) {
		return keyboard.isKeyReleased(keyCode);
	}

	public boolean isKeyUp(KeyCode keyCode) {
		return keyboard.isKeyUp(keyCode);
	}

	public boolean isKeyDown(KeyCode keyCode) {
		return keyboard.isKeyDown(keyCode);
	}

	public boolean isMouseButtonPressed(MouseCode mouseCode) {
		return mouse.isButtonPressed(mouseCode);
	}

	public boolean isMouseButtonReleased(MouseCode mouseCode) {
		return mouse.isButtonReleased(mouseCode);
	}

	public boolean isMouseButtonUp(MouseCode mouseCode) {
		return mouse.isButtonUp(mouseCode);
	}

	public boolean isMouseButtonDown(MouseCode mouseCode) {
		return mouse.isButtonDown(mouseCode);
	}

	public Vector2f getMouseCursorPosition() {
		return mouse.getCursorPosition();
	}

	public Vector2f getMouseCursorDelta() {
		return mouse.getCursorDelta();
	}

	public Vector2f getMouseScrollOffset() {
		return mouse.getScrollOffset();
	}

	public boolean isMouseScrollingUp() {
		return mouse.isScrollingUp();
	}

	public boolean isMouseScrollingDown() {
		return mouse.isScrollingDown();
	}
}
