package com.krnl32.jupiter.events.input;

import com.krnl32.jupiter.event.Event;
import org.joml.Vector2f;

public class MouseScrollEvent implements Event {
	private final Vector2f offset;

	public MouseScrollEvent(Vector2f offset) {
		this.offset = offset;
	}

	public Vector2f getOffset() {
		return offset;
	}

	public float getHorizontalOffset() {
		return offset.x;
	}

	public float getVerticalOffset() {
		return offset.y;
	}
}
