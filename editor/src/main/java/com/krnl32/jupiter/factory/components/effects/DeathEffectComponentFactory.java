package com.krnl32.jupiter.factory.components.effects;

import com.krnl32.jupiter.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.factory.ComponentFactory;
import com.krnl32.jupiter.model.Sprite;
import org.joml.Vector4f;

public class DeathEffectComponentFactory implements ComponentFactory<DeathEffectComponent> {
	@Override
	public DeathEffectComponent create() {
		return new DeathEffectComponent(20, new Sprite(-1, new Vector4f(1.0f, 0.45f, 0.0f, 0.95f), null));
	}
}
