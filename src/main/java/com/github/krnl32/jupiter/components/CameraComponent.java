package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.game.Component;
import com.github.krnl32.jupiter.renderer.Camera;
import com.github.krnl32.jupiter.renderer.Renderer;

public class CameraComponent extends Component {
	private Camera camera;
	private boolean primary;

	public CameraComponent(Camera camera, boolean primary) {
		this.camera = camera;
		this.primary = primary;
	}

	@Override
	public void onUpdate(float dt) {

	}

	@Override
	public void onRender(float dt, Renderer renderer) {

	}

	public Camera getCamera() {
		return camera;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
}
