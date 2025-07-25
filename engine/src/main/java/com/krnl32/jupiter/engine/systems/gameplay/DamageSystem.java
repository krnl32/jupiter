package com.krnl32.jupiter.engine.systems.gameplay;

import com.krnl32.jupiter.engine.components.effects.BlinkComponent;
import com.krnl32.jupiter.engine.components.gameplay.HealthComponent;
import com.krnl32.jupiter.engine.components.gameplay.TeamComponent;
import com.krnl32.jupiter.engine.components.projectile.ProjectileComponent;
import com.krnl32.jupiter.engine.components.utility.DestroyComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.physics.BeginCollisionEvent;
import com.krnl32.jupiter.engine.renderer.Renderer;

public class DamageSystem implements System {
	private final Registry registry;

	public DamageSystem(Registry registry) {
		this.registry = registry;

		EventBus.getInstance().register(BeginCollisionEvent.class, event -> {
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
