package com.github.krnl32.jupiter.input;

import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.event.EventBus;
import com.github.krnl32.jupiter.events.input.KeyPressEvent;
import com.github.krnl32.jupiter.events.input.KeyReleaseEvent;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;

public class Keyboard {
	private final int MAX_KEYBOARD_KEYS = GLFW_KEY_LAST;
	private final boolean[] keyState = new boolean[MAX_KEYBOARD_KEYS];
	private final boolean[] keyPressed = new boolean[MAX_KEYBOARD_KEYS];
	private final boolean[] keyReleased = new boolean[MAX_KEYBOARD_KEYS];

	public Keyboard() {
		EventBus.getInstance().register(KeyPressEvent.class, event -> {
			int keyCode = event.getKeyCode().getCode();
			if (keyCode < 0 || keyCode > MAX_KEYBOARD_KEYS)
				Logger.error("KeyPress keyCode out of Bounds!", keyCode);

			if (!keyState[keyCode])
				keyPressed[keyCode] = true;
			keyState[keyCode] = true;
		});

		EventBus.getInstance().register(KeyReleaseEvent.class, event -> {
			int keyCode = event.getKeyCode().getCode();
			if (keyCode < 0 || keyCode > MAX_KEYBOARD_KEYS)
				Logger.error("KeyRelease keyCode out of Bounds!", keyCode);

			keyReleased[keyCode] = true;
			keyState[keyCode] = false;
		});
	}

	public void reset() {
		Arrays.fill(keyPressed, false);
		Arrays.fill(keyReleased, false);
	}

	public boolean isKeyPressed(KeyCode keyCode) {
		if (keyCode.getCode() < 0 || keyCode.getCode() > MAX_KEYBOARD_KEYS)
			Logger.error("isKeyPressed({}) keyCode out of Bounds!", keyCode.getCode());
		return keyPressed[keyCode.getCode()];
	}

	public boolean isKeyReleased(KeyCode keyCode) {
		if (keyCode.getCode() < 0 || keyCode.getCode() > MAX_KEYBOARD_KEYS)
			Logger.error("isKeyReleased({}) keyCode out of Bounds!", keyCode.getCode());
		return keyReleased[keyCode.getCode()];
	}

	public boolean isKeyUp(KeyCode keyCode) {
		if (keyCode.getCode() < 0 || keyCode.getCode() > MAX_KEYBOARD_KEYS)
			Logger.error("isKeyUp({}) keyCode out of Bounds!", keyCode.getCode());
		return !keyState[keyCode.getCode()];
	}

	public boolean isKeyDown(KeyCode keyCode) {
		if (keyCode.getCode() < 0 || keyCode.getCode() > MAX_KEYBOARD_KEYS)
			Logger.error("isKeyDown({}) keyCode out of Bounds!", keyCode.getCode());
		return keyState[keyCode.getCode()];
	}
}
