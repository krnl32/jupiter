package com.github.krnl32.jupiter.core;

import com.github.krnl32.jupiter.utility.DateTime;

public abstract class Engine {
	private boolean running;
	private Window window;

	public Engine(String name, int width, int height) {
		running = false;
		window = new Window(name, width, height);
	}

	public void run() {
		if(!onInit())
			Logger.critical("Failed to run onInit");

		running = true;

		float lastTime = 0.0f;
		while (running) {
			float time = DateTime.getTimeSeconds();
			float dt = time - lastTime;
			lastTime = time;

			if(dt > 0) {
				//System.out.printf("FPS: %f\n", (1/dt));
				onInput(dt);
				onUpdate(dt);
				onRender(dt);
			}

			window.update();
		}

		window.destroy();
	}

	public abstract boolean onInit();
	public abstract void onInput(float dt);
	public abstract void onUpdate(float dt);
	public abstract void onRender(float dt);
}
