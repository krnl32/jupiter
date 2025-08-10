package com.krnl32.jupiter.engine.asset.loader.types;

import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.loader.AssetLoader;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializer;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializerRegistry;
import com.krnl32.jupiter.engine.asset.types.SceneAsset;
import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.dto.ecs.EntityDTO;
import com.krnl32.jupiter.engine.dto.scene.SceneDTO;
import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.mapper.SceneMapper;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.ComponentSerializerRegistry;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.utility.FileIO;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DTOSceneAssetLoader implements AssetLoader<SceneAsset> {
	@Override
	public SceneAsset load(AssetMetadata assetMetadata) {
		try {
			AssetSerializer<SceneAsset, SceneDTO> assetSerializer = AssetSerializerRegistry.getSerializer(assetMetadata.getAssetType());
			if (assetSerializer == null) {
				Logger.error("SceneAssetDTOLoader Failed to Load Asset({}): No Serializer for Type({})", assetMetadata.getAssetId(), assetMetadata.getAssetType());
				return null;
			}

			// Get Raw Asset Data
			Path assetPath = ProjectContext.getInstance().getAssetDirectory().resolve(assetMetadata.getSourcePath());
			byte[] assetRawData = FileIO.readFileContentBytes(assetPath);

			SceneDTO sceneDTO = assetSerializer.deserialize(assetRawData);
			if (sceneDTO == null) {
				Logger.error("SceneAssetDTOLoader AssetSerializer Failed to Deserialize Asset({})", assetMetadata.getAssetId());
				return null;
			}

			// Setup Scene
			Scene scene = new Scene(sceneDTO.getName(), SceneMapper.fromDTO(sceneDTO.getSettings())) {
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

			// Prepare for Entity Resolver
			Map<UUID, Entity> uuidToEntity = new HashMap<>();

			// Create Empty Entities with UUIDComponents
			for (int i = 0; i < sceneDTO.getEntities().size(); i++) {
				var components = sceneDTO.getEntities().get(i).getComponents();
				Map<String, Object> uuidComponent = components.get("UUIDComponent");
				if (uuidComponent == null) {
					Logger.error("DTOSceneAssetLoader Failed to Load Scene({}), UUIDComponent not Found for Entity({})", sceneDTO.getName(), i);
					return null;
				}

				UUID uuid = UUID.fromString(uuidComponent.get("uuid").toString());

				Entity entity = scene.createEntity();
				entity.addComponent(new UUIDComponent(uuid));
				uuidToEntity.put(uuid, entity);
			}

			// Setup Entity Resolver for UUID Entity Lookup
			EntityResolver resolver = uuid -> {
				Entity entity = uuidToEntity.get(uuid);
				if (entity == null) {
					Logger.error("EntityResolver Failed for Scene({}), UUID Not Found for Entity({})", sceneDTO.getName(), uuid.toString());
					return null;
				}
				return entity;
			};

			sceneDTO.getEntities().forEach(entityDTO -> {
				loadEntityDTO(entityDTO, uuidToEntity, resolver);
			});

			return new SceneAsset(assetMetadata.getAssetId(), scene);
		} catch (Exception e) {
			Logger.error("SceneAssetDTOLoader Failed to Load Asset({}): {}", assetMetadata.getAssetId(), e.getMessage());
			return null;
		}
}

	@Override
	public void unload(SceneAsset asset) {
	}

	private void loadEntityDTO(EntityDTO entityDTO, Map<UUID, Entity> uuidToEntity, EntityResolver resolver) {
		Map<String, Object> uuidComponent = entityDTO.getComponents().get("UUIDComponent");
		UUID uuid = UUID.fromString(uuidComponent.get("uuid").toString());
		Entity entity = uuidToEntity.get(uuid);

		for (var componentEntry : entityDTO.getComponents().entrySet()) {
			String componentName = componentEntry.getKey();
			Map<String, Object> componentData = componentEntry.getValue();

			// Skip, we already added this earlier to setup resolver
			if (componentName.equals("UUIDComponent")) {
				continue;
			}

			ComponentSerializer<Component, Map<String, Object>> serializer = ComponentSerializerRegistry.getSerializer(componentName);
			if (serializer != null) {
				entity.addComponent(serializer.deserialize(componentData, resolver));
			}
		}
	}
}
