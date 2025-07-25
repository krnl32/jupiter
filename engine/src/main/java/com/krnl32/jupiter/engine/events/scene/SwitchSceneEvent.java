package com.krnl32.jupiter.engine.events.scene;

import com.krnl32.jupiter.engine.event.Event;

public class SwitchSceneEvent implements Event {
	private final String sceneName;

	public SwitchSceneEvent(String sceneName) {
		this.sceneName = sceneName;
	}

	public String getSceneName() {
		return sceneName;
	}
}
