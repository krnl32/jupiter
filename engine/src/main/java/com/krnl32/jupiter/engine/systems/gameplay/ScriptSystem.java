package com.krnl32.jupiter.engine.systems.gameplay;

import com.krnl32.jupiter.engine.asset.types.ScriptAsset;
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
				if (bindings != null) {
					try {
						bindings.onDestroy();
					} catch (Exception e) {
						Logger.warn("ScriptSystem Script({}) onDestroy Error for Entity({}): {}", script.getScriptAssetId(), event.getEntity().getTagOrId(), e.getMessage());
					}
				}
			}
		});
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity : registry.getEntitiesWith(ScriptComponent.class)) {
			ScriptComponent scriptComponent = entity.getComponent(ScriptComponent.class); // NEVER EXECUTED WHY?

			for (ScriptInstance script : scriptComponent.scripts) {
				ScriptAsset scriptAsset = ProjectContext.getInstance().getAssetManager().getAsset(script.getScriptAssetId());
				if (scriptAsset == null)
					continue;

				File scriptFile = new File(scriptAsset.getDefinition().getScriptPath().toString());
				if (!scriptFile.exists())
					continue;

				// Hot Reloadable Scripts
				ScriptContext scriptContext = new ScriptContext(entity, script);
				ScriptBindings bindings = scriptBindings.get(scriptContext);
				if (bindings == null || script.getLastModified() != scriptFile.lastModified()) {
					Logger.info("ScriptSystem Hot Reloading Script({}) for Entity({})", scriptAsset.getAssetPath(), entity.getTagOrId());

					bindings = scriptAsset.getDefinition().createBindings(scriptContext);
					if (bindings == null) {
						Logger.error("ScriptSystem Script({}) Loading Error for Entity({}): Failed to Create Bindings, disabling script...", scriptAsset.getAssetPath(), entity.getTagOrId());
						script.setDisabled(true);
						continue;
					}

					scriptBindings.put(scriptContext, bindings);
					script.setLastModified(scriptFile.lastModified());
					script.setInitialized(false);
				}

				// Handle Script onInit
				if (!script.isInitialized() && !script.isDisabled()) {
					try {
						if (bindings.onInit()) {
							script.setInitialized(true);
						}
					} catch (Exception e) {
						Logger.error("ScriptSystem Script({}) onInit Error for Entity({}): {}, disabling script...", scriptAsset.getAssetPath(), entity.getTagOrId(), e.getMessage());
						script.setDisabled(true);
					}
				}

				// Handle onUpdate
				if (!script.isDisabled()) {
					try {
						bindings.onUpdate(dt);
					} catch (Exception e) {
						Logger.error("ScriptSystem Script({}) onUpdate Error for Entity({}): {}, Disabling Script...", scriptAsset.getAssetPath(), entity.getTagOrId(), e.getMessage());
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
