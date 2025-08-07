package com.krnl32.jupiter.engine.script;

import com.krnl32.jupiter.engine.oldAsset.AssetID;
import com.krnl32.jupiter.engine.ecs.Entity;

import java.util.Objects;

public class ScriptContext {
	private final Entity entity;
	private final ScriptInstance script;

	public ScriptContext(Entity entity, ScriptInstance script) {
		this.entity = entity;
		this.script = script;
	}

	public Entity getEntity() {
		return entity;
	}

	public AssetID getScriptAssetID() {
		return script.getScriptAssetID();
	}

	public boolean isInitialized() {
		return script.isInitialized();
	}

	public void setInitialized(boolean initialized) {
		script.setInitialized(initialized);
	}

	public boolean isDisabled() {
		return script.isDisabled();
	}

	public void setDisabled(boolean disabled) {
		script.setDisabled(disabled);
	}

	public long getLastModified() {
		return script.getLastModified();
	}

	public void setLastModified(long lastModified) {
		script.setLastModified(lastModified);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ScriptContext that = (ScriptContext) o;
		return Objects.equals(entity, that.entity) && Objects.equals(script, that.script);
	}

	@Override
	public int hashCode() {
		return Objects.hash(entity, script);
	}
}
