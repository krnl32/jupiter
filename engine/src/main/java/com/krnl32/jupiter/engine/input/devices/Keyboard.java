package com.krnl32.jupiter.engine.input.devices;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.input.KeyPressEvent;
import com.krnl32.jupiter.engine.events.input.KeyReleaseEvent;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;

public class Keyboard {
	private final int MAX_KEYBOARD_KEYS = GLFW_KEY_LAST;
	private final boolean[] keyState = new boolean[MAX_KEYBOARD_KEYS];
	private final boolean[] keyPressed = new boolean[MAX_KEYBOARD_KEYS];
	private final boolean[] keyReleased = new boolean[MAX_KEYBOARD_KEYS];

	public Keyboard() {
		EventBus.getInstance().register(KeyPressEvent.class, this::onKeyPressEvent);
		EventBus.getInstance().register(KeyReleaseEvent.class, this::onKeyReleaseEvent);
	}

	public void reset() {
		Arrays.fill(keyPressed, false);
		Arrays.fill(keyReleased, false);
	}

	public boolean isKeyPressed(KeyCode keyCode) {
		if (keyCode.getCode() < 0 || keyCode.getCode() > MAX_KEYBOARD_KEYS) {
			Logger.error("isKeyPressed({}) keyCode out of Bounds!", keyCode.getCode());
		}

		return keyPressed[keyCode.getCode()];
	}

	public boolean isKeyReleased(KeyCode keyCode) {
		if (keyCode.getCode() < 0 || keyCode.getCode() > MAX_KEYBOARD_KEYS) {
			Logger.error("isKeyReleased({}) keyCode out of Bounds!", keyCode.getCode());
		}

		return keyReleased[keyCode.getCode()];
	}

	public boolean isKeyUp(KeyCode keyCode) {
		if (keyCode.getCode() < 0 || keyCode.getCode() > MAX_KEYBOARD_KEYS) {
			Logger.error("isKeyUp({}) keyCode out of Bounds!", keyCode.getCode());
		}

		return !keyState[keyCode.getCode()];
	}

	public boolean isKeyDown(KeyCode keyCode) {
		if (keyCode.getCode() < 0 || keyCode.getCode() > MAX_KEYBOARD_KEYS) {
			Logger.error("isKeyDown({}) keyCode out of Bounds!", keyCode.getCode());
		}

		return keyState[keyCode.getCode()];
	}

	private void onKeyPressEvent(KeyPressEvent event) {
		int keyCode = event.getKeyCode().getCode();

		if (keyCode < 0 || keyCode > MAX_KEYBOARD_KEYS) {
			Logger.error("KeyPress keyCode out of Bounds!", keyCode);
		}

		if (!keyState[keyCode]) {
			keyPressed[keyCode] = true;
		}

		keyState[keyCode] = true;
	}

	private void onKeyReleaseEvent(KeyReleaseEvent event) {
		int keyCode = event.getKeyCode().getCode();

		if (keyCode < 0 || keyCode > MAX_KEYBOARD_KEYS) {
			Logger.error("KeyRelease keyCode out of Bounds!", keyCode);
		}

		keyReleased[keyCode] = true;
		keyState[keyCode] = false;
	}
}
