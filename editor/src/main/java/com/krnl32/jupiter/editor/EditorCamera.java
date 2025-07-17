package com.krnl32.jupiter.editor;

import com.krnl32.jupiter.input.InputActionSystem;
import com.krnl32.jupiter.input.action.InputActionMap;
import com.krnl32.jupiter.input.action.InputBinding;
import com.krnl32.jupiter.input.devices.KeyCode;
import com.krnl32.jupiter.renderer.Camera;
import com.krnl32.jupiter.renderer.ProjectionType;
import org.joml.Vector3f;

public class EditorCamera {
	private final Camera camera;
	private final float moveSpeed;

	public EditorCamera(ProjectionType projectionType, float moveSpeed) {
		this.camera = new Camera(new Vector3f(0.0f, 0.0f, 3.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f, false);
		if (projectionType == ProjectionType.ORTHOGRAPHIC) {
			this.camera.setOrthographic(10.0f, 0.1f, 100.0f);
		} else if (projectionType == ProjectionType.PERSPECTIVE) {
			this.camera.setPerspective(45.0f, 0.1f, 100.0f);
		}
		this.moveSpeed = moveSpeed;

		// Setup Input
		InputActionMap editorCameraControls = new InputActionMap("EditorCameraControls");

		com.krnl32.jupiter.input.action.InputAction moveUp = new com.krnl32.jupiter.input.action.InputAction("MoveUp");
		moveUp.addBinding(new InputBinding(KeyCode.SPACE));

		com.krnl32.jupiter.input.action.InputAction moveDown = new com.krnl32.jupiter.input.action.InputAction("MoveDown");
		moveDown.addBinding(new InputBinding(KeyCode.LEFT_CONTROL));

		com.krnl32.jupiter.input.action.InputAction moveLeft = new com.krnl32.jupiter.input.action.InputAction("MoveLeft");
		moveLeft.addBinding(new InputBinding(KeyCode.A));

		com.krnl32.jupiter.input.action.InputAction moveRight = new com.krnl32.jupiter.input.action.InputAction("MoveRight");
		moveRight.addBinding(new InputBinding(KeyCode.D));

		com.krnl32.jupiter.input.action.InputAction moveForward = new com.krnl32.jupiter.input.action.InputAction("MoveForward");
		moveForward.addBinding(new InputBinding(KeyCode.W));

		com.krnl32.jupiter.input.action.InputAction moveBackward = new com.krnl32.jupiter.input.action.InputAction("MoveBackward");
		moveBackward.addBinding(new InputBinding(KeyCode.S));

		com.krnl32.jupiter.input.action.InputAction toggleMouse = new com.krnl32.jupiter.input.action.InputAction("ToggleMouse");
		toggleMouse.addBinding(new InputBinding(KeyCode.X));

		editorCameraControls.addAction(moveUp);
		editorCameraControls.addAction(moveDown);
		editorCameraControls.addAction(moveLeft);
		editorCameraControls.addAction(moveRight);
		editorCameraControls.addAction(moveForward);
		editorCameraControls.addAction(moveBackward);
		editorCameraControls.addAction(toggleMouse);
		InputActionSystem.getInstance().addInputActionMap(editorCameraControls);
	}

	public void onUpdate(float dt) {
		camera.onUpdate(dt);

		Vector3f position = camera.getPosition();
		Vector3f move = new Vector3f();

		InputActionMap editorCameraControls = InputActionSystem.getInstance().getInputActionMap("EditorCameraControls");
		if (editorCameraControls != null) {
			if (editorCameraControls.getAction("MoveForward").isActive()) {
				move.add(0.0f, 0.0f, -1.0f);
			}
			if (editorCameraControls.getAction("MoveDown").isActive()) {
				move.add(0.0f, 0.0f, 1.0f);
			}
			if (editorCameraControls.getAction("MoveLeft").isActive()) {
				move.add(-1.0f, 0.0f, 0.0f);
			}
			if (editorCameraControls.getAction("MoveRight").isActive()) {
				move.add(1.0f, 0.0f, 0.0f);
			}
			if (editorCameraControls.getAction("MoveUp").isActive()) {
				move.add(0.0f, 1.0f, 0.0f);
			}
			if (editorCameraControls.getAction("MoveDown").isActive()) {
				move.add(0.0f, -1.0f, 0.0f);
			}
			if (editorCameraControls.getAction("ToggleMouse").isPressed()) {
				camera.setMouseEnabled(!camera.isMouseEnabled());
			}
		}

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
