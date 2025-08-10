package com.krnl32.jupiter.engine.dto.scene;

import com.krnl32.jupiter.engine.dto.ecs.EntityDTO;

import java.util.List;
import java.util.Objects;

public class SceneDTO {
	private String name;
	private SceneSettingsDTO settings;
	private List<EntityDTO> entities;

	public SceneDTO() {
	}

	public SceneDTO(String name, SceneSettingsDTO settings, List<EntityDTO> entities) {
		this.name = name;
		this.settings = settings;
		this.entities = entities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SceneSettingsDTO getSettings() {
		return settings;
	}

	public void setSettings(SceneSettingsDTO settings) {
		this.settings = settings;
	}

	public List<EntityDTO> getEntities() {
		return entities;
	}

	public void setEntities(List<EntityDTO> entities) {
		this.entities = entities;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		SceneDTO sceneDTO = (SceneDTO) o;
		return Objects.equals(name, sceneDTO.name) && Objects.equals(settings, sceneDTO.settings) && Objects.equals(entities, sceneDTO.entities);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, settings, entities);
	}

	@Override
	public String toString() {
		return "SceneDTO{" +
			"name='" + name + '\'' +
			", settings=" + settings +
			", entities=" + entities +
			'}';
	}
}
