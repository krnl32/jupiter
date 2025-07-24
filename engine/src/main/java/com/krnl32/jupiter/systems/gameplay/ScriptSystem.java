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
import com.krnl32.jupiter.script.ScriptBindings;
import com.krnl32.jupiter.script.utility.DefaultComponentBinders;
import org.luaj.vm2.LuaValue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ScriptSystem implements System {
	private final Registry registry;
	private final Map<Entity, ScriptBindings> scriptBindings;

	public ScriptSystem(Registry registry) {
		this.registry = registry;
		this.scriptBindings = new HashMap<>();

		DefaultComponentBinders.registerAll();

		// Handle onDestroy
		EventBus.getInstance().register(EntityDestroyedEvent.class, event -> {
			ScriptBindings bindings = scriptBindings.get(event.getEntity());
			if (bindings != null && bindings.onDestroy() != null) {
				try {
					bindings.onDestroy().call();
				} catch (Exception e) {
					Logger.warn("ScriptSystem onDestroy Error for Entity({}): {}", event.getEntity().getTagOrId(), e.getMessage());
				}
			}
		});
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity : registry.getEntitiesWith(ScriptComponent.class)) {
			ScriptComponent script = entity.getComponent(ScriptComponent.class);

			ScriptAsset scriptAsset = AssetManager.getInstance().getAsset(script.scriptAssetID);
			if (scriptAsset == null)
				continue;

			File scriptFile = new File(scriptAsset.getAbsolutePath());
			if (!scriptFile.exists())
				continue;

			// Hot Reloadable Scripts
			ScriptBindings bindings = scriptBindings.get(entity);
			if (bindings == null || script.lastModified != scriptFile.lastModified()) {
				Logger.info("ScriptSystem Hot Reloading Script({}) for Entity({})", scriptAsset.getRelativePath(), entity.getTagOrId());
				bindings = scriptAsset.getScriptDefinition().createBindings(entity);
				scriptBindings.put(entity, bindings);
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
					Logger.error("ScriptSystem onInit Error for Entity({}): {}, disabling script...", entity.getTagOrId(), e.getMessage());
					script.disabled = true;
				}
			}

			// Handle onUpdate
			if (bindings.onUpdate() != null && !script.disabled) {
				try {
					bindings.onUpdate().call(LuaValue.valueOf(dt));
				} catch (Exception e) {
					Logger.error("ScriptSystem onUpdate Error for Entity({}): {}, Disabling Script...", entity.getTagOrId(), e.getMessage());
					script.disabled = true;
				}
			}
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
