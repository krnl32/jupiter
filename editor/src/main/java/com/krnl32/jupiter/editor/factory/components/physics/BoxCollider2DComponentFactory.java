package com.krnl32.jupiter.editor.factory.components.physics;

import com.krnl32.jupiter.engine.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.editor.factory.ComponentFactory;
import org.joml.Vector2f;

public class BoxCollider2DComponentFactory implements ComponentFactory<BoxCollider2DComponent> {
	@Override
	public BoxCollider2DComponent create() {
		return new BoxCollider2DComponent(new Vector2f(1.0f, 1.0f));
	}
}
