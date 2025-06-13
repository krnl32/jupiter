package com.github.krnl32.jupiter.ecs;

import com.github.krnl32.jupiter.renderer.Renderer;

public abstract class System {
	private final Registry registry;

	public System(Registry registry) {
		this.registry = registry;
	}

	public Registry getRegistry() {
		return registry;
	}

	public abstract void onUpdate(float dt);
	public abstract void onRender(float dt, Renderer renderer);
}
