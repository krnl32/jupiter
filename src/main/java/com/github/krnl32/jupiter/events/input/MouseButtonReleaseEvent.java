package com.github.krnl32.jupiter.events.input;

import com.github.krnl32.jupiter.event.Event;
import com.github.krnl32.jupiter.input.MouseCode;

public class MouseButtonReleaseEvent implements Event {
	private final MouseCode mouseCode;

	public MouseButtonReleaseEvent(MouseCode mouseCode) {
		this.mouseCode = mouseCode;
	}

	public MouseCode getMouseCode() {
		return mouseCode;
	}
}
