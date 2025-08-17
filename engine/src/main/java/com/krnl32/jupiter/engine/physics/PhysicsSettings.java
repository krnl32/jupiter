package com.krnl32.jupiter.engine.physics;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.Objects;

public class PhysicsSettings {
	private boolean enabled;
	private Vector3f gravity;

	public PhysicsSettings(boolean enabled, Vector3f gravity) {
		this.enabled = enabled;
		this.gravity = gravity;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Vector3fc getGravity() {
		return gravity;
	}

	public void setGravity(Vector3f gravity) {
		this.gravity = gravity;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		PhysicsSettings that = (PhysicsSettings) o;
		return enabled == that.enabled && Objects.equals(gravity, that.gravity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(enabled, gravity);
	}

	@Override
	public String toString() {
		return "PhysicsSettings{" +
			"enabled=" + enabled +
			", gravity=" + gravity +
			'}';
	}
}
