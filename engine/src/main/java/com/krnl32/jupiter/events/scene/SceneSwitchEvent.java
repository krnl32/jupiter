package com.krnl32.jupiter.events.scene;

import com.krnl32.jupiter.event.Event;

public class SceneSwitchEvent implements Event {
	private final String sceneName;

	public SceneSwitchEvent(String sceneName) {
		this.sceneName = sceneName;
	}

	public String getSceneName() {
		return sceneName;
	}
}
