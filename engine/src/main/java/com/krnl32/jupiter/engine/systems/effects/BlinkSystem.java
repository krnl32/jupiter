package com.krnl32.jupiter.engine.systems.effects;

import com.krnl32.jupiter.engine.components.effects.BlinkComponent;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.renderer.Renderer;

public class BlinkSystem implements System {
	private final Registry registry;

	public BlinkSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity : registry.getEntitiesWith(BlinkComponent.class)) {
			BlinkComponent blink = entity.getComponent(BlinkComponent.class);
			blink.elapsedTime += dt;
			blink.blinkTime += dt;

			if (blink.blinkTime >= blink.interval) {
				blink.visible = !blink.visible;
				blink.blinkTime = 0.0f;
			}

			if (blink.elapsedTime >= blink.duration)
				entity.removeComponent(BlinkComponent.class);
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
