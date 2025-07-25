package com.krnl32.jupiter.engine.serializer;

import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
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
			deserializeEntity(entities.getJSONObject(i), resolver);
		}
	}

	public Scene clone(Scene scene, boolean regenUUID) {
		Scene clone = new Scene() {
			@Override
			public void onCreate() {
			}

			@Override
			public void onActivate() {
			}

			@Override
			public void onUnload() {
			}
		};

		Map<UUID, UUID> originalUUIDToNewUUID = new HashMap<>();
		Map<UUID, Entity> cloneUUIDToEntity = new HashMap<>();

		// Create Entities
		for (Entity entity : scene.getRegistry().getEntities()) {
			UUIDComponent uuidComp = entity.getComponent(UUIDComponent.class);
			if (uuidComp == null) {
				Logger.warn("SceneSerializer Clone Skipping Entity({}) without UUIDComponent", entity.getTagOrId());
				continue;
			}

			UUID originalUUID = uuidComp.uuid;
			UUID newUUID = regenUUID ? UUID.randomUUID() : originalUUID;

			Entity newEntity = clone.createEntity();
			newEntity.addComponent(new UUIDComponent(newUUID));

			originalUUIDToNewUUID.put(originalUUID, newUUID);
			cloneUUIDToEntity.put(newUUID, newEntity);
		}

		// Clone Components
		for (Entity entity : scene.getRegistry().getEntities()) {
			UUIDComponent uuidComp = entity.getComponent(UUIDComponent.class);
			if (uuidComp == null)
				continue;

			UUID originalUUID = uuidComp.uuid;
			UUID clonedUUID = originalUUIDToNewUUID.get(originalUUID);
			Entity clonedEntity = cloneUUIDToEntity.get(clonedUUID);

			for (Component component : entity.getComponents()) {
				if (component instanceof UUIDComponent)
					continue;

				ComponentSerializer componentSerializer = SerializerRegistry.getComponentSerializer(component.getClass());
				if (componentSerializer != null) {
					Component clonedComponent = componentSerializer.clone(component);
					clonedEntity.addComponent(clonedComponent);
				}
			}
		}

		return clone;
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

	private void deserializeEntity(JSONObject data, EntityResolver resolver) {
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
