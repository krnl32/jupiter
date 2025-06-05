package com.github.krnl32.jupiter.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static java.lang.Math.*;
import static org.joml.Vector3fKt.cross;

public class RendererCamera {
	private Vector3f position;
	private Vector3f direction;
	private Vector3f right;
	private Vector3f up;
	private Vector3f worldUp;
	private float yaw, pitch;
	private float moveSpeed, turnSpeed;

	public RendererCamera(Vector3f position, Vector3f worldUp, float yaw, float pitch, float moveSpeed, float turnSpeed) {
		this.position = position;
		this.direction = new Vector3f(0.0f, 0.0f, -1.0f);
		this.worldUp = worldUp;
		this.yaw = yaw;
		this.pitch = pitch;
		this.moveSpeed = moveSpeed;
		this.turnSpeed = turnSpeed;

		onUpdate();
	}

	public void onUpdate() {
		direction.x = (float)(cos(toRadians(yaw)) * cos(toRadians(pitch)));
		direction.y = (float)(sin(toRadians(pitch)));
		direction.z = (float)(sin(toRadians(yaw)) * cos(toRadians(pitch)));
		direction.normalize();

		right = cross(direction, worldUp).normalize();
		up = cross(right, direction).normalize();
	}

	public Matrix4f getViewMatrix() {
		return new Matrix4f().lookAt(position, new Vector3f(position).add(direction), up);
	}
}
