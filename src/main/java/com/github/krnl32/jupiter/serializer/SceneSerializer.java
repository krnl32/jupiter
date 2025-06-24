package com.github.krnl32.jupiter.serializer;

import com.github.krnl32.jupiter.components.IDComponent;
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

		// Create Entities with IDComponents
		for (int i = 0; i < entities.length(); i++) {
			JSONObject components = entities.getJSONObject(i).getJSONObject("components");
			JSONObject idComponent = components.getJSONObject("IDComponent");
			if (idComponent == null) {
				Logger.error("SceneSerializer Deserialize Failed, IDComponent not Found for Entity({})", i);
				return;
			}
			UUID uuid = UUID.fromString(idComponent.getString("id"));

			Entity entity = scene.createEntity();
			entity.addComponent(new IDComponent(uuid));
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
		UUID uuid = UUID.fromString(components.getJSONObject("IDComponent").getString("id"));
		Entity entity = uuidToEntity.get(uuid);

		for (String key: components.keySet()) {
			if (key.equals("IDComponent"))
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
