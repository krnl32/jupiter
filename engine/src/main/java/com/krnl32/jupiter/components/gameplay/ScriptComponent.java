package com.krnl32.jupiter.components.gameplay;

import com.krnl32.jupiter.ecs.Component;
import org.luaj.vm2.LuaFunction;

public class ScriptComponent implements Component {
	public String scriptPath;
	public LuaFunction onInit;
	public LuaFunction onUpdate;

	public ScriptComponent(String scriptPath) {
		this.scriptPath = scriptPath;
		this.onInit = null;
		this.onUpdate = null;
	}

	public ScriptComponent(String scriptPath, LuaFunction onInit, LuaFunction onUpdate) {
		this.scriptPath = scriptPath;
		this.onInit = onInit;
		this.onUpdate = onUpdate;
	}
}
