package com.krnl32.jupiter.components;

import com.krnl32.jupiter.ecs.Component;
import com.krnl32.jupiter.renderer.Camera;

public class CameraComponent implements Component {
	public Camera camera;
	public boolean primary;

	public CameraComponent(Camera camera, boolean primary) {
		this.camera = camera;
		this.primary = primary;
	}
}
