package com.krnl32.jupiter.engine.script;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.script.types.LuaEntity;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.nio.file.Path;

public class ScriptDefinition {
	private final Path scriptPath;
	private final ScriptSettings settings;

	public ScriptDefinition(Path scriptPath, ScriptSettings settings) {
		this.scriptPath = scriptPath;
		this.settings = settings;
	}

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

		return new ScriptBindings(
			globals.get("onInit").isfunction() ? globals.get("onInit").checkfunction() : null,
			globals.get("onDestroy").isfunction() ? globals.get("onDestroy").checkfunction() : null,
			globals.get("onUpdate").isfunction() ? globals.get("onUpdate").checkfunction() : null
		);
	}

	public Path getScriptPath() {
		return scriptPath;
	}

	public ScriptSettings getSettings() {
		return settings;
	}
}
