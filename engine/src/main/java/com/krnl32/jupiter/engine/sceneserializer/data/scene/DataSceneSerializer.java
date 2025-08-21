package com.krnl32.jupiter.engine.sceneserializer.data.scene;

import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.scene.SceneSettings;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializerRegistry;
import com.krnl32.jupiter.engine.sceneserializer.SceneSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataSceneSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

import java.util.*;

public class DataSceneSerializer implements SceneSerializer<Map<String, Object>> {
	private final ComponentSerializerRegistry componentSerializerRegistry;

	public DataSceneSerializer(ComponentSerializerRegistry componentSerializerRegistry) {
		this.componentSerializerRegistry = componentSerializerRegistry;
	}

	@Override
	public Map<String, Object> serialize(Scene scene) {
		List<Object> entities = new ArrayList<>();

		for (Entity entity : scene.getRegistry().getEntities()) {
			entities.add(serializeEntity(entity));
		}

		return Map.of(
			"name", scene.getName(),
			"settings", DataSceneSerializerUtility.serializerSceneSettings(scene.getSceneSettings()),
			"entities", entities
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Scene deserialize(Map<String, Object> data) {
		String sceneName = (String) data.get("name");
		Map<String, Object> sceneSettingsSerialized = (Map<String, Object>) data.get("settings");
		SceneSettings sceneSettings = DataSceneSerializerUtility.deserializeSceneSettings(sceneSettingsSerialized);

		Scene scene = new Scene(sceneName, sceneSettings) {
			@Override
			public void onCreate() {}
			@Override
			public void onActivate() {}
			@Override
			public void onUnload() {}
		};

		// Create Entities with UUIDComponent
		Map<UUID, Entity> uuidToEntity = new HashMap<>();

		List<Map<String, Object>> entities = (List<Map<String, Object>>) data.get("entities");
		for (int i = 0; i < entities.size(); i++) {
			Map<String, Object> entityMap = entities.get(i);
			Map<String, Map<String, Object>> components = (Map<String, Map<String, Object>>) entityMap.get("components");

			Map<String, Object> uuidComponent = components.get("UUIDComponent");
			if (uuidComponent == null) {
				Logger.error("SceneSerializer Deserialize Failed, UUIDComponent not Found for Entity({})", i);
				return null;
			}

			UUID uuid = UUID.fromString((String) uuidComponent.get("uuid"));
			Entity entity = scene.createEntity();
			entity.addComponent(new UUIDComponent(uuid));
			uuidToEntity.put(uuid, entity);
		}

		// Resolve UUIDs
		EntityResolver resolver = uuid -> {
			Entity entity = uuidToEntity.get(uuid);
			if (entity == null) {
				Logger.error("SceneSerializer Deserialize Failed, Entity Not Found for UUID({})", uuid);
			}
			return entity;
		};

		// Deserialize components
		for (int i = 0; i < entities.size(); i++) {
			Map<String, Object> entityMap = entities.get(i);
			deserializeEntity(entityMap, resolver, uuidToEntity);
		}

		return scene;
	}

	@SuppressWarnings("unchecked")
	public void deserialize(Map<String, Object> data, Scene scene) {
		// Create Entities with UUIDComponents
		Map<UUID, Entity> uuidToEntity = new HashMap<>();

		List<Map<String, Object>> entities = (List<Map<String, Object>>) data.get("entities");
		for (int i = 0; i < entities.size(); i++) {
			Map<String, Object> entityMap = entities.get(i);
			Map<String, Map<String, Object>> components = (Map<String, Map<String, Object>>) entityMap.get("components");

			Map<String, Object> uuidComponent = components.get("UUIDComponent");
			if (uuidComponent == null) {
				Logger.error("SceneSerializer Deserialize Failed, UUIDComponent not Found for Entity({})", i);
				return;
			}

			UUID uuid = UUID.fromString((String) uuidComponent.get("uuid"));
			Entity entity = scene.createEntity();
			entity.addComponent(new UUIDComponent(uuid));
			uuidToEntity.put(uuid, entity);
		}

		// Resolve UUIDs
		EntityResolver resolver = uuid -> {
			Entity entity = uuidToEntity.get(uuid);
			if (entity == null) {
				Logger.error("SceneSerializer Deserialize Failed, Entity Not Found for UUID({})", uuid);
			}
			return entity;
		};

		// Deserialize components
		for (int i = 0; i < entities.size(); i++) {
			Map<String, Object> entityMap = entities.get(i);
			deserializeEntity(entityMap, resolver, uuidToEntity);
		}
	}

	private Map<String, Object> serializeEntity(Entity entity) {
		Map<String, Map<String, Object>> components = new HashMap<>();
		for (Component component : entity.getComponents()) {
			ComponentSerializer<Component, Map<String, Object>> serializer = componentSerializerRegistry.getSerializer(component.getClass());
			if (serializer != null) {
				components.put(component.getClass().getSimpleName(), serializer.serialize(component));
			}
		}
		return Map.of("components", components);
	}

	@SuppressWarnings("unchecked")
	private void deserializeEntity(Map<String, Object> data, EntityResolver resolver, Map<UUID, Entity> uuidToEntity) {
		Map<String, Map<String, Object>> components = (Map<String, Map<String, Object>>) data.get("components");

		Map<String, Object> uuidComponent = components.get("UUIDComponent");
		if (uuidComponent == null) {
			Logger.error("SceneSerializer deserializeEntity Failed, UUIDComponent Not Found");
			return;
		}

		UUID uuid = UUID.fromString((String) uuidComponent.get("uuid"));
		Entity entity = uuidToEntity.get(uuid);

		for (Map.Entry<String, Map<String, Object>> entry : components.entrySet()) {
			String componentName = entry.getKey();
			Map<String, Object> componentData = entry.getValue();

			// Skip UUIDComponent since it's already handled
			if ("UUIDComponent".equals(componentName))
				continue;

			ComponentSerializer serializer = componentSerializerRegistry.getSerializer(componentName);
			if (serializer == null) {
				Logger.warn("SceneSerializer deserializeEntity Failed, ComponentSerializer({}) Not Found", componentName);
				continue;
			}

			Component component = serializer.deserialize(componentData, resolver);
			entity.addComponent(component);
		}
	}
}
