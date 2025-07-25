package com.krnl32.jupiter.engine.components.projectile;

import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.input.devices.KeyCode;
import com.krnl32.jupiter.engine.model.Sprite;

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

	public ProjectileEmitterComponent(KeyCode shootKey, float fireRate, float projectileSpeed, float lastEmissionTime, Sprite sprite) {
		this.shootKey = shootKey;
		this.fireRate = fireRate;
		this.projectileSpeed = projectileSpeed;
		this.lastEmissionTime = lastEmissionTime;
		this.sprite = sprite;
	}
}
