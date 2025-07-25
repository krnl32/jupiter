package com.krnl32.jupiter.editor.factory.components.effects;

import com.krnl32.jupiter.editor.factory.ComponentFactory;
import com.krnl32.jupiter.engine.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.engine.model.Sprite;
import org.joml.Vector4f;

public class DeathEffectComponentFactory implements ComponentFactory<DeathEffectComponent> {
	@Override
	public DeathEffectComponent create() {
		return new DeathEffectComponent(20, new Sprite(-1, new Vector4f(1.0f, 0.45f, 0.0f, 0.95f), null));
	}
}
