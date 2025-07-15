package com.krnl32.jupiter.factory.components.physics;

import com.krnl32.jupiter.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.factory.ComponentFactory;
import com.krnl32.jupiter.physics.BodyType;

public class Rigidbody2DComponentFactory implements ComponentFactory<RigidBody2DComponent> {
	@Override
	public RigidBody2DComponent create() {
		return new RigidBody2DComponent(BodyType.DYNAMIC);
	}
}
