package com.krnl32.jupiter.engine.components.gameplay;

import com.krnl32.jupiter.engine.ecs.Component;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TransformComponent implements Component {
	public Vector3f translation;
	public Vector3f rotation;
	public Vector3f scale;

	public TransformComponent(Vector3f translation, Vector3f rotation, Vector3f scale) {
		this.translation = translation;
		this.rotation = rotation;
		this.scale = scale;
	}

	public Matrix4f getTransform() {
		return new Matrix4f()
			.translate(translation)
			.rotateXYZ(rotation.x, rotation.y, rotation.z)
			.scale(scale);
	}

	@Override
	public String toString() {
		return "TransformComponent{" +
			"translation=" + translation +
			", rotation=" + rotation +
			", scale=" + scale +
			'}';
	}
}
