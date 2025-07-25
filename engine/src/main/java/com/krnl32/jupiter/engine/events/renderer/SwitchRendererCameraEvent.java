package com.krnl32.jupiter.engine.events.renderer;

import com.krnl32.jupiter.engine.event.Event;
import com.krnl32.jupiter.engine.renderer.Camera;

public class SwitchRendererCameraEvent implements Event {
	private final Camera camera;

	public SwitchRendererCameraEvent(Camera camera) {
		this.camera = camera;
	}

	public Camera getCamera() {
		return camera;
	}
}
