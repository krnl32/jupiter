package com.krnl32.jupiter.ecs;

import com.krnl32.jupiter.renderer.Renderer;

public interface System {
	void onUpdate(float dt);
	void onRender(float dt, Renderer renderer);
}
