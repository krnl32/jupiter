package com.krnl32.jupiter.engine.renderer;

import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.scene.ViewportResizeEvent;
import com.krnl32.jupiter.engine.input.InputDeviceSystem;
import com.krnl32.jupiter.engine.input.devices.KeyCode;
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
	private float yaw, pitch, roll, zoom;
	private final float turnSpeed, rollSpeed, zoomSpeed;

	private Matrix4f projectionMatrix;
	private ProjectionType projectionType;
	private float projectionFOV;
	private float projectionNear;
	private float projectionFar;
	private float projectionSize;
	private float aspectRatio;

	private boolean mouseEnabled;

	public Camera(Vector3f position, Vector3f worldUp, float yaw, float pitch, float roll, float zoom, float turnSpeed, float rollSpeed, float zoomSpeed, boolean mouseEnabled) {
		this.position = position;
		this.direction = new Vector3f(0.0f, 0.0f, -1.0f);
		this.worldUp = worldUp;
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
		this.zoom = zoom;
		this.turnSpeed = turnSpeed;
		this.rollSpeed = rollSpeed;
		this.zoomSpeed = zoomSpeed;
		calculateCamera();

		this.projectionMatrix = new Matrix4f().identity();
		this.projectionType = ProjectionType.PERSPECTIVE;
		this.projectionFOV = 45.0f;
		this.projectionNear = 0.1f;
		this.projectionFar = 1000.0f;
		this.projectionSize = 10.0f;
		this.aspectRatio = 1.778f;
		calculateProjection();

		this.mouseEnabled = mouseEnabled;

		EventBus.getInstance().register(ViewportResizeEvent.class, event -> {
			setViewport(event.getWidth(), event.getHeight());
		});
	}

	public void onUpdate(float dt) {
		if (mouseEnabled) {
			// Mouse
			Vector2f mouseCursorDelta = InputDeviceSystem.getInstance().getMouseCursorDelta();
			yaw += (mouseCursorDelta.x * turnSpeed);
			pitch += (mouseCursorDelta.y * turnSpeed);
			pitch = clamp(pitch, -89.0f, 89.0f);

			if (InputDeviceSystem.getInstance().isKeyDown(KeyCode.Q))
				roll += rollSpeed * dt;
			if (InputDeviceSystem.getInstance().isKeyDown(KeyCode.E))
				roll -= rollSpeed * dt;

			if (roll > 180.0f)
				roll -= 360.0f;
			else if (roll < -180.0f)
				roll += 360.0f;

			if (InputDeviceSystem.getInstance().isMouseScrollingUp())
				zoom(zoom * zoomSpeed * dt);
			if (InputDeviceSystem.getInstance().isMouseScrollingDown())
				zoom(-zoom * zoomSpeed * dt);
		}

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

	public Vector3f getWorldUp() {
		return new Vector3f(worldUp);
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public float getRoll() {
		return roll;
	}

	public float getZoom() {
		return zoom;
	}

	public float getTurnSpeed() {
		return turnSpeed;
	}

	public float getRollSpeed() {
		return rollSpeed;
	}

	public float getZoomSpeed() {
		return zoomSpeed;
	}

	public boolean isMouseEnabled() {
		return mouseEnabled;
	}

	public void setMouseEnabled(boolean state) {
		mouseEnabled = state;
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

	private void zoom(float size) {
		if (projectionType == ProjectionType.ORTHOGRAPHIC)
			projectionSize = max(0.1f, projectionSize - size * 0.1f);
		else if (projectionType == ProjectionType.PERSPECTIVE)
			projectionFOV = clamp(projectionFOV - size, 1.0f, 90.0f);
		calculateProjection();
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
