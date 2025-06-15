package com.github.krnl32.jupiter.ecs;

import com.github.krnl32.jupiter.renderer.Renderer;

public interface System {
	void onUpdate(float dt);
	void onRender(float dt, Renderer renderer);
}
