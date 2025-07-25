package com.krnl32.jupiter.engine.ecs;

import com.krnl32.jupiter.engine.renderer.Renderer;

public interface System {
	void onUpdate(float dt);
	void onRender(float dt, Renderer renderer);
}
