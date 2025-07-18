package com.krnl32.jupiter.factory.components.utility;

import com.krnl32.jupiter.components.utility.LifetimeComponent;
import com.krnl32.jupiter.factory.ComponentFactory;

public class LifetimeComponentFactory implements ComponentFactory<LifetimeComponent> {
	@Override
	public LifetimeComponent create() {
		return new LifetimeComponent(10.0f);
	}
}
