package com.krnl32.jupiter.engine.script.lua.utility;

import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.script.binder.ComponentBinderRegistry;
import com.krnl32.jupiter.engine.script.lua.binders.components.LuaRigidBody2DComponentBinder;

public class LuaComponentBinders {
	public static void registerAll() {
		// Gameplay
		ComponentBinderRegistry.register(RigidBody2DComponent.class, new LuaRigidBody2DComponentBinder());
	}
}
