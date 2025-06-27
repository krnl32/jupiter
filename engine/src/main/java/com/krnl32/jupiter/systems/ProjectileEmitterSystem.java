package com.krnl32.jupiter.systems;

import com.krnl32.jupiter.components.*;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.input.Input;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.utility.Timer;
import org.joml.Vector3f;

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
		float angle = (-transform.rotation.z);
		float directionX = (float) Math.sin(angle);
		float directionY = (float) Math.cos(angle);
		Vector3f direction = new Vector3f(directionX, directionY, 0.0f).normalize();
		Vector3f velocity = new Vector3f(direction).mul(emitter.projectileSpeed);

		Entity projectile = registry.createEntity();
		projectile.addComponent(new TransformComponent(new Vector3f(transform.translation), new Vector3f(transform.rotation), new Vector3f(transform.scale.x / 3, transform.scale.y / 3, transform.scale.z / 3)));
		projectile.addComponent(new RigidBodyComponent(velocity));
		projectile.addComponent(new ProjectileComponent(owner, 10.0f));
		projectile.addComponent(new LifetimeComponent(1.0f));
		projectile.addComponent(new SpriteRendererComponent(emitter.sprite.getIndex(), emitter.sprite.getColor(), emitter.sprite.getTextureAssetID()));
		projectile.addComponent(new BoxColliderComponent(new Vector3f(1.0f, 1.0f, 1.0f)));

		if (owner.hasComponent(TeamComponent.class)) {
			TeamComponent team = owner.getComponent(TeamComponent.class);
			projectile.addComponent(new TeamComponent(team.teamID));
		}
	}
}
