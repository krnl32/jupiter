package com.krnl32.jupiter.engine.dto.ecs;

import java.util.Map;
import java.util.Objects;

public class EntityDTO {
	private Map<String, Map<String, Object>> components;

	public EntityDTO() {
	}

	public EntityDTO(Map<String, Map<String, Object>> components) {
		this.components = components;
	}

	public Map<String, Map<String, Object>> getComponents() {
		return components;
	}

	public void setComponents(Map<String, Map<String, Object>> components) {
		this.components = components;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		EntityDTO entityDTO = (EntityDTO) o;
		return Objects.equals(components, entityDTO.components);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(components);
	}

	@Override
	public String toString() {
		return "EntityDTO{" +
			"components=" + components +
			'}';
	}
}
