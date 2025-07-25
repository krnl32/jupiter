package com.krnl32.jupiter.script;

import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.script.types.LuaEntity;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class ScriptDefinition {
	private final String scriptPath;

	public ScriptDefinition(String scriptPath) {
		this.scriptPath = scriptPath;
	}

	public ScriptBindings createBindings(ScriptContext scriptContext) {
		Globals globals = JsePlatform.standardGlobals();
		globals.set("entity", new LuaEntity(scriptContext));

		try {
			LuaValue chunk = globals.loadfile(scriptPath);
			chunk.call();
		} catch (Exception e) {
			Logger.error("ScriptDefinition createBindings Failed to Call Script({}): {}", scriptPath, e.getMessage());
			return null;
		}

		return new ScriptBindings(
			globals.get("onInit").isfunction() ? globals.get("onInit").checkfunction() : null,
			globals.get("onDestroy").isfunction() ? globals.get("onDestroy").checkfunction() : null,
			globals.get("onUpdate").isfunction() ? globals.get("onUpdate").checkfunction() : null
		);
	}
}
