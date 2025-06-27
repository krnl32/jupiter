package com.krnl32.jupiter.events.input;

import com.krnl32.jupiter.event.Event;
import com.krnl32.jupiter.input.KeyCode;

public class KeyDownEvent implements Event {
	private final KeyCode keyCode;

	public KeyDownEvent(KeyCode keyCode) {
		this.keyCode = keyCode;
	}

	public KeyCode getKeyCode() {
		return keyCode;
	}
}
