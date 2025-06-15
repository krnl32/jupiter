package com.github.krnl32.jupiter.serializer;

import com.github.krnl32.jupiter.ecs.Component;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.game.Scene;
import org.json.JSONArray;
import org.json.JSONObject;

public class SceneSerializer {
	public JSONObject serialize(Scene scene) {
		JSONArray entities = new JSONArray();
		for(Entity entity : scene.getRegistry().getEntities())
			entities.put(serializeEntity(entity));
		return new JSONObject().put("entities", entities);
	}

	public void deserialize(JSONObject data, Scene scene) {
		JSONArray entities = data.getJSONArray("entities");
		for (int i = 0; i < entities.length(); i++) {
			deserializeEntity(entities.getJSONObject(i), scene);
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

	private void deserializeEntity(JSONObject data, Scene scene) {
		Entity entity = scene.createEntity();
		JSONObject components = data.getJSONObject("components");
		for (String key: components.keySet()) {
			for (var entry : SerializerRegistry.getComponentSerializers().entrySet()) {
				if (entry.getKey().getSimpleName().equals(key)) {
					Component component = entry.getValue().deserialize(components.getJSONObject(key));
					entity.addComponent(component);
					break;
				}
			}
		}
	}
}
