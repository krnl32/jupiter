package com.krnl32.jupiter.script.utility;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.script.types.LuaEntity;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;

public class ScriptDefinition {
	private final AssetID scriptAssetID;
	private final String scriptPath;

	public ScriptDefinition(AssetID scriptAssetID, String scriptPath) {
		this.scriptAssetID = scriptAssetID;
		this.scriptPath = scriptPath;
	}

	public AssetID getScriptAssetID() {
		return scriptAssetID;
	}

	public String getScriptPath() {
		return scriptPath;
	}

	public ScriptComponent createComponent(Entity entity) {
		Globals globals = JsePlatform.standardGlobals();
		globals.set("entity", new LuaEntity(entity));

		try {
			LuaValue chunk = globals.loadfile(scriptPath);
			chunk.call();
		} catch (Exception e) {
			Logger.error("ScriptDefinition createComponent Failed to Call Script({}) Lua Chunk: {}", scriptPath, e.getMessage());
			return null;
		}

		return new ScriptComponent(
			scriptAssetID,
			globals.get("onInit").isfunction() ? globals.get("onInit").checkfunction() : null,
			globals.get("onUpdate").isfunction() ? globals.get("onUpdate").checkfunction() : null,
			globals.get("onDestroy").isfunction() ? globals.get("onDestroy").checkfunction() : null,
			false,
			false,
			new File(scriptPath).lastModified()
		);
	}
}
