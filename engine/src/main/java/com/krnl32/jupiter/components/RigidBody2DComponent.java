package com.krnl32.jupiter.components;

import com.krnl32.jupiter.ecs.Component;
import com.krnl32.jupiter.physics.BodyType;
import org.jbox2d.dynamics.Body;
import org.joml.Vector2f;

public class RigidBody2DComponent implements Component {
	public BodyType bodyType;
	public float angularDamping;
	public float linearDamping;
	public float mass;
	public boolean fixedRotation;
	public boolean continuousCollision;
	public Body rawBody;

	public RigidBody2DComponent() {
		this.angularDamping = 0.8f;
		this.linearDamping = 0.9f;
		this.mass = 1.0f;
		this.bodyType = BodyType.DYNAMIC;
		this.fixedRotation = false;
		this.continuousCollision = true;
		this.rawBody = null;
	}

	public RigidBody2DComponent(BodyType bodyType, float angularDamping, float linearDamping, float mass, boolean fixedRotation, boolean continuousCollision, Body rawBody) {
		this.bodyType = bodyType;
		this.angularDamping = angularDamping;
		this.linearDamping = linearDamping;
		this.mass = mass;
		this.fixedRotation = fixedRotation;
		this.continuousCollision = continuousCollision;
		this.rawBody = rawBody;
	}
}
