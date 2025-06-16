package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.ecs.Component;
import com.github.krnl32.jupiter.ecs.Entity;

public class ProjectileComponent implements Component {
	public Entity owner;
	public float damage;

	public ProjectileComponent(Entity owner, float damage) {
		this.owner = owner;
		this.damage = damage;
	}
}
