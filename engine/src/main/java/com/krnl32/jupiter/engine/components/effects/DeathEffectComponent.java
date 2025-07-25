package com.krnl32.jupiter.engine.components.effects;

import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.model.Sprite;

public class DeathEffectComponent implements Component {
	public int particleCount;
	public Sprite particleSprite;

	public DeathEffectComponent(int particleCount, Sprite particleSprite) {
		this.particleCount = particleCount;
		this.particleSprite = particleSprite;
	}
}
