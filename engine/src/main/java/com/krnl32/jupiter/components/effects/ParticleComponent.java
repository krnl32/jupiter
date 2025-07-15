package com.krnl32.jupiter.components.effects;

import com.krnl32.jupiter.ecs.Component;
import org.joml.Vector3f;

public class ParticleComponent implements Component {
	public Vector3f velocity;
	public float duration;
	public float remainingTime;

	public ParticleComponent(Vector3f velocity, float duration) {
		this.velocity = velocity;
		this.duration = duration;
		this.remainingTime = duration;
	}

	public ParticleComponent(Vector3f velocity, float duration, float remainingTime) {
		this.velocity = velocity;
		this.duration = duration;
		this.remainingTime = remainingTime;
	}
}
