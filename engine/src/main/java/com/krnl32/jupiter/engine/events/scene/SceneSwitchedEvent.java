package com.krnl32.jupiter.engine.events.scene;

import com.krnl32.jupiter.engine.event.Event;
import com.krnl32.jupiter.engine.scene.Scene;

public class SceneSwitchedEvent implements Event {
	private final Scene scene;

	public SceneSwitchedEvent(Scene scene) {
		this.scene = scene;
	}

	public Scene getScene() {
		return scene;
	}
}
