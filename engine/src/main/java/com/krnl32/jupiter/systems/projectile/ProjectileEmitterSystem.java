package com.krnl32.jupiter.systems.projectile;

import com.krnl32.jupiter.components.gameplay.TeamComponent;
import com.krnl32.jupiter.components.gameplay.TransformComponent;
import com.krnl32.jupiter.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.components.projectile.ProjectileComponent;
import com.krnl32.jupiter.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.components.utility.LifetimeComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.input.Input;
import com.krnl32.jupiter.physics.BodyType;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.utility.Timer;
import org.joml.Vector2f;
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
		// Calculate Forward Direction
		float angle = (-transform.rotation.z);
		float directionX = (float) Math.sin(angle);
		float directionY = (float) Math.cos(angle);
		Vector3f direction = new Vector3f(directionX, directionY, 0.0f).normalize();

		// Offset Projectile Position
		Vector3f projectileTranslation = new Vector3f(transform.translation).add(new Vector3f(direction).mul(0.5f));
		Vector3f projectileScale = new Vector3f(transform.scale).div(3);

		// Initial Velocity
		Vector2f velocity = new Vector2f(direction).mul(emitter.projectileSpeed);

		// Spawn Projectile
		Entity projectile = registry.createEntity();
		projectile.addComponent(new TransformComponent(projectileTranslation, new Vector3f(transform.rotation), projectileScale));
		projectile.addComponent(new RigidBody2DComponent(BodyType.KINEMATIC, velocity, 0.0f, 0.0f, 0.1f, 0.0f, true, true));
		projectile.addComponent(new BoxCollider2DComponent(new Vector2f(projectileScale.x, projectileScale.y), new Vector2f(0.0f, 0.0f), 0.0f, 0.0f, true));
		projectile.addComponent(new ProjectileComponent(owner, 10.0f));
		projectile.addComponent(new LifetimeComponent(1.0f));
		projectile.addComponent(new SpriteRendererComponent(emitter.sprite.getIndex(), emitter.sprite.getColor(), emitter.sprite.getTextureAssetID()));

		if (owner.hasComponent(TeamComponent.class)) {
			TeamComponent team = owner.getComponent(TeamComponent.class);
			projectile.addComponent(new TeamComponent(team.teamID));
		}
	}
}
