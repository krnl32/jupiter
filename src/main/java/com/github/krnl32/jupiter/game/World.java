package com.github.krnl32.jupiter.game;

import com.github.krnl32.jupiter.core.Logger;

import java.util.HashMap;
import java.util.Map;

public class World {
	private Map<String, Scene> scenes = new HashMap<>();
	private Scene currentScene;

	public void onUpdate(float dt) {
		if (currentScene != null)
			currentScene.onUpdate(dt);
	}

	public void onRender(float dt) {
		if (currentScene != null)
			currentScene.onRender(dt);
	}

	public void addScene(String name, Scene scene) {
		scenes.put(name, scene);
	}

	public void switchScene(String name) {
		currentScene = scenes.get(name);
		if(currentScene == null) {
			Logger.error("Scene {} doesn't exist", name);
			return;
		}
		currentScene.load();
	}
}
