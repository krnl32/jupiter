package com.krnl32.jupiter.engine.script.utility;

import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.script.binder.BinderRegistry;
import com.krnl32.jupiter.engine.script.binder.components.RigidBody2DComponentBinder;

public class DefaultComponentBinders {
	public static void registerAll() {
		// Gameplay
		BinderRegistry.registerComponentBinder(RigidBody2DComponent.class, new RigidBody2DComponentBinder());
	}
}
