package com.krnl32.jupiter.engine.asset.importsettings.types;

import com.krnl32.jupiter.engine.asset.importsettings.ImportSettings;
import com.krnl32.jupiter.engine.scene.SceneSettings;

import java.util.HashMap;
import java.util.Map;

public class SceneAssetImportSettings implements ImportSettings {
	private final SceneSettings settings;

	public SceneAssetImportSettings(SceneSettings settings) {
		this.settings = settings;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> data = new HashMap<>();
		data.put("gravity", settings.getGravity());

		return data;
	}

	@Override
	public void fromMap(Map<String, Object> data) {
		settings.setGravity((float) data.get("gravity"));
	}

	public SceneSettings getSettings() {
		return settings;
	}
}
