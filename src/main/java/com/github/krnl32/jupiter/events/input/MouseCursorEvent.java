package com.github.krnl32.jupiter.events.input;

import com.github.krnl32.jupiter.event.Event;
import org.joml.Vector2f;

public class MouseCursorEvent implements Event {
	private final Vector2f position;

	public MouseCursorEvent(Vector2f position) {
		this.position = position;
	}

	public Vector2f getPosition() {
		return position;
	}
}
