package com.krnl32.jupiter.systems.gameplay;

import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.types.ScriptAsset;
import com.krnl32.jupiter.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.entity.EntityDestroyedEvent;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.script.utility.DefaultComponentBinders;
import org.luaj.vm2.LuaValue;

import java.io.File;

public class ScriptSystem implements System {
	private final Registry registry;

	public ScriptSystem(Registry registry) {
		this.registry = registry;
		DefaultComponentBinders.registerAll();

		// Handle onDestroy
		EventBus.getInstance().register(EntityDestroyedEvent.class, event -> {
			ScriptComponent script = event.getEntity().getComponent(ScriptComponent.class);
			if (script != null) {
				try {
					script.onDestroy.call();
				} catch (Exception e) {
					Logger.warn("ScriptEngine onDestroy Error on Entity({}): {}", event.getEntity().getTagOrId(), e.getMessage());
				}
			}
		});
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity : registry.getEntitiesWith(ScriptComponent.class)) {
			ScriptComponent script = entity.getComponent(ScriptComponent.class);

			// Hot Reloadable Scripts
			ScriptAsset scriptAsset = AssetManager.getInstance().getAsset(script.scriptAssetID);
			if (scriptAsset != null) {
				File scriptFile = new File(scriptAsset.getScriptDefinition().getScriptPath());
				if (scriptFile.exists()) {
					long lastModified = scriptFile.lastModified();
					if (lastModified != script.lastModified) {
						Logger.info("ScriptSystem: Hot Reloading Script({}) for Entity({})", scriptAsset.getScriptDefinition().getScriptPath(), entity.getTagOrId());
						ScriptComponent reloaded = scriptAsset.getScriptDefinition().createComponent(entity);
						script.onInit = reloaded.onInit;
						script.onUpdate = reloaded.onUpdate;
						script.onDestroy = reloaded.onDestroy;
						script.lastModified = reloaded.lastModified;
						script.initialized = false;
						script.disabled = false;
					}
				}
			}

			// Handle Script onInit
			if (script.onInit != null && !script.initialized && !script.disabled) {
				try {
					script.onInit.call();
					script.initialized = true;
				} catch (Exception e) {
					Logger.error("ScriptEngine onInit Error on Entity({}): {}, disabling script...", entity.getTagOrId(), e.getMessage());
					script.disabled = true;
				}
			}

			// Handle onUpdate
			if (!script.disabled && script.onUpdate != null) {
				try {
					script.onUpdate.call(LuaValue.valueOf(dt));
				} catch (Exception e) {
					Logger.error("ScriptEngine onUpdate Error on Entity({}): {}, Disabling Script...", entity.getTagOrId(), e.getMessage());
					script.disabled = true;
				}
			}
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
