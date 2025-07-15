package com.krnl32.jupiter.factory.components;

import com.krnl32.jupiter.components.HealthComponent;
import com.krnl32.jupiter.factory.ComponentFactory;

public class HealthComponentFactory implements ComponentFactory<HealthComponent> {
	@Override
	public HealthComponent create() {
		return new HealthComponent(100.0f, 100.0f);
	}
}
