package com.krnl32.jupiter.components.gameplay;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.ecs.Component;

public class ScriptComponent implements Component {
	public AssetID scriptAssetID;
	public boolean initialized;
	public boolean disabled;
	public long lastModified;

	public ScriptComponent(AssetID scriptAssetID) {
		this.scriptAssetID = scriptAssetID;
		this.initialized = false;
		this.disabled = false;
		this.lastModified = 0;
	}

	public ScriptComponent(AssetID scriptAssetID, boolean disabled) {
		this.scriptAssetID = scriptAssetID;
		this.initialized = false;
		this.disabled = disabled;
		this.lastModified = 0;
	}

	public ScriptComponent(AssetID scriptAssetID, boolean initialized, boolean disabled, long lastModified) {
		this.scriptAssetID = scriptAssetID;
		this.initialized = initialized;
		this.disabled = disabled;
		this.lastModified = lastModified;
	}
}
