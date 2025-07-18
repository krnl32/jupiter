package com.krnl32.jupiter.factory.components.effects;

import com.krnl32.jupiter.components.effects.ParticleComponent;
import com.krnl32.jupiter.factory.ComponentFactory;
import org.joml.Vector3f;

public class ParticleComponentFactory implements ComponentFactory<ParticleComponent> {
	@Override
	public ParticleComponent create() {
		return new ParticleComponent(new Vector3f(0.0f, 0.0f, 0.0f), 10.0f);
	}
}
