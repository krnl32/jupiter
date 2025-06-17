package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.ecs.Component;
import org.joml.Vector3f;

public class BoxColliderComponent implements Component {
	public Vector3f size;
	public Vector3f offset;

	public BoxColliderComponent(Vector3f size) {
		this.size = size;
		this.offset = new Vector3f(0.0f, 0.0f, 0.0f);
	}

	public BoxColliderComponent(Vector3f size, Vector3f offset) {
		this.size = size;
		this.offset = offset;
	}

	public Vector3f getHalfSize() {
		return new Vector3f(size).mul(0.5f);
	}
}
