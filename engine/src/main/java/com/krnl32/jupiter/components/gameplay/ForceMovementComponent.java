package com.krnl32.jupiter.components.gameplay;

import com.krnl32.jupiter.ecs.Component;

public class ForceMovementComponent implements Component {
	public float moveForce;
	public float sprintMultiplier;
	public float maxSpeed;
	public float rotationTorque;
	public float jumpImpulse;

	public ForceMovementComponent() {
		this.moveForce = 10.0f;
		this.sprintMultiplier = 1.5f;
		this.maxSpeed = 15.0f;
		this.rotationTorque = 1.0f;
		this.jumpImpulse = 10.0f;
	}

	public ForceMovementComponent(float moveForce, float sprintMultiplier, float maxSpeed, float rotationTorque, float jumpImpulse) {
		this.moveForce = moveForce;
		this.sprintMultiplier = sprintMultiplier;
		this.maxSpeed = maxSpeed;
		this.rotationTorque = rotationTorque;
		this.jumpImpulse = jumpImpulse;
	}
}
