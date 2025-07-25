package com.krnl32.jupiter.editor.factory.components.physics;

import com.krnl32.jupiter.editor.factory.ComponentFactory;
import com.krnl32.jupiter.engine.components.physics.CircleCollider2DComponent;

public class CircleCollider2DComponentFactory implements ComponentFactory<CircleCollider2DComponent> {
	@Override
	public CircleCollider2DComponent create() {
		return new CircleCollider2DComponent(0.5f);
	}
}
