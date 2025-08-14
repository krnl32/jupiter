package com.krnl32.jupiter.engine.script.lua;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.script.ScriptBindings;
import com.krnl32.jupiter.engine.script.ScriptContext;
import com.krnl32.jupiter.engine.script.ScriptDefinition;
import com.krnl32.jupiter.engine.script.ScriptSettings;
import com.krnl32.jupiter.engine.script.lua.types.LuaEntity;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.nio.file.Path;

public class LuaScriptDefinition implements ScriptDefinition {
	private final Path scriptPath;
	private final ScriptSettings settings;

	public LuaScriptDefinition(Path scriptPath, ScriptSettings settings) {
		this.scriptPath = scriptPath;
		this.settings = settings;
	}

	@Override
	public Path getScriptPath() {
		return scriptPath;
	}

	@Override
	public ScriptSettings getSettings() {
		return settings;
	}

	@Override
	public ScriptBindings createBindings(ScriptContext scriptContext) {
		Globals globals = JsePlatform.standardGlobals();
		globals.set("entity", new LuaEntity(scriptContext));

		try {
			LuaValue chunk = globals.loadfile(scriptPath.toString());
			chunk.call();
		} catch (Exception e) {
			Logger.error("ScriptDefinition createBindings Failed to Call Script({}): {}", scriptPath, e.getMessage());
			return null;
		}

		return new LuaScriptBindings(
			globals.get("onInit").isfunction() ? globals.get("onInit").checkfunction() : null,
			globals.get("onDestroy").isfunction() ? globals.get("onDestroy").checkfunction() : null,
			globals.get("onUpdate").isfunction() ? globals.get("onUpdate").checkfunction() : null
		);
	}
}
