package com.krnl32.jupiter.components;

import com.krnl32.jupiter.ecs.Component;
import org.joml.Vector3f;

public class RigidBodyComponent implements Component {
	public Vector3f velocity;
	public Vector3f angularVelocity;

	public RigidBodyComponent(Vector3f velocity) {
		this.velocity = velocity;
		this.angularVelocity = new Vector3f(0.0f, 0.0f, 0.0f);
	}

	public RigidBodyComponent(Vector3f velocity, Vector3f angularVelocity) {
		this.velocity = velocity;
		this.angularVelocity = angularVelocity;
	}
}
