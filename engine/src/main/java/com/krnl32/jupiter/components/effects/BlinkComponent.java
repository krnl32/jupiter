package com.krnl32.jupiter.components.effects;

import com.krnl32.jupiter.ecs.Component;

public class BlinkComponent implements Component {
	public float duration;
	public float interval;
	public float elapsedTime;
	public float blinkTime;
	public boolean visible;

	public BlinkComponent(float duration, float interval) {
		this.duration = duration;
		this.interval = interval;
		this.elapsedTime = 0.0f;
		this.blinkTime = 0.0f;
		this.visible = true;
	}

	public BlinkComponent(float duration, float interval, float elapsedTime, float blinkTime, boolean visible) {
		this.duration = duration;
		this.interval = interval;
		this.elapsedTime = elapsedTime;
		this.blinkTime = blinkTime;
		this.visible = visible;
	}
}
