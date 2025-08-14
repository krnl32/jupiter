package com.krnl32.jupiter.engine.script.lua;

import com.krnl32.jupiter.engine.script.ScriptBindings;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;

public class LuaScriptBindings implements ScriptBindings {
	private final LuaFunction onInit;
	private final LuaFunction onDestroy;
	private final LuaFunction onUpdate;

	public LuaScriptBindings(LuaFunction onInit, LuaFunction onDestroy, LuaFunction onUpdate) {
		this.onInit = onInit;
		this.onDestroy = onDestroy;
		this.onUpdate = onUpdate;
	}

	@Override
	public boolean onInit() throws LuaError {
		if (onInit != null) {
			return onInit.call().toboolean();
		}
		return false;
	}

	@Override
	public void onDestroy() throws LuaError {
		if (onDestroy != null) {
			onDestroy.call();
		}
	}

	@Override
	public void onUpdate(float dt) throws LuaError {
		if (onUpdate != null) {
			onUpdate.call(LuaValue.valueOf(dt));
		}
	}
}
