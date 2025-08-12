package com.krnl32.jupiter.engine.asset.importsettings.types;

import com.krnl32.jupiter.engine.asset.importsettings.ImportSettings;
import com.krnl32.jupiter.engine.script.ScriptSettings;

import java.util.HashMap;
import java.util.Map;

public class ScriptAssetImportSettings implements ImportSettings {
	private final ScriptSettings settings;

	public ScriptAssetImportSettings(ScriptSettings settings) {
		this.settings = settings;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> data = new HashMap<>();
		data.put("hotReload", settings.isHotReload());
		data.put("compile", settings.isCompile());
		return data;
	}

	@Override
	public void fromMap(Map<String, Object> data) {
		settings.setHotReload((boolean) data.get("hotReload"));
		settings.setCompile((boolean) data.get("compile"));
	}

	public ScriptSettings getSettings() {
		return settings;
	}
}
