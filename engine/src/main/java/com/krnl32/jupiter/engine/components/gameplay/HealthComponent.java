package com.krnl32.jupiter.engine.components.gameplay;

import com.krnl32.jupiter.engine.ecs.Component;

public class HealthComponent implements Component {
	public float maxHealth;
	public float currentHealth;

	public HealthComponent(float maxHealth, float currentHealth) {
		this.maxHealth = maxHealth;
		this.currentHealth = currentHealth;
	}
}
