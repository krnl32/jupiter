package com.krnl32.jupiter.components.effects;

import com.krnl32.jupiter.ecs.Component;
import com.krnl32.jupiter.model.Sprite;

public class DeathEffectComponent implements Component {
	public int particleCount;
	public Sprite particleSprite;

	public DeathEffectComponent(int particleCount, Sprite particleSprite) {
		this.particleCount = particleCount;
		this.particleSprite = particleSprite;
	}
}
