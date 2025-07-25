package com.krnl32.jupiter.engine.events.window;

import com.krnl32.jupiter.engine.event.Event;

public class WindowResizeEvent implements Event {
	private final int width, height;

	public WindowResizeEvent(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
