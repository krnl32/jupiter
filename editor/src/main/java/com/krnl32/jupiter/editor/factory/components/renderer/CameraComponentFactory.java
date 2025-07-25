package com.krnl32.jupiter.editor.factory.components.renderer;

import com.krnl32.jupiter.engine.components.renderer.CameraComponent;
import com.krnl32.jupiter.editor.factory.ComponentFactory;
import com.krnl32.jupiter.engine.renderer.Camera;
import org.joml.Vector3f;

public class CameraComponentFactory implements ComponentFactory<CameraComponent> {
	@Override
	public CameraComponent create() {
		CameraComponent cameraComponent = new CameraComponent(new Camera(new Vector3f(0.0f, 0.0f, 3.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f, false), false);
		cameraComponent.camera.setOrthographic(10.0f, 0.1f, 100.0f);
		return cameraComponent;
	}
}
