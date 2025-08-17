package com.krnl32.jupiter.engine.asset.importsettings.types;

import com.krnl32.jupiter.engine.asset.importsettings.ImportSettings;
import com.krnl32.jupiter.engine.scene.SceneSettings;
import com.krnl32.jupiter.engine.serializer.SceneSerializerUtility;

import java.util.Map;

public class SceneAssetImportSettings implements ImportSettings {
	private final SceneSettings settings;

	public SceneAssetImportSettings(SceneSettings settings) {
		this.settings = settings;
	}

	@Override
	public Map<String, Object> toMap() {
		return SceneSerializerUtility.serializerSceneSettings(settings);
	}

	@Override
	public void fromMap(Map<String, Object> data) {
		SceneSettings sceneSettings = SceneSerializerUtility.deserializeSceneSettings(data);
		settings.setPhysicsSettings(sceneSettings.getPhysicsSettings());
	}

	public SceneSettings getSettings() {
		return settings;
	}
}
