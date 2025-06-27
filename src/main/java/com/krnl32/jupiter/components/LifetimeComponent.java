package com.krnl32.jupiter.components;

import com.krnl32.jupiter.ecs.Component;

public class LifetimeComponent implements Component {
	public float remainingTime;

	public LifetimeComponent(float remainingTime) {
		this.remainingTime = remainingTime;
	}
}
