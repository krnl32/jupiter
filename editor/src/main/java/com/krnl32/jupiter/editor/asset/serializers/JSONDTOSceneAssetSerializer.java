package com.krnl32.jupiter.editor.asset.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializer;
import com.krnl32.jupiter.engine.asset.types.SceneAsset;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.dto.ecs.EntityDTO;
import com.krnl32.jupiter.engine.dto.scene.SceneDTO;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.mapper.EntityMapper;
import com.krnl32.jupiter.engine.mapper.SceneMapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JSONDTOSceneAssetSerializer implements AssetSerializer<SceneAsset, SceneDTO> {
	@Override
	public byte[] serialize(SceneAsset asset) {
		SceneDTO sceneDTO = new SceneDTO();
		sceneDTO.setName(asset.getScene().getName());
		sceneDTO.setSettings(SceneMapper.toDTO(asset.getScene().getSceneSettings()));

		List<EntityDTO> entityDTOs = new ArrayList<>();
		for (Entity entity : asset.getScene().getRegistry().getEntities()) {
			entityDTOs.add(EntityMapper.toDTO(entity));
		}

		sceneDTO.setEntities(entityDTOs);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsBytes(sceneDTO);
		} catch (JsonProcessingException e) {
			Logger.error("SceneAssetJSONSerializer failed to Serialize Scene({}): {}", asset.getScene().getName(), e.getMessage());
			return null;
		}
	}

	@Override
	public SceneDTO deserialize(byte[] data) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), SceneDTO.class);
		} catch (JsonProcessingException e) {
			Logger.error("SceneAssetJSONSerializer failed to Deserialize Scene: {}", e.getMessage());
			return null;
		}
	}
}
