package com.krnl32.jupiter.events.input;

import com.krnl32.jupiter.event.Event;
import com.krnl32.jupiter.input.MouseCode;

public class MouseButtonPressEvent implements Event {
	private final MouseCode mouseCode;

	public MouseButtonPressEvent(MouseCode mouseCode) {
		this.mouseCode = mouseCode;
	}

	public MouseCode getMouseCode() {
		return mouseCode;
	}
}
