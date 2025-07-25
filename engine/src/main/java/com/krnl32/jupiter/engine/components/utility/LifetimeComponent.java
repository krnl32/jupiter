package com.krnl32.jupiter.engine.components.utility;

import com.krnl32.jupiter.engine.ecs.Component;

public class LifetimeComponent implements Component {
	public float remainingTime;

	public LifetimeComponent(float remainingTime) {
		this.remainingTime = remainingTime;
	}
}
