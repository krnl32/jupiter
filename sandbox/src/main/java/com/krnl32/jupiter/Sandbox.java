package com.krnl32.jupiter;

import com.krnl32.jupiter.core.Engine;
import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.scene.ViewportResizeEvent;
import com.krnl32.jupiter.events.window.WindowResizeEvent;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.scene.SceneManager;
import com.krnl32.jupiter.scenes.TestScene;
import org.joml.Vector4f;

public class Sandbox extends Engine {
	private SceneManager sceneManager;

	public Sandbox(String name, int width, int height) {
		super(name, width, height);

		EventBus.getInstance().register(WindowResizeEvent.class, event -> {
			EventBus.getInstance().emit(new ViewportResizeEvent(event.getWidth(), event.getHeight()));
		});
	}

	@Override
	public boolean onInit() {
		// Scene
		sceneManager = new SceneManager();
		sceneManager.addScene("test", new TestScene(getWindow().getWidth(), getWindow().getHeight()));
		sceneManager.switchScene("test");
		return true;
	}

	@Override
	public void onUpdate(float dt) {
		sceneManager.onUpdate(dt);
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		renderer.setClearColor(new Vector4f(0.07f, 0.13f, 0.17f, 1.0f)); // After Framebuffer as well, Later? NOTE!
		renderer.clear();
		sceneManager.onRender(dt, renderer);
	}
}
