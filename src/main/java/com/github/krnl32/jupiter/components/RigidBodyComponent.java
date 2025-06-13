package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.ecs.Component;
import org.joml.Vector3f;

public class RigidBodyComponent implements Component {
	public Vector3f velocity;

	public RigidBodyComponent(Vector3f velocity) {
		this.velocity = velocity;
	}
}
