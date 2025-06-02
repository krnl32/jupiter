package com.github.krnl32.jupiter.game;

public abstract class Component {
	private GameObject gameObject;

	public abstract void onUpdate(float dt);
	public abstract void onRender(float dt);

	public GameObject getGameObject() {
		return gameObject;
	}

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
}
