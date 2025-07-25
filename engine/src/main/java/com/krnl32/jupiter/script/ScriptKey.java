package com.krnl32.jupiter.script;

import com.krnl32.jupiter.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.ecs.Entity;

import java.util.Objects;

public class ScriptKey {
	private final Entity entity;
	private final ScriptComponent script;

	public ScriptKey(Entity entity, ScriptComponent script) {
		this.entity = entity;
		this.script = script;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ScriptKey scriptKey = (ScriptKey) o;
		return Objects.equals(entity, scriptKey.entity) && Objects.equals(script, scriptKey.script);
	}

	@Override
	public int hashCode() {
		return Objects.hash(entity, script);
	}
}
