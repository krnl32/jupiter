package com.krnl32.jupiter.engine.mapper;

import com.krnl32.jupiter.engine.dto.ecs.EntityDTO;
import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.ComponentSerializerRegistry;

import java.util.HashMap;
import java.util.Map;

public class EntityMapper {
	public static EntityDTO toDTO(Entity entity) {
		Map<String, Map<String, Object>> components = new HashMap<>();
		for (Component component : entity.getComponents()) {
			ComponentSerializer<Component, Map<String, Object>> serializer = ComponentSerializerRegistry.getSerializer(component.getClass());
			if (serializer != null) {
				components.put(component.getClass().getSimpleName(), serializer.serialize(component));
			}
		}
		return new EntityDTO(components);
	}
}
