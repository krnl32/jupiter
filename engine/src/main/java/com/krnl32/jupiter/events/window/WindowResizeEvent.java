package com.krnl32.jupiter.events.window;

import com.krnl32.jupiter.event.Event;

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
