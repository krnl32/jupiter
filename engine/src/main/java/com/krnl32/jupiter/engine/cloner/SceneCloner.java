package com.krnl32.jupiter.engine.cloner;

import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.scene.Scene;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SceneCloner {
	public static Scene clone(Scene scene, boolean regenUUID) {
		Scene clonedScene = new Scene(scene.getName() + "_clone", scene.getSceneSettings().clone()) {
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

		Map<UUID, UUID> originalToCloneUUID = new HashMap<>();
		Map<UUID, Entity> cloneUUIDToEntity = new HashMap<>();

		// Create Entities
		for (Entity entity : scene.getRegistry().getEntities()) {
			UUIDComponent uuidComp = entity.getComponent(UUIDComponent.class);
			if (uuidComp == null) {
				Logger.warn("SceneCloner Skipping Entity({}) without UUIDComponent", entity.getTagOrId());
				continue;
			}

			UUID originalUUID = uuidComp.uuid;
			UUID newUUID = regenUUID ? UUID.randomUUID() : originalUUID;

			Entity newEntity = clonedScene.createEntity();
			newEntity.addComponent(new UUIDComponent(newUUID));

			originalToCloneUUID.put(originalUUID, newUUID);
			cloneUUIDToEntity.put(newUUID, newEntity);
		}

		// Clone Components
		for (Entity entity : scene.getRegistry().getEntities()) {
			UUIDComponent uuidComp = entity.getComponent(UUIDComponent.class);
			if (uuidComp == null)
				continue;

			UUID originalUUID = uuidComp.uuid;
			UUID cloneUUID = originalToCloneUUID.get(originalUUID);
			Entity clonedEntity = cloneUUIDToEntity.get(cloneUUID);

			for (Component component : entity.getComponents()) {
				if (component instanceof UUIDComponent)
					continue;

				ComponentCloner componentCloner = ComponentClonerRegistry.getCloner(component.getClass());
				if (componentCloner != null) {
					Component clonedComponent = componentCloner.clone(component);
					clonedEntity.addComponent(clonedComponent);
				}
			}
		}

		return clonedScene;
	}
}
