package com.krnl32.jupiter;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.SceneAsset;
import com.krnl32.jupiter.core.Engine;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.scene.ViewportResizeEvent;
import com.krnl32.jupiter.events.window.WindowResizeEvent;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.scene.SceneManager;
import com.krnl32.jupiter.scenes.TestScene;
import com.krnl32.jupiter.scenes.levels.Level1Scene;
import com.krnl32.jupiter.scenes.menu.MainMenuScene;
import org.joml.Vector4f;

public class Spaceshooter extends Engine {
	private SceneManager sceneManager;

	public Spaceshooter(String name, int width, int height) {
		super(name, width, height);

		EventBus.getInstance().register(WindowResizeEvent.class, event -> {
			EventBus.getInstance().emit(new ViewportResizeEvent(event.getWidth(), event.getHeight()));
		});
	}

	@Override
	public boolean onInit() {
		// Scene
		AssetID level1AssetID = AssetManager.getInstance().register("scenes/level1.json", () -> new SceneAsset("scenes/level1.json"));
		if (level1AssetID == null)
			Logger.critical("Game Failed to Register Scene Asset({})", "scenes/level1.json");

		sceneManager = new SceneManager();
		sceneManager.addScene("mainMenu", new MainMenuScene(getWindow().getWidth(), getWindow().getHeight()));
		sceneManager.addScene("level1", new Level1Scene(getWindow().getWidth(), getWindow().getHeight()));
		sceneManager.addScene("test", new TestScene(getWindow().getWidth(), getWindow().getHeight()));
		sceneManager.switchScene("mainMenu");
		return true;
	}

	@Override
	public void onUpdate(float dt) {
		sceneManager.onUpdate(dt);
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		renderer.setClearColor(new Vector4f(0.07f, 0.13f, 0.17f, 1.0f));
		renderer.clear();
		sceneManager.onRender(dt, renderer);
	}
}
