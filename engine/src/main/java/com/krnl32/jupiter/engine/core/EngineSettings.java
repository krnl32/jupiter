package com.krnl32.jupiter.engine.core;

public final class EngineSettings {
	private final WindowSettings windowSettings;

	public EngineSettings(WindowSettings windowSettings) {
		this.windowSettings = windowSettings;
	}

	public WindowSettings getWindowSettings() {
		return windowSettings;
	}

	@Override
	public String toString() {
		return "EngineSettings{" +
			"windowSettings=" + windowSettings +
			'}';
	}
}
