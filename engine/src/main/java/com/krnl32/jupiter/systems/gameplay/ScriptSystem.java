package com.krnl32.jupiter.systems.gameplay;

import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.types.ScriptAsset;
import com.krnl32.jupiter.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.components.gameplay.ScriptsComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.entity.EntityDestroyedEvent;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.script.ScriptBindings;
import com.krnl32.jupiter.script.ScriptKey;
import com.krnl32.jupiter.script.utility.DefaultComponentBinders;
import org.luaj.vm2.LuaValue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ScriptSystem implements System {
	private final Registry registry;
	private final Map<ScriptKey, ScriptBindings> scriptBindings;

	public ScriptSystem(Registry registry) {
		this.registry = registry;
		this.scriptBindings = new HashMap<>();

		DefaultComponentBinders.registerAll();

		// Handle onDestroy
		EventBus.getInstance().register(EntityDestroyedEvent.class, event -> {
			ScriptsComponent scripts = event.getEntity().getComponent(ScriptsComponent.class);
			if (scripts == null)
				return;

			for (ScriptComponent script : scripts.scripts) {
				ScriptBindings bindings = scriptBindings.get(new ScriptKey(event.getEntity(), script));
				if (bindings != null && bindings.onDestroy() != null) {
					try {
						bindings.onDestroy().call();
					} catch (Exception e) {
						Logger.warn("ScriptSystem Script({}) onDestroy Error for Entity({}): {}", script.scriptAssetID, event.getEntity().getTagOrId(), e.getMessage());
					}
				}

			}
		});
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity : registry.getEntitiesWith(ScriptsComponent.class)) {
			ScriptsComponent scripts = entity.getComponent(ScriptsComponent.class);

			for (ScriptComponent script : scripts.scripts) {
				ScriptAsset scriptAsset = AssetManager.getInstance().getAsset(script.scriptAssetID);
				if (scriptAsset == null)
					continue;

				File scriptFile = new File(scriptAsset.getAbsolutePath());
				if (!scriptFile.exists())
					continue;

				// Hot Reloadable Scripts
				ScriptKey key = new ScriptKey(entity, script);
				ScriptBindings bindings = scriptBindings.get(key);
				if (bindings == null || script.lastModified != scriptFile.lastModified()) {
					Logger.info("ScriptSystem Hot Reloading Script({}) for Entity({})", scriptAsset.getRelativePath(), entity.getTagOrId());
					bindings = scriptAsset.getScriptDefinition().createBindings(entity);
					if (bindings == null) {
						Logger.error("ScriptSystem Script({}) Loading Error for Entity({}): Failed to Create Bindings, disabling script...", scriptAsset.getRelativePath(), entity.getTagOrId());
						script.disabled = true;
						continue;
					}
					scriptBindings.put(key, bindings);
					script.lastModified = scriptFile.lastModified();
					script.initialized = false;
					script.disabled = false;
				}

				// Handle Script onInit
				if (bindings.onInit() != null && !script.initialized && !script.disabled) {
					try {
						bindings.onInit().call();
						script.initialized = true;
					} catch (Exception e) {
						Logger.error("ScriptSystem Script({}) onInit Error for Entity({}): {}, disabling script...", scriptAsset.getRelativePath(), entity.getTagOrId(), e.getMessage());
						script.disabled = true;
					}
				}

				// Handle onUpdate
				if (bindings.onUpdate() != null && !script.disabled) {
					try {
						bindings.onUpdate().call(LuaValue.valueOf(dt));
					} catch (Exception e) {
						Logger.error("ScriptSystem Script({}) onUpdate Error for Entity({}): {}, Disabling Script...", scriptAsset.getRelativePath(), entity.getTagOrId(), e.getMessage());
						script.disabled = true;
					}
				}
			}
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
