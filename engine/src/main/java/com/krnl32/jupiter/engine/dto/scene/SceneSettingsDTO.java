package com.krnl32.jupiter.engine.dto.scene;

import java.util.Objects;

public class SceneSettingsDTO {
	private float gravity;

	public SceneSettingsDTO() {
	}

	public SceneSettingsDTO(float gravity) {
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
		SceneSettingsDTO that = (SceneSettingsDTO) o;
		return Float.compare(gravity, that.gravity) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(gravity);
	}

	@Override
	public String toString() {
		return "SceneSettingsDTO{" +
			"gravity=" + gravity +
			'}';
	}
}
