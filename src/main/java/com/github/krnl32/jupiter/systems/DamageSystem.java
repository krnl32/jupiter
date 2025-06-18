package com.github.krnl32.jupiter.systems;

import com.github.krnl32.jupiter.components.*;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.ecs.Registry;
import com.github.krnl32.jupiter.ecs.System;
import com.github.krnl32.jupiter.event.EventBus;
import com.github.krnl32.jupiter.events.physics.CollisionEvent;
import com.github.krnl32.jupiter.renderer.Renderer;

public class DamageSystem implements System {
	private final Registry registry;

	public DamageSystem(Registry registry) {
		this.registry = registry;

		EventBus.getInstance().register(CollisionEvent.class, event -> {
			if (event.getEntityA().hasComponent(ProjectileComponent.class) && event.getEntityB().hasComponent(HealthComponent.class)) {
				applyDamage(event.getEntityA(), event.getEntityB());
			} else if (event.getEntityB().hasComponent(ProjectileComponent.class) && event.getEntityA().hasComponent(HealthComponent.class)) {
				applyDamage(event.getEntityB(), event.getEntityA());
			}
		});
	}

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}

	private void applyDamage(Entity projectileEntity, Entity targetEntity) {
		ProjectileComponent projectile = projectileEntity.getComponent(ProjectileComponent.class);

		if (projectile.owner.equals(targetEntity) && !projectile.canHitOwner)
			return;

		if (projectileEntity.hasComponent(TeamComponent.class) && targetEntity.hasComponent(TeamComponent.class)) {
			TeamComponent projectileTeam = projectileEntity.getComponent(TeamComponent.class);
			TeamComponent targetTeam = targetEntity.getComponent(TeamComponent.class);
			if (projectileTeam.teamID == targetTeam.teamID)
				return;
		}

		HealthComponent health = targetEntity.getComponent(HealthComponent.class);
		health.currentHealth -= projectile.damage;

		projectileEntity.addComponent(new DestroyComponent());

		if (!targetEntity.hasComponent(BlinkComponent.class))
			targetEntity.addComponent(new BlinkComponent(0.3f, 0.05f));

		Logger.info("Entity({}) hit Entity({}) {} Damage", projectile.owner.getTagOrId(), targetEntity.getTagOrId(), projectile.damage);
	}
}
