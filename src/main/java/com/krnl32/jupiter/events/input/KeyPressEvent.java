package com.krnl32.jupiter.events.input;

import com.krnl32.jupiter.event.Event;
import com.krnl32.jupiter.input.KeyCode;

public class KeyPressEvent implements Event {
	private final KeyCode keyCode;

	public KeyPressEvent(KeyCode keyCode) {
		this.keyCode = keyCode;
	}

	public KeyCode getKeyCode() {
		return keyCode;
	}
}
