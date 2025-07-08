package com.krnl32.jupiter.components;

import com.krnl32.jupiter.ecs.Component;
import org.joml.Vector2f;

public class BoxCollider2DComponent implements Component {
	public Vector2f size;
	public Vector2f offset;

	public BoxCollider2DComponent(Vector2f size) {
		this.size = size;
		this.offset = new Vector2f(0.0f, 0.0f);
	}

	public BoxCollider2DComponent(Vector2f size, Vector2f offset) {
		this.size = size;
		this.offset = offset;
	}

	public Vector2f getHalfSize() {
		return new Vector2f(size).mul(0.5f);
	}
}
