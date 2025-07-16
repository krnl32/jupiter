package com.krnl32.jupiter.events.input;

import com.krnl32.jupiter.event.Event;
import com.krnl32.jupiter.input.devices.KeyCode;

public class KeyReleaseEvent implements Event {
	private final KeyCode keyCode;

	public KeyReleaseEvent(KeyCode keyCode) {
		this.keyCode = keyCode;
	}

	public KeyCode getKeyCode() {
		return keyCode;
	}
}
