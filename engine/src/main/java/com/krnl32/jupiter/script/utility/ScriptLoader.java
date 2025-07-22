package com.krnl32.jupiter.script.utility;

import com.krnl32.jupiter.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.script.types.LuaEntity;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;

public class ScriptLoader {
	public static ScriptComponent loadScript(String filepath, Entity entity) {
		Globals globals = JsePlatform.standardGlobals();
		globals.set("entity", new LuaEntity(entity));

		LuaValue chunk = globals.loadfile(filepath);
		chunk.call();

		return new ScriptComponent(filepath,
			globals.get("onInit").isfunction() ? globals.get("onInit").checkfunction() : null,
			globals.get("onUpdate").isfunction() ? globals.get("onUpdate").checkfunction() : null,
			globals.get("onDestroy").isfunction() ? globals.get("onDestroy").checkfunction() : null,
			false,
			false,
			new File(filepath).lastModified()
		);
	}
}
