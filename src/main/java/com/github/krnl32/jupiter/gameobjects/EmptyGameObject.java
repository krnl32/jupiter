package com.github.krnl32.jupiter.gameobjects;

import com.github.krnl32.jupiter.game.GameObject;
import com.github.krnl32.jupiter.renderer.Renderer;

public class EmptyGameObject extends GameObject {
	@Override
	public void onUpdate(float dt) {
		super.onUpdate(dt);
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		super.onRender(dt, renderer);
	}
}
