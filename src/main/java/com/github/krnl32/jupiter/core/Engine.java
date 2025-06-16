package com.github.krnl32.jupiter.core;

import com.github.krnl32.jupiter.event.EventBus;
import com.github.krnl32.jupiter.events.window.WindowCloseEvent;
import com.github.krnl32.jupiter.input.Input;
import com.github.krnl32.jupiter.renderer.Renderer;
import com.github.krnl32.jupiter.utility.Timer;

public abstract class Engine {
	private boolean running;
	private Window window;
	private Renderer renderer;

	public Engine(String name, int width, int height) {
		running = false;
		window = new Window(name, width, height);

		EventBus.getInstance().register(WindowCloseEvent.class, event -> {
			running = false;
		});
	}

	public void run() {
		if(!onInit())
			Logger.critical("Failed to run onInit");

		renderer = new Renderer();
		renderer.setDepthTest(true);
		renderer.setAlphaBlending(true);

		running = true;

		float lastTime = 0.0f;
		while (running) {
			float time = Timer.getTimeSeconds();
			float dt = time - lastTime;
			lastTime = time;

			if(dt > 0) {
				//System.out.printf("FPS: %f\n", (1/dt));
				onUpdate(dt);

				renderer.beginFrame();
				onRender(dt, renderer);
				renderer.endFrame();
			}

			Input.getInstance().reset();
			window.update();
		}

		window.destroy();
	}

	protected Window getWindow() {
		return window;
	}

	public abstract boolean onInit();
	public abstract void onUpdate(float dt);
	public abstract void onRender(float dt, Renderer renderer);
}
