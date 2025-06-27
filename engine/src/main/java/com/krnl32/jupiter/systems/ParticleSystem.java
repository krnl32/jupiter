package com.krnl32.jupiter.systems;

import com.krnl32.jupiter.components.DestroyComponent;
import com.krnl32.jupiter.components.ParticleComponent;
import com.krnl32.jupiter.components.SpriteRendererComponent;
import com.krnl32.jupiter.components.TransformComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.renderer.Renderer;
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
			if (particle.remainingTime <= 0)
				entity.addComponent(new DestroyComponent());

			spriteRenderer.color.w = (spriteRenderer.color.w * (particle.remainingTime / particle.duration));
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
