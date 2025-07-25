package com.krnl32.jupiter.engine.events.input;

import com.krnl32.jupiter.engine.event.Event;
import com.krnl32.jupiter.engine.input.devices.KeyCode;

public class KeyDownEvent implements Event {
	private final KeyCode keyCode;

	public KeyDownEvent(KeyCode keyCode) {
		this.keyCode = keyCode;
	}

	public KeyCode getKeyCode() {
		return keyCode;
	}
}
