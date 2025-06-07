package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.game.Component;
import com.github.krnl32.jupiter.renderer.Camera;
import com.github.krnl32.jupiter.renderer.ProjectionType;
import com.github.krnl32.jupiter.renderer.Renderer;

public class CameraComponent extends Component {
	private final Camera camera;
	private boolean primary;

	public CameraComponent(Camera camera, boolean primary) {
		this.camera = camera;
		this.primary = primary;
	}

	@Override
	public void onUpdate(float dt) {
		TransformComponent transform = getGameObject().getComponent(TransformComponent.class);
		camera.setPosition(transform.getTranslation());
		camera.onUpdate(dt);
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		if (primary)
			renderer.setActiveCamera(camera);
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

	public void setOrthographic(float size, float near, float far) {
		camera.setOrthographic(size, near, far);
	}

	public void setPerspective(float fov, float near, float far) {
		camera.setPerspective(fov, near, far);
	}

	public ProjectionType getProjectionType() {
		return camera.getProjectionType();
	}

	public void setProjectionType(ProjectionType projectionType) {
		camera.setProjectionType(projectionType);
	}
}
