package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.ecs.Component;
import com.github.krnl32.jupiter.input.KeyCode;
import com.github.krnl32.jupiter.model.Sprite;

public class ProjectileEmitterComponent implements Component {
	public KeyCode shootKey;
	public float fireRate;
	public float projectileSpeed;
	public float lastEmissionTime;
	public Sprite sprite;

	public ProjectileEmitterComponent(KeyCode shootKey, float fireRate, float projectileSpeed, Sprite sprite) {
		this.shootKey = shootKey;
		this.fireRate = fireRate;
		this.projectileSpeed = projectileSpeed;
		this.lastEmissionTime = 0.0f;
		this.sprite = sprite;
	}
}
