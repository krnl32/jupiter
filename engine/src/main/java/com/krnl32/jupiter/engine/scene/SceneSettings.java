package com.krnl32.jupiter.engine.scene;

import com.krnl32.jupiter.engine.physics.PhysicsSettings;

import java.util.Objects;

public class SceneSettings {
	private PhysicsSettings physicsSettings;

	public SceneSettings(PhysicsSettings physicsSettings) {
		this.physicsSettings = physicsSettings;
	}

	public PhysicsSettings getPhysicsSettings() {
		return physicsSettings;
	}

	public void setPhysicsSettings(PhysicsSettings physicsSettings) {
		this.physicsSettings = physicsSettings;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		SceneSettings settings = (SceneSettings) o;
		return Objects.equals(physicsSettings, settings.physicsSettings);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(physicsSettings);
	}

	@Override
	public String toString() {
		return "SceneSettings{" +
			"physicsSettings=" + physicsSettings +
			'}';
	}
}
