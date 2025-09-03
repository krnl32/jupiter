package com.krnl32.jupiter.engine.systems.effects;

import com.krnl32.jupiter.engine.components.effects.ParticleComponent;
import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.engine.components.utility.DestroyComponent;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.renderer.Renderer;
import org.joml.Vector3f;

public class ParticleSystem implements System {
	public final Registry registry;

	public ParticleSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity : registry.getEntitiesWith(ParticleComponent.class, TransformComponent.class, SpriteRendererComponent.class)) {
			ParticleComponent particle = entity.getComponent(ParticleComponent.class);
			TransformComponent transform = entity.getComponent(TransformComponent.class);
			SpriteRendererComponent spriteRenderer = entity.getComponent(SpriteRendererComponent.class);

			transform.translation.add(new Vector3f(particle.velocity).mul(dt));

			particle.remainingTime -= dt;

			if (particle.remainingTime <= 0) {
				entity.addComponent(new DestroyComponent());
			}

			spriteRenderer.color.w = (spriteRenderer.color.w * (particle.remainingTime / particle.duration));
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
