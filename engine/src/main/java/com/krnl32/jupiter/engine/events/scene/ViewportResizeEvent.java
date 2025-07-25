package com.krnl32.jupiter.engine.events.scene;

import com.krnl32.jupiter.engine.event.Event;

public class ViewportResizeEvent implements Event {
	private final int width, height;

	public ViewportResizeEvent(int width, int height) {
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
