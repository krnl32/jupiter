package com.github.krnl32.jupiter.events.input;

import com.github.krnl32.jupiter.event.Event;

public class KeyDownEvent implements Event {
	private final int keyCode;

	public KeyDownEvent(int keyCode) {
		this.keyCode = keyCode;
	}

	public int getKeyCode() {
		return keyCode;
	}
}
