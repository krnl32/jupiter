package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.game.Component;
import com.github.krnl32.jupiter.renderer.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TransformComponent extends Component {
	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;
	private float rotation2D;

	public TransformComponent() {
	}

	public TransformComponent(Vector3f translation, Vector3f rotation, Vector3f scale) {
		this.translation = translation;
		this.rotation = rotation;
		this.scale = scale;
	}

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}

	public Vector3f getTranslation() {
		return translation;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public float getRotation2D() {
		return rotation2D;
	}

	public Matrix4f getTransform() {
		return new Matrix4f().identity().translate(translation).rotate(rotation2D, rotation).scale(scale);
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
