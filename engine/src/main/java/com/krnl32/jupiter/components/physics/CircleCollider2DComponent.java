package com.krnl32.jupiter.components.physics;

import com.krnl32.jupiter.ecs.Component;
import org.joml.Vector2f;

public class CircleCollider2DComponent implements Component {
	public float radius;
	public Vector2f offset;
	public float friction;
	public float density;
	public boolean sensor;


	public CircleCollider2DComponent(float radius) {
		this.radius = radius;
		this.offset = new Vector2f(0.0f, 0.0f);
		this.friction = 0.5f;
		this.density = 1.0f;
		this.sensor = false;
	}

	public CircleCollider2DComponent(float radius, Vector2f offset, float friction, float density, boolean sensor) {
		this.radius = radius;
		this.offset = offset;
		this.friction = friction;
		this.density = density;
		this.sensor = sensor;
	}
}
