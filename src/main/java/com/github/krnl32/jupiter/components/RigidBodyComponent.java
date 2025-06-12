package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.game.Component;
import com.github.krnl32.jupiter.renderer.Renderer;
import org.joml.Vector3f;

public class RigidBodyComponent extends Component {
	private final Vector3f velocity;

	public RigidBodyComponent(Vector3f velocity) {
		this.velocity = velocity;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
