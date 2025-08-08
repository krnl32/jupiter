package com.krnl32.jupiter.engine.script;

import com.krnl32.jupiter.engine.asset.handle.AssetId;

import java.util.Objects;

public class ScriptInstance {
	private AssetId scriptAssetId;
	private boolean initialized;
	private boolean disabled;
	private long lastModified;

	public ScriptInstance(AssetId scriptAssetId) {
		this.scriptAssetId = scriptAssetId;
		this.initialized = false;
		this.disabled = false;
		this.lastModified = 0;
	}

	public ScriptInstance(AssetId scriptAssetId, boolean disabled) {
		this.scriptAssetId = scriptAssetId;
		this.initialized = false;
		this.disabled = disabled;
		this.lastModified = 0;
	}

	public ScriptInstance(AssetId scriptAssetId, boolean initialized, boolean disabled, long lastModified) {
		this.scriptAssetId = scriptAssetId;
		this.initialized = initialized;
		this.disabled = disabled;
		this.lastModified = lastModified;
	}

	public AssetId getScriptAssetId() {
		return scriptAssetId;
	}

	public void setScriptAssetId(AssetId scriptAssetId) {
		this.scriptAssetId = scriptAssetId;
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
		return initialized == that.initialized && disabled == that.disabled && lastModified == that.lastModified && Objects.equals(scriptAssetId, that.scriptAssetId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(scriptAssetId, initialized, disabled, lastModified);
	}
}
