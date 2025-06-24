package com.github.krnl32.jupiter.serializer;

import com.github.krnl32.jupiter.components.UUIDComponent;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.ecs.Component;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.game.Scene;
import com.github.krnl32.jupiter.serializer.resolvers.EntityResolver;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SceneSerializer {
	private final Map<UUID, Entity> uuidToEntity = new HashMap<>();

	public JSONObject serialize(Scene scene) {
		JSONArray entities = new JSONArray();
		for(Entity entity : scene.getRegistry().getEntities())
			entities.put(serializeEntity(entity));
		return new JSONObject().put("entities", entities);
	}

	public void deserialize(JSONObject data, Scene scene) {
		JSONArray entities = data.getJSONArray("entities");

		// Create Entities with UUIDComponents
		for (int i = 0; i < entities.length(); i++) {
			JSONObject components = entities.getJSONObject(i).getJSONObject("components");
			JSONObject uuidComponent = components.getJSONObject("UUIDComponent");
			if (uuidComponent == null) {
				Logger.error("SceneSerializer Deserialize Failed, UUIDComponent not Found for Entity({})", i);
				return;
			}
			UUID uuid = UUID.fromString(uuidComponent.getString("uuid"));

			Entity entity = scene.createEntity();
			entity.addComponent(new UUIDComponent(uuid));
			uuidToEntity.put(uuid, entity);
		}

		// Resolve UUIDs
		EntityResolver resolver = uuid -> {
			Entity entity = uuidToEntity.get(uuid);
			if (entity == null)
				Logger.error("SceneSerializer Deserialize Failed, UUID Not Found for Entity({})");
			return entity;
		};

		for (int i = 0; i < entities.length(); i++) {
			deserializeEntity(entities.getJSONObject(i), scene, resolver);
		}
	}

	private JSONObject serializeEntity(Entity entity) {
		JSONObject componentsObj = new JSONObject();
		for(Component component: entity.getComponents()) {
			if (SerializerRegistry.hasComponentSerializer(component.getClass())) {
				@SuppressWarnings("unchecked")
				ComponentSerializer<Component> serializer = (ComponentSerializer<Component>) SerializerRegistry.getComponentSerializer(component.getClass());
				componentsObj.put(component.getClass().getSimpleName(), serializer.serialize(component));
			}
		}
		return new JSONObject().put("components", componentsObj);
	}

	private void deserializeEntity(JSONObject data, Scene scene, EntityResolver resolver) {
		JSONObject components = data.getJSONObject("components");
		UUID uuid = UUID.fromString(components.getJSONObject("UUIDComponent").getString("uuid"));
		Entity entity = uuidToEntity.get(uuid);

		for (String key: components.keySet()) {
			if (key.equals("UUIDComponent"))
				continue;

			for (var entry : SerializerRegistry.getComponentSerializers().entrySet()) {
				if (entry.getKey().getSimpleName().equals(key)) {
					Component component = entry.getValue().deserialize(components.getJSONObject(key), resolver);
					entity.addComponent(component);
					break;
				}
			}
		}
	}
}
