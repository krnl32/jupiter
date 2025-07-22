package com.krnl32.jupiter.systems.gameplay;

import com.krnl32.jupiter.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.renderer.Renderer;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class ScriptSystem implements System {
	private final Registry registry;
	private final Globals luaGlobals;

	public ScriptSystem(Registry registry) {
		this.registry = registry;
		this.luaGlobals = JsePlatform.standardGlobals();
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity : registry.getEntitiesWith(ScriptComponent.class)) {
			ScriptComponent script = entity.getComponent(ScriptComponent.class);
			if (script.onUpdate == null) {
				try {
					luaGlobals.loadfile(script.scriptPath).call();
					script.onInit = luaGlobals.get("onInit").isfunction() ? luaGlobals.get("onInit").checkfunction() : null;
					script.onUpdate = luaGlobals.get("onUpdate").isfunction() ? luaGlobals.get("onUpdate").checkfunction() : null;

					script.onInit.call();
				} catch (Exception e) {
					Logger.error("ScriptSystem Failed to Load Script: " + script.scriptPath);
				}
			}

			try {
				script.onUpdate.call(LuaValue.valueOf(dt));
			} catch (Exception e) {
				Logger.error("ScriptEngine Lua Script Error on Entity({}): {}", entity.getTagOrId(), e.getMessage());
				// disable script or mark for reload
			}
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
