package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.ecs.Component;
import org.joml.Vector3f;

public class TransformComponent implements Component {
	public Vector3f translation;
	public Vector3f rotation;
	public Vector3f scale;
	public float rotation2D;

	public TransformComponent(Vector3f translation, Vector3f rotation, Vector3f scale) {
		this.translation = translation;
		this.rotation = rotation;
		this.scale = scale;
		this.rotation2D = 0;
	}

	@Override
	public String toString() {
		return "TransformComponent{" +
			"translation=" + translation +
			", rotation=" + rotation +
			", scale=" + scale +
			", rotation2D=" + rotation2D +
			'}';
	}
}
