package com.github.krnl32.jupiter.game;

import com.github.krnl32.jupiter.renderer.Renderer;

public abstract class Component {
	private GameObject gameObject;

	public abstract void onUpdate(float dt);
	public abstract void onRender(float dt, Renderer renderer);

	public GameObject getGameObject() {
		return gameObject;
	}

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
}
