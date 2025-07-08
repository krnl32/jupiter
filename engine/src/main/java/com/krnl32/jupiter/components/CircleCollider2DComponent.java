package com.krnl32.jupiter.components;

import com.krnl32.jupiter.ecs.Component;
import org.joml.Vector2f;

public class CircleCollider2DComponent implements Component {
	public float radius;
	public Vector2f offset;

	public CircleCollider2DComponent(float radius) {
		this.radius = radius;
		this.offset = new Vector2f(0.0f, 0.0f);
	}

	public CircleCollider2DComponent(float radius, Vector2f offset) {
		this.radius = radius;
		this.offset = offset;
	}
}
