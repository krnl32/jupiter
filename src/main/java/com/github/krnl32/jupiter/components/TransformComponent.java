package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.game.Component;
import org.joml.Vector3f;

public class TransformComponent extends Component {
	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;

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
	public void onRender(float dt) {
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
