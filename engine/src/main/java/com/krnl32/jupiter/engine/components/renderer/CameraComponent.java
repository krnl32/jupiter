package com.krnl32.jupiter.engine.components.renderer;

import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.renderer.Camera;

public class CameraComponent implements Component {
	public Camera camera;
	public boolean primary;

	public CameraComponent(Camera camera, boolean primary) {
		this.camera = camera;
		this.primary = primary;
	}
}
