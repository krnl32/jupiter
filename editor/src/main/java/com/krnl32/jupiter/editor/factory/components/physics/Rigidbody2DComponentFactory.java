package com.krnl32.jupiter.editor.factory.components.physics;

import com.krnl32.jupiter.editor.factory.ComponentFactory;
import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.physics.BodyType;

public class Rigidbody2DComponentFactory implements ComponentFactory<RigidBody2DComponent> {
	@Override
	public RigidBody2DComponent create() {
		return new RigidBody2DComponent(BodyType.DYNAMIC);
	}
}
