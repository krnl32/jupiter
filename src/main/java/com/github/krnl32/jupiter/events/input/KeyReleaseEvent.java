package com.github.krnl32.jupiter.events.input;

import com.github.krnl32.jupiter.event.Event;
import com.github.krnl32.jupiter.input.KeyCode;

public class KeyReleaseEvent implements Event {
	private final KeyCode keyCode;

	public KeyReleaseEvent(KeyCode keyCode) {
		this.keyCode = keyCode;
	}

	public KeyCode getKeyCode() {
		return keyCode;
	}
}
