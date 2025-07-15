package com.krnl32.jupiter.editor;

import com.krnl32.jupiter.input.Input;
import com.krnl32.jupiter.input.KeyCode;
import com.krnl32.jupiter.renderer.Camera;
import com.krnl32.jupiter.renderer.ProjectionType;
import org.joml.Vector3f;

public class EditorCamera {
	private final Camera camera;
	private final float moveSpeed;

	public EditorCamera(ProjectionType projectionType, float moveSpeed) {
		this.camera = new Camera(new Vector3f(0.0f, 0.0f, 3.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f, true);
		if (projectionType == ProjectionType.ORTHOGRAPHIC) {
			this.camera.setOrthographic(10.0f, 0.1f, 100.0f);
		} else if (projectionType == ProjectionType.PERSPECTIVE) {
			this.camera.setPerspective(45.0f, 0.1f, 100.0f);
		}
		this.moveSpeed = moveSpeed;
	}

	public void onUpdate(float dt) {
		camera.onUpdate(dt);

		Vector3f position = camera.getPosition();
		Vector3f move = new Vector3f();

		if (Input.getInstance().isKeyDown(KeyCode.W))
			move.add(0.0f, 0.0f, -1.0f);
		if (Input.getInstance().isKeyDown(KeyCode.S))
			move.add(0.0f, 0.0f, 1.0f);
		if (Input.getInstance().isKeyDown(KeyCode.A))
			move.add(-1.0f, 0.0f, 0.0f);
		if (Input.getInstance().isKeyDown(KeyCode.D))
			move.add(1.0f, 0.0f, 0.0f);
		if (Input.getInstance().isKeyDown(KeyCode.SPACE))
			move.add(0.0f, 1.0f, 0.0f);
		if (Input.getInstance().isKeyDown(KeyCode.LEFT_CONTROL))
			move.add(0.0f, -1.0f, 0.0f);
		if (Input.getInstance().isKeyPressed(KeyCode.X))
			camera.setMouseEnabled(!camera.isMouseEnabled());

		if (move.lengthSquared() > 0) {
			move.normalize().mul(moveSpeed * dt);
			position.add(move);
			camera.setPosition(position);
		}
	}

	public Camera getCamera() {
		return camera;
	}
}
