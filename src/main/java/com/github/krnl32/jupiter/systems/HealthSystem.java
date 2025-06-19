package com.github.krnl32.jupiter.systems;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.TextureAsset;
import com.github.krnl32.jupiter.components.*;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.ecs.Registry;
import com.github.krnl32.jupiter.ecs.System;
import com.github.krnl32.jupiter.renderer.Renderer;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class HealthSystem implements System {
	private final Registry registry;
	private final AssetID starParticleID;

	public HealthSystem(Registry registry) {
		this.registry = registry;

		starParticleID = AssetManager.getInstance().registerAndLoad("textures/particles/star3.png", () -> new TextureAsset("particles/star3.png"));
		if (starParticleID == null)
			Logger.critical("Game Failed to Load Texture Asset({})", "textures/particles/star3.png");
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity: registry.getEntitiesWith(HealthComponent.class)) {
			HealthComponent health = entity.getComponent(HealthComponent.class);
			if (health.currentHealth <= 0 && !entity.hasComponent(DestroyComponent.class)) {
				spawnExplosion(entity);
				entity.addComponent(new DestroyComponent());
				Logger.info("Entity({}) Died!", entity.getTagOrId());
			}
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}

	private void spawnExplosion(Entity entity) {
		TransformComponent transform = entity.getComponent(TransformComponent.class);

		int particleCount = 20;
		for (int i = 0; i < particleCount; i++) {
			Entity particle = registry.createEntity();

			Vector3f pos = new Vector3f(transform.translation);

			float speed = 2.0f + (float) Math.random() * 3.0f;
			float angle = (float) (Math.random() * Math.PI * 2);
			Vector3f velocity = new Vector3f((float) Math.cos(angle), (float) Math.sin(angle), 0).mul(speed);

			particle.addComponent(new TransformComponent(pos, new Vector3f(0, 0, 0), new Vector3f(0.5f, 0.5f, 0.5f)));
			particle.addComponent(new ParticleComponent(velocity, 1.0f + (float) Math.random() * 0.5f));
			particle.addComponent(new SpriteRendererComponent(0, new Vector4f(1.0f, 0.45f, 0.0f, 0.95f), starParticleID));
		}
	}
}
