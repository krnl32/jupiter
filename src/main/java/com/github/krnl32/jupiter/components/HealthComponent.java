package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.ecs.Component;

public class HealthComponent implements Component {
	public float maxHealth;
	public float currentHealth;

	public HealthComponent(float maxHealth, float currentHealth) {
		this.maxHealth = maxHealth;
		this.currentHealth = currentHealth;
	}
}
