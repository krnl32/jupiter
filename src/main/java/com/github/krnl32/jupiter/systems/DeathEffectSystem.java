package com.github.krnl32.jupiter.systems;

import com.github.krnl32.jupiter.components.DeathEffectComponent;
import com.github.krnl32.jupiter.components.ParticleComponent;
import com.github.krnl32.jupiter.components.SpriteRendererComponent;
import com.github.krnl32.jupiter.components.TransformComponent;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.ecs.Registry;
import com.github.krnl32.jupiter.ecs.System;
import com.github.krnl32.jupiter.event.EventBus;
import com.github.krnl32.jupiter.events.game.EntityDeathEvent;
import com.github.krnl32.jupiter.model.Sprite;
import com.github.krnl32.jupiter.renderer.Renderer;
import org.joml.Vector3f;

public class DeathEffectSystem implements System {
	private final Registry registry;

	public DeathEffectSystem(Registry registry) {
		this.registry = registry;

		EventBus.getInstance().register(EntityDeathEvent.class, event -> {
			Entity entity = event.getEntity();
			TransformComponent transform = entity.getComponent(TransformComponent.class);
			DeathEffectComponent deathEffect = entity.getComponent(DeathEffectComponent.class);

			if (transform != null && deathEffect != null)
				spawnParticles(transform.translation, deathEffect.particleCount, deathEffect.particleSprite);
		});
	}

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}

	private void spawnParticles(Vector3f position, int count, Sprite sprite) {
		for (int i = 0; i < count; i++) {
			float speed = 2.0f + (float) Math.random() * 3.0f;
			float angle = (float) (Math.random() * Math.PI * 2);
			Vector3f velocity = new Vector3f((float) Math.cos(angle), (float) Math.sin(angle), 0).mul(speed);

			Entity particle = registry.createEntity();
			particle.addComponent(new TransformComponent(new Vector3f(position), new Vector3f(0, 0, 0), new Vector3f(0.5f, 0.5f, 0.5f)));
			particle.addComponent(new ParticleComponent(velocity, 10 + (float) Math.random() * 0.5f));
			particle.addComponent(new SpriteRendererComponent(sprite.getIndex(), sprite.getColor(), sprite.getTextureAssetID()));
		}
	}
}
