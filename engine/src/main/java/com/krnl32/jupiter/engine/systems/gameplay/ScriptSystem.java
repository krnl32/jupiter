package com.krnl32.jupiter.engine.systems.gameplay;

import com.krnl32.jupiter.engine.oldAsset.types.ScriptAsset;
import com.krnl32.jupiter.engine.components.gameplay.ScriptComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.entity.EntityDestroyedEvent;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.renderer.Renderer;
import com.krnl32.jupiter.engine.script.ScriptBindings;
import com.krnl32.jupiter.engine.script.ScriptContext;
import com.krnl32.jupiter.engine.script.ScriptInstance;
import com.krnl32.jupiter.engine.script.utility.DefaultComponentBinders;
import org.luaj.vm2.LuaValue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ScriptSystem implements System {
	private final Registry registry;
	private final Map<ScriptContext, ScriptBindings> scriptBindings;

	public ScriptSystem(Registry registry) {
		this.registry = registry;
		this.scriptBindings = new HashMap<>();

		DefaultComponentBinders.registerAll();

		// Handle onDestroy
		EventBus.getInstance().register(EntityDestroyedEvent.class, event -> {
			ScriptComponent scriptComponent = event.getEntity().getComponent(ScriptComponent.class);
			if (scriptComponent == null)
				return;

			for (ScriptInstance script : scriptComponent.scripts) {
				ScriptBindings bindings = scriptBindings.get(new ScriptContext(event.getEntity(), script));
				if (bindings != null && bindings.onDestroy() != null) {
					try {
						bindings.onDestroy().call();
					} catch (Exception e) {
						Logger.warn("ScriptSystem Script({}) onDestroy Error for Entity({}): {}", script.getScriptAssetID(), event.getEntity().getTagOrId(), e.getMessage());
					}
				}

			}
		});
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity : registry.getEntitiesWith(ScriptComponent.class)) {
			ScriptComponent scriptComponent = entity.getComponent(ScriptComponent.class);

			for (ScriptInstance script : scriptComponent.scripts) {
				ScriptAsset scriptAsset = ProjectContext.getInstance().getAssetManager().getAsset(script.getScriptAssetID());
				if (scriptAsset == null)
					continue;

				File scriptFile = new File(scriptAsset.getAbsolutePath());
				if (!scriptFile.exists())
					continue;

				// Hot Reloadable Scripts
				ScriptContext scriptContext = new ScriptContext(entity, script);
				ScriptBindings bindings = scriptBindings.get(scriptContext);
				if (bindings == null || script.getLastModified() != scriptFile.lastModified()) {
					Logger.info("ScriptSystem Hot Reloading Script({}) for Entity({})", scriptAsset.getRelativePath(), entity.getTagOrId());

					bindings = scriptAsset.getScriptDefinition().createBindings(scriptContext);
					if (bindings == null) {
						Logger.error("ScriptSystem Script({}) Loading Error for Entity({}): Failed to Create Bindings, disabling script...", scriptAsset.getRelativePath(), entity.getTagOrId());
						script.setDisabled(true);
						continue;
					}

					scriptBindings.put(scriptContext, bindings);
					script.setLastModified(scriptFile.lastModified());
					script.setInitialized(false);
					script.setDisabled(false);
				}

				// Handle Script onInit
				if (bindings.onInit() != null && !script.isInitialized() && !script.isDisabled()) {
					try {
						bindings.onInit().call();
						script.setInitialized(true);
					} catch (Exception e) {
						Logger.error("ScriptSystem Script({}) onInit Error for Entity({}): {}, disabling script...", scriptAsset.getRelativePath(), entity.getTagOrId(), e.getMessage());
						script.setDisabled(true);
					}
				}

				// Handle onUpdate
				if (bindings.onUpdate() != null && !script.isDisabled()) {
					try {
						bindings.onUpdate().call(LuaValue.valueOf(dt));
					} catch (Exception e) {
						Logger.error("ScriptSystem Script({}) onUpdate Error for Entity({}): {}, Disabling Script...", scriptAsset.getRelativePath(), entity.getTagOrId(), e.getMessage());
						script.setDisabled(true);
					}
				}
			}
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
