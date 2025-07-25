package com.krnl32.jupiter.engine.events.input;

import com.krnl32.jupiter.engine.event.Event;
import com.krnl32.jupiter.engine.input.devices.KeyCode;

public class KeyPressEvent implements Event {
	private final KeyCode keyCode;

	public KeyPressEvent(KeyCode keyCode) {
		this.keyCode = keyCode;
	}

	public KeyCode getKeyCode() {
		return keyCode;
	}
}
