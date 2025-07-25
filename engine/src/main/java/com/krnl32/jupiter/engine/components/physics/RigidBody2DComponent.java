package com.krnl32.jupiter.engine.components.physics;

import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.physics.BodyType;
import org.jbox2d.dynamics.Body;
import org.joml.Vector2f;

public class RigidBody2DComponent implements Component {
	public BodyType bodyType;
	public Vector2f velocity;
	public float linearDamping;
	public float angularDamping;
	public float mass;
	public float gravityScale;
	public boolean fixedRotation;
	public boolean continuousCollision;
	public Body rawBody;
	public boolean resync;

	public RigidBody2DComponent(BodyType bodyType) {
		this.bodyType = bodyType;
		this.velocity = new Vector2f();
		this.linearDamping = 0.9f;
		this.angularDamping = 0.8f;
		this.mass = 1.0f;
		this.gravityScale = 1.0f;
		this.fixedRotation = false;
		this.continuousCollision = true;
		this.rawBody = null;
		this.resync = false;
	}

	public RigidBody2DComponent(BodyType bodyType, Vector2f velocity) {
		this.bodyType = bodyType;
		this.velocity = velocity;
		this.linearDamping = 0.9f;
		this.angularDamping = 0.8f;
		this.mass = 1.0f;
		this.gravityScale = 1.0f;
		this.fixedRotation = false;
		this.continuousCollision = true;
		this.rawBody = null;
		this.resync = false;
	}

	public RigidBody2DComponent(BodyType bodyType, Vector2f velocity, float linearDamping, float angularDamping, float mass, float gravityScale, boolean fixedRotation, boolean continuousCollision) {
		this.bodyType = bodyType;
		this.velocity = velocity;
		this.linearDamping = linearDamping;
		this.angularDamping = angularDamping;
		this.mass = mass;
		this.gravityScale = gravityScale;
		this.fixedRotation = fixedRotation;
		this.continuousCollision = continuousCollision;
		this.rawBody = null;
		this.resync = false;
	}

	public RigidBody2DComponent(BodyType bodyType, Vector2f velocity, float linearDamping, float angularDamping, float mass, float gravityScale, boolean fixedRotation, boolean continuousCollision, Body rawBody) {
		this.bodyType = bodyType;
		this.velocity = velocity;
		this.linearDamping = linearDamping;
		this.angularDamping = angularDamping;
		this.mass = mass;
		this.gravityScale = gravityScale;
		this.fixedRotation = fixedRotation;
		this.continuousCollision = continuousCollision;
		this.rawBody = rawBody;
		this.resync = false;
	}
}
