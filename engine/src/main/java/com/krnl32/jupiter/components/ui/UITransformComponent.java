package com.krnl32.jupiter.components.ui;

import com.krnl32.jupiter.ecs.Component;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class UITransformComponent implements Component {
	public Vector3f translation;
	public Vector3f rotation;
	public Vector3f scale;
	public Vector2f anchor;
	public Vector2f pivot;

	public UITransformComponent(Vector3f translation, Vector3f rotation, Vector3f scale) {
		this.translation = translation;
		this.rotation = rotation;
		this.scale = scale;
		this.anchor = new Vector2f(0.0f, 0.0f);
		this.pivot = new Vector2f(0.5f, 0.5f);
	}

	public UITransformComponent(Vector3f translation, Vector3f rotation, Vector3f scale, Vector2f anchor, Vector2f pivot) {
		this.translation = translation;
		this.rotation = rotation;
		this.scale = scale;
		this.anchor = anchor;
		this.pivot = pivot;
	}
}
