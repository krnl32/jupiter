package com.krnl32.jupiter.factory.components;

import com.krnl32.jupiter.components.CircleCollider2DComponent;
import com.krnl32.jupiter.factory.ComponentFactory;

public class CircleCollider2DComponentFactory implements ComponentFactory<CircleCollider2DComponent> {
	@Override
	public CircleCollider2DComponent create() {
		return new CircleCollider2DComponent(0.5f);
	}
}
