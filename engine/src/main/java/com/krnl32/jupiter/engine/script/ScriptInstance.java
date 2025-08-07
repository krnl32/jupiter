package com.krnl32.jupiter.engine.script;

import com.krnl32.jupiter.engine.oldAsset.AssetID;

import java.util.Objects;

public class ScriptInstance {
	private AssetID scriptAssetID;
	private boolean initialized;
	private boolean disabled;
	private long lastModified;

	public ScriptInstance(AssetID scriptAssetID) {
		this.scriptAssetID = scriptAssetID;
		this.initialized = false;
		this.disabled = false;
		this.lastModified = 0;
	}

	public ScriptInstance(AssetID scriptAssetID, boolean disabled) {
		this.scriptAssetID = scriptAssetID;
		this.initialized = false;
		this.disabled = disabled;
		this.lastModified = 0;
	}

	public ScriptInstance(AssetID scriptAssetID, boolean initialized, boolean disabled, long lastModified) {
		this.scriptAssetID = scriptAssetID;
		this.initialized = initialized;
		this.disabled = disabled;
		this.lastModified = lastModified;
	}

	public AssetID getScriptAssetID() {
		return scriptAssetID;
	}

	public void setScriptAssetID(AssetID scriptAssetID) {
		this.scriptAssetID = scriptAssetID;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ScriptInstance that = (ScriptInstance) o;
		return initialized == that.initialized && disabled == that.disabled && lastModified == that.lastModified && Objects.equals(scriptAssetID, that.scriptAssetID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(scriptAssetID, initialized, disabled, lastModified);
	}
}
