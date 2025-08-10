package com.krnl32.jupiter.engine.scene;

public class SceneSettings {
	private float gravity;

	public SceneSettings() {
		this.gravity = 9.8f;
	}

	public SceneSettings(float gravity) {
		this.gravity = gravity;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	@Override
	public String toString() {
		return "SceneSettings{" +
			"gravity=" + gravity +
			'}';
	}
}
