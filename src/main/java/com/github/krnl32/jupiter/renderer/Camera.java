package com.github.krnl32.jupiter.renderer;

import com.github.krnl32.jupiter.event.EventBus;
import com.github.krnl32.jupiter.events.window.WindowResizeEvent;
import com.github.krnl32.jupiter.input.Input;
import com.github.krnl32.jupiter.input.KeyCode;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static java.lang.Math.*;
import static org.joml.Vector3fKt.cross;

public class Camera {
	private Vector3f position;
	private Vector3f direction;
	private Vector3f right;
	private Vector3f up;
	private Vector3f worldUp;
	private float yaw, pitch, roll = 0.0f;
	private float moveSpeed, turnSpeed, rollSpeed = 45.0f;

	private Matrix4f projectionMatrix;
	private ProjectionType projectionType;
	private float projectionFOV;
	private float projectionNear;
	private float projectionFar;
	private float projectionSize;
	private float aspectRatio;

	public Camera(Vector3f position, Vector3f worldUp, float yaw, float pitch, float moveSpeed, float turnSpeed) {
		this.position = position;
		this.direction = new Vector3f(0.0f, 0.0f, -1.0f);
		this.worldUp = worldUp;
		this.yaw = yaw;
		this.pitch = pitch;
		this.moveSpeed = moveSpeed;
		this.turnSpeed = turnSpeed;
		calculateCamera();

		this.projectionMatrix = new Matrix4f().identity();
		this.projectionType = ProjectionType.PERSPECTIVE;
		this.projectionFOV = 45.0f;
		this.projectionNear = 0.1f;
		this.projectionFar = 1000.0f;
		this.projectionSize = 10.0f;
		this.aspectRatio = 1.778f;
		calculateProjection();

		EventBus.getInstance().register(WindowResizeEvent.class, event -> {
			setViewport(event.getWidth(), event.getHeight());
		});
	}

	public void onUpdate(float dt) {
		// Mouse
		Vector2f mouseCursorDelta = Input.getInstance().getMouseCursorDelta();
		yaw += (mouseCursorDelta.x * turnSpeed);
		pitch += (mouseCursorDelta.y * turnSpeed);
		pitch = clamp(pitch, -89.0f, 89.0f);

		if (Input.getInstance().isKeyDown(KeyCode.Q))
			roll += rollSpeed * dt;

		if (Input.getInstance().isKeyDown(KeyCode.E))
			roll -= rollSpeed * dt;

		if (roll > 180.0f)
			roll -= 360.0f;
		else if (roll < -180.0f)
			roll += 360.0f;

		calculateCamera();
	}

	public void setViewport(int width, int height) {
		aspectRatio = (float)width / height;
		calculateProjection();
	}

	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	public void setPosition(Vector3f position) {
		this.position = position;
		calculateCamera();
	}

	public Matrix4f getProjectionMatrix() {
		return new Matrix4f(projectionMatrix);
	}

	public ProjectionType getProjectionType() {
		return projectionType;
	}

	public void setProjectionType(ProjectionType projectionType) {
		this.projectionType = projectionType;
		calculateProjection();
	}

	public float getProjectionFOV() {
		return projectionFOV;
	}

	public void setProjectionFOV(float fov) {
		this.projectionFOV = fov;
		calculateProjection();
	}

	public float getProjectionNear() {
		return projectionNear;
	}

	public void setProjectionNear(float near) {
		this.projectionNear = near;
		calculateProjection();
	}

	public float getProjectionFar() {
		return projectionFar;
	}

	public void setProjectionFar(float far) {
		this.projectionFar = far;
		calculateProjection();
	}

	public float getProjectionSize() {
		return projectionSize;
	}

	public void setProjectionSize(float size) {
		this.projectionSize = size;
		calculateProjection();
	}

	public float getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
		calculateProjection();
	}

	public void setOrthographic(float size, float near, float far) {
		projectionType = ProjectionType.ORTHOGRAPHIC;
		projectionSize = size;
		projectionNear = near;
		projectionFar = far;
		calculateProjection();
	}

	public void setPerspective(float fov, float near, float far) {
		projectionType = ProjectionType.PERSPECTIVE;
		projectionFOV = fov;
		projectionNear = near;
		projectionFar = far;
		calculateProjection();
	}

	public Matrix4f getViewMatrix() {
		return new Matrix4f().lookAt(position, new Vector3f(position).add(direction), up);
	}

	private void calculateCamera() {
		direction.x = (float)(cos(toRadians(yaw)) * cos(toRadians(pitch)));
		direction.y = (float)(sin(toRadians(pitch)));
		direction.z = (float)(sin(toRadians(yaw)) * cos(toRadians(pitch)));
		direction.normalize();

		right = cross(direction, worldUp).normalize();
		up = cross(right, direction).normalize();

		Matrix4f rollRotation = new Matrix4f().identity().rotate((float)Math.toRadians(roll), direction);
		right = rollRotation.transformDirection(right);
		up = rollRotation.transformDirection(up);
	}

	private void calculateProjection() {
		if (projectionType == ProjectionType.ORTHOGRAPHIC)
			projectionMatrix.identity().ortho(-aspectRatio * projectionSize * 0.5f, aspectRatio * projectionSize * 0.5f, -projectionSize * 0.5f, projectionSize * 0.5f, projectionNear, projectionFar);
		else if(projectionType == ProjectionType.PERSPECTIVE)
			projectionMatrix.identity().perspective((float)toRadians(projectionFOV), aspectRatio, projectionNear, projectionFar);
	}
}
