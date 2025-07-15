package com.krnl32.jupiter.scene;

import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.scene.SceneSwitchedEvent;
import com.krnl32.jupiter.events.scene.SwitchSceneEvent;
import com.krnl32.jupiter.renderer.Renderer;

import java.util.HashMap;
import java.util.Map;

public class SceneManager {
	private final Map<String, Scene> scenes;
	private Scene currentScene;

	public SceneManager() {
		this.scenes = new HashMap<>();
		this.currentScene = null;

		EventBus.getInstance().register(SwitchSceneEvent.class, event -> {
			switchScene(event.getSceneName());
		});
	}

	public void onUpdate(float dt) {
		if (currentScene != null)
			currentScene.onUpdate(dt);
	}

	public void onRender(float dt, Renderer renderer) {
		if (currentScene != null)
			currentScene.onRender(dt, renderer);
	}

	public void addScene(String name, Scene scene) {
		scenes.put(name, scene);
	}

	public void switchScene(String name) {
		if (currentScene != null)
			currentScene.onUnload();

		currentScene = scenes.get(name);
		if(currentScene == null) {
			Logger.error("Scene {} doesn't exist", name);
			return;
		}
		currentScene.load();

		EventBus.getInstance().emit(new SceneSwitchedEvent(currentScene));
	}

	public Scene getScene() {
		return currentScene;
	}

	public void setScene(Scene scene) {
		if (currentScene != null)
			currentScene.onUnload();
		currentScene = scene;
		currentScene.load();

		EventBus.getInstance().emit(new SceneSwitchedEvent(currentScene));
	}
}
