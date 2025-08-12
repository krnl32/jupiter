package com.krnl32.jupiter.engine.asset.types;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.script.ScriptDefinition;
import com.krnl32.jupiter.engine.script.ScriptSettings;

public class ScriptAsset extends Asset {
	private final String assetPath;
	private final ScriptSettings settings;
	private final ScriptDefinition definition;

	public ScriptAsset(String assetPath, ScriptSettings settings, ScriptDefinition definition) {
		super(AssetType.SCRIPT);
		this.assetPath = assetPath;
		this.settings = settings;
		this.definition = definition;
	}

	public ScriptAsset(AssetId assetId, String assetPath, ScriptSettings settings, ScriptDefinition definition) {
		super(assetId, AssetType.SCRIPT);
		this.assetPath = assetPath;
		this.settings = settings;
		this.definition = definition;
	}

	public String getAssetPath() {
		return assetPath;
	}

	public ScriptSettings getSettings() {
		return settings;
	}

	public ScriptDefinition getDefinition() {
		return definition;
	}
}
