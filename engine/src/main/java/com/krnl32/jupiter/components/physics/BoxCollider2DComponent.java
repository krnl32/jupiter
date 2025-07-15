package com.krnl32.jupiter.components.physics;

import com.krnl32.jupiter.ecs.Component;
import org.joml.Vector2f;

public class BoxCollider2DComponent implements Component {
	public Vector2f size;
	public Vector2f offset;
	public float friction;
	public float density;
	public boolean sensor;

	public BoxCollider2DComponent(Vector2f size) {
		this.size = size;
		this.offset = new Vector2f(0.0f, 0.0f);
		this.friction = 0.5f;
		this.density = 1.0f;
		this.sensor = false;
	}

	public BoxCollider2DComponent(Vector2f size, Vector2f offset, float friction, float density, boolean sensor) {
		this.size = size;
		this.offset = offset;
		this.friction = friction;
		this.density = density;
		this.sensor = sensor;
	}

	public Vector2f getHalfSize() {
		return new Vector2f(size).mul(0.5f);
	}
}
