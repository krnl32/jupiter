package com.github.krnl32.jupiter.systems;

import com.github.krnl32.jupiter.components.*;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.ecs.Registry;
import com.github.krnl32.jupiter.ecs.System;
import com.github.krnl32.jupiter.input.Input;
import com.github.krnl32.jupiter.renderer.Renderer;
import com.github.krnl32.jupiter.utility.Timer;
import org.joml.Vector3f;

import static java.lang.Math.toRadians;

public class ProjectileEmitterSystem implements System {
	private final Registry registry;

	public ProjectileEmitterSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		float currentTime = Timer.getTimeSeconds();

		for (Entity entity : registry.getEntitiesWith(TransformComponent.class, ProjectileEmitterComponent.class)) {
			TransformComponent transform = entity.getComponent(TransformComponent.class);
			ProjectileEmitterComponent emitter = entity.getComponent(ProjectileEmitterComponent.class);

			boolean fire = false;

			if (emitter.shootKey == null) {
				if (currentTime - emitter.lastEmissionTime >= 1.0f / emitter.fireRate)
					fire = true;
			}

			if (emitter.shootKey != null && Input.getInstance().isKeyDown(emitter.shootKey))
				fire = true;

			if (fire && currentTime - emitter.lastEmissionTime >= 1.0f / emitter.fireRate) {
				emitter.lastEmissionTime = currentTime;
				spawnProjectile(entity, transform, emitter);
			}
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}

	private void spawnProjectile(Entity owner, TransformComponent transform, ProjectileEmitterComponent emitter) {
		float angle = (float) toRadians(transform.rotation.z);
		float directionX = (float) Math.cos(angle);
		float directionY = (float) Math.sin(angle);
		Vector3f direction = new Vector3f(directionX, directionY, 0.0f);
		Vector3f velocity = new Vector3f(direction).mul(emitter.projectileSpeed);

		Entity projectile = registry.createEntity();
		projectile.addComponent(new TransformComponent(new Vector3f(transform.translation), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(transform.scale.x / 3, transform.scale.y / 3, transform.scale.z / 3)));
		projectile.addComponent(new RigidBodyComponent(velocity));
		projectile.addComponent(new ProjectileComponent(owner, 10.0f));
		projectile.addComponent(new LifetimeComponent(0.5f));
		projectile.addComponent(new SpriteRendererComponent(emitter.sprite.getIndex(), emitter.sprite.getColor(), emitter.sprite.getTextureAssetID()));
	}
}
