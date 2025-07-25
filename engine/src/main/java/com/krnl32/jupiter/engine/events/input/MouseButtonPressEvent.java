package com.krnl32.jupiter.engine.events.input;

import com.krnl32.jupiter.engine.event.Event;
import com.krnl32.jupiter.engine.input.devices.MouseCode;

public class MouseButtonPressEvent implements Event {
	private final MouseCode mouseCode;

	public MouseButtonPressEvent(MouseCode mouseCode) {
		this.mouseCode = mouseCode;
	}

	public MouseCode getMouseCode() {
		return mouseCode;
	}
}
