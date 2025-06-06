package com.github.krnl32.jupiter.game;

import com.github.krnl32.jupiter.components.CameraComponent;
import com.github.krnl32.jupiter.gameobjects.EmptyGameObject;
import com.github.krnl32.jupiter.renderer.Camera;
import com.github.krnl32.jupiter.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
	private final List<GameObject> gameObjects = new ArrayList<>();

	public void onUpdate(float dt) {
		for (var gameObject: gameObjects)
			gameObject.onUpdate(dt);
	}

	public void onRender(float dt, Renderer renderer) {
		for (var gameObject: gameObjects)
			gameObject.onRender(dt, renderer);
	}

	public abstract void load();

	public void addGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
	}

	public void spawnGameObject() {
		gameObjects.add(new EmptyGameObject());
	}

	public Camera getPrimaryCamera() {
		for (var gameObject: gameObjects)
		{
			CameraComponent cameraComponent = gameObject.getComponent(CameraComponent.class);
			if (cameraComponent != null && cameraComponent.isPrimary())
				return cameraComponent.getCamera();
		}
		return null;
	}
}
