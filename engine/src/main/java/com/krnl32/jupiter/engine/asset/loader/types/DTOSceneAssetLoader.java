package com.krnl32.jupiter.engine.asset.loader.types;

import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;
import com.krnl32.jupiter.engine.asset.loader.AssetLoader;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializer;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializerRegistry;
import com.krnl32.jupiter.engine.asset.types.SceneAsset;
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
import com.krnl32.jupiter.engine.utility.FileIO;

import java.nio.file.Path;
import java.util.Map;

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

			sceneDTO.getEntities().forEach(entityDTO -> {
				loadEntityDTO(scene, entityDTO);
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

	private void loadEntityDTO(Scene scene, EntityDTO dto) {
		Entity entity = scene.createEntity();

		for (var componentEntry : dto.getComponents().entrySet()) {
			String componentName = componentEntry.getKey();
			Map<String, Object> componentData = componentEntry.getValue();

			ComponentSerializer<Component, Map<String, Object>> serializer = ComponentSerializerRegistry.getSerializer(componentName);
			if (serializer != null) {
				entity.addComponent(serializer.deserialize(componentData));
			}
		}
	}
}
