package com.github.krnl32.jupiter.game;

import com.github.krnl32.jupiter.gameobjects.EmptyGameObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
	private final List<GameObject> gameObjects = new ArrayList<>();

	public void onUpdate(float dt) {
		for (var gameObject: gameObjects)
			gameObject.onUpdate(dt);
	}

	public void onRender(float dt) {
		for (var gameObject: gameObjects)
			gameObject.onRender(dt);
	}

	public abstract void load();

	public void addGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
	}

	public void spawnGameObject() {
		gameObjects.add(new EmptyGameObject());
	}
}
