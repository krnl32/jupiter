package com.krnl32.jupiter.engine.scene;

import com.krnl32.jupiter.engine.core.Logger;

import java.util.Objects;

public class SceneSettings implements Cloneable {
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
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		SceneSettings settings = (SceneSettings) o;
		return Float.compare(gravity, settings.gravity) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(gravity);
	}

	@Override
	public String toString() {
		return "SceneSettings{" +
			"gravity=" + gravity +
			'}';
	}

	@Override
	public SceneSettings clone() {
		try {
			return (SceneSettings) super.clone();
		} catch (CloneNotSupportedException e) {
			Logger.error("SceneSettings Clone Failed");
			return new SceneSettings(gravity);
		}
	}
}
