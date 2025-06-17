package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.ecs.Component;
import com.github.krnl32.jupiter.ecs.Entity;

public class ProjectileComponent implements Component {
	public Entity owner;
	public float damage;
	public boolean canHitOwner;

	public ProjectileComponent(Entity owner, float damage) {
		this.owner = owner;
		this.damage = damage;
		this.canHitOwner = false;
	}

	public ProjectileComponent(Entity owner, float damage, boolean canHitOwner) {
		this.owner = owner;
		this.damage = damage;
		this.canHitOwner = canHitOwner;
	}
}
