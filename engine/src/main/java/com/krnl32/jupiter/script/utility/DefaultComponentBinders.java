package com.krnl32.jupiter.script.utility;

import com.krnl32.jupiter.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.script.binder.BinderRegistry;
import com.krnl32.jupiter.script.binder.components.RigidBody2DComponentBinder;

public class DefaultComponentBinders {
	public static void registerAll() {
		// Gameplay
		BinderRegistry.registerComponentBinder(RigidBody2DComponent.class, new RigidBody2DComponentBinder());
	}
}
