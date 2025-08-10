package com.krnl32.jupiter.engine.mapper;

import com.krnl32.jupiter.engine.dto.scene.SceneSettingsDTO;
import com.krnl32.jupiter.engine.scene.SceneSettings;

public class SceneMapper {
	public static SceneSettingsDTO toDTO(SceneSettings settings) {
		return new SceneSettingsDTO(settings.getGravity());
	}

	public static SceneSettings fromDTO(SceneSettingsDTO dto) {
		return new SceneSettings(dto.getGravity());
	}
}
