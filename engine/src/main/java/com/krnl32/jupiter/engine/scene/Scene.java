package com.krnl32.jupiter.engine.scene;

import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.renderer.Renderer;
import com.krnl32.jupiter.engine.systems.effects.BlinkSystem;
import com.krnl32.jupiter.engine.systems.effects.DeathEffectSystem;
import com.krnl32.jupiter.engine.systems.effects.ParticleSystem;
import com.krnl32.jupiter.engine.systems.gameplay.DamageSystem;
import com.krnl32.jupiter.engine.systems.gameplay.ForceMovementSystem;
import com.krnl32.jupiter.engine.systems.gameplay.HealthSystem;
import com.krnl32.jupiter.engine.systems.gameplay.ScriptSystem;
import com.krnl32.jupiter.engine.systems.input.KeyboardControlSystem;
import com.krnl32.jupiter.engine.systems.physics.Physics2DSystem;
import com.krnl32.jupiter.engine.systems.projectile.ProjectileEmitterSystem;
import com.krnl32.jupiter.engine.systems.renderer.CameraSystem;
import com.krnl32.jupiter.engine.systems.renderer.RenderSystem;
import com.krnl32.jupiter.engine.systems.ui.*;
import com.krnl32.jupiter.engine.systems.utility.DestroySystem;
import com.krnl32.jupiter.engine.systems.utility.LifetimeSystem;

public abstract class Scene {
	private final Registry registry = new Registry();
	private boolean initialized = false;

	public void onUpdate(float dt) {
		registry.onUpdate(dt);
	}

	public void onRender(float dt, Renderer renderer) {
		registry.onRender(dt, renderer);
	}

	public final void load() {
		if (!initialized) {
			registerDefaultSystems();
			onCreate();
			initialized = true;
		}
		onActivate();
	}

	public abstract void onCreate();
	public abstract void onActivate();
	public abstract void onUnload();

	public Registry getRegistry() {
		return registry;
	}

	public Entity createEntity() {
		return registry.createEntity();
	}

	public void destroyEntity(Entity entity) {
		registry.destroyEntity(entity);
	}

	public void addSystem(System system) {
		registry.addSystem(system);
	}

	public void addSystem(System system, int priority, boolean enabled) {
		registry.addSystem(system, priority, enabled);
	}

	public void removeSystem(Class<? extends System> system) {
		registry.removeSystem(system);
	}

	public void setSystemEnabled(Class<? extends System> system, boolean enabled) {
		registry.setSystemEnabled(system, enabled);
	}

	protected void registerDefaultSystems() {
		addSystem(new KeyboardControlSystem(getRegistry()), 1, true);
		addSystem(new UIInputSystem(getRegistry()), 2, true);
		addSystem(new ScriptSystem(getRegistry()));
		addSystem(new UIButtonSystem(getRegistry()));
		addSystem(new ForceMovementSystem(getRegistry()));
		addSystem(new Physics2DSystem(getRegistry()));
		addSystem(new ProjectileEmitterSystem(getRegistry()));
		addSystem(new DamageSystem(getRegistry()));
		addSystem(new HealthSystem(getRegistry()));
		addSystem(new LifetimeSystem(getRegistry()));
		addSystem(new DestroySystem(getRegistry()));
		addSystem(new BlinkSystem(getRegistry()));
		addSystem(new CameraSystem(getRegistry()), 50, true);
		addSystem(new ParticleSystem(getRegistry()));
		addSystem(new DeathEffectSystem(getRegistry()));
		addSystem(new UILayoutSystem(getRegistry()));
		addSystem(new UIScrollSystem(getRegistry()));
		addSystem(new UITextRenderSystem(getRegistry()));
		addSystem(new UIRenderSystem(getRegistry()));
		addSystem(new RenderSystem(getRegistry()));
	}
}
