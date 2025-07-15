package com.krnl32.jupiter.components;

import com.krnl32.jupiter.ecs.Component;
import com.krnl32.jupiter.physics.BodyType;
import org.jbox2d.dynamics.Body;
import org.joml.Vector2f;

public class RigidBody2DComponent implements Component {
	public BodyType bodyType;
	public Vector2f initialVelocity;
	public float angularDamping;
	public float linearDamping;
	public float mass;
	public float gravityScale;
	public boolean fixedRotation;
	public boolean continuousCollision;
	public Body rawBody;

	public RigidBody2DComponent(BodyType bodyType) {
		this.bodyType = bodyType;
		this.initialVelocity = new Vector2f();
		this.angularDamping = 0.8f;
		this.linearDamping = 0.9f;
		this.mass = 1.0f;
		this.gravityScale = 1.0f;
		this.fixedRotation = false;
		this.continuousCollision = true;
		this.rawBody = null;
	}

	public RigidBody2DComponent(BodyType bodyType, Vector2f initialVelocity) {
		this.bodyType = bodyType;
		this.initialVelocity = initialVelocity;
		this.angularDamping = 0.8f;
		this.linearDamping = 0.9f;
		this.mass = 1.0f;
		this.gravityScale = 1.0f;
		this.fixedRotation = false;
		this.continuousCollision = true;
		this.rawBody = null;
	}

	public RigidBody2DComponent(BodyType bodyType, Vector2f initialVelocity, float angularDamping, float linearDamping, float mass, float gravityScale, boolean fixedRotation, boolean continuousCollision) {
		this.bodyType = bodyType;
		this.initialVelocity = initialVelocity;
		this.angularDamping = angularDamping;
		this.linearDamping = linearDamping;
		this.mass = mass;
		this.gravityScale = gravityScale;
		this.fixedRotation = fixedRotation;
		this.continuousCollision = continuousCollision;
		this.rawBody = null;
	}

	public RigidBody2DComponent(BodyType bodyType, Vector2f initialVelocity, float angularDamping, float linearDamping, float mass, float gravityScale, boolean fixedRotation, boolean continuousCollision, Body rawBody) {
		this.bodyType = bodyType;
		this.initialVelocity = initialVelocity;
		this.angularDamping = angularDamping;
		this.linearDamping = linearDamping;
		this.mass = mass;
		this.gravityScale = gravityScale;
		this.fixedRotation = fixedRotation;
		this.continuousCollision = continuousCollision;
		this.rawBody = rawBody;
	}
}
