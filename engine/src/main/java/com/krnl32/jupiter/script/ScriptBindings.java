package com.krnl32.jupiter.script;

import org.luaj.vm2.LuaFunction;

public class ScriptBindings {
	private final LuaFunction onInit;
	private final LuaFunction onDestroy;
	private final LuaFunction onUpdate;

	public ScriptBindings(LuaFunction onInit, LuaFunction onDestroy, LuaFunction onUpdate) {
		this.onInit = onInit;
		this.onDestroy = onDestroy;
		this.onUpdate = onUpdate;
	}

	public LuaFunction onInit() {
		return onInit;
	}

	public LuaFunction onDestroy() {
		return onDestroy;
	}

	public LuaFunction onUpdate() {
		return onUpdate;
	}
}
