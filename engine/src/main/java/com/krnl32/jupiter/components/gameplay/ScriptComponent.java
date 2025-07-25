package com.krnl32.jupiter.components.gameplay;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.ecs.Component;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ScriptComponent that = (ScriptComponent) o;
		return initialized == that.initialized && disabled == that.disabled && lastModified == that.lastModified && Objects.equals(scriptAssetID, that.scriptAssetID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(scriptAssetID, initialized, disabled, lastModified);
	}
}
