package com.krnl32.jupiter.engine.sceneserializer.jnative.model;

import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.scene.SceneSettings;

import java.util.Objects;

public class JScene {
	private final JSceneHeader header;
	private final SceneSettings settings;
	private final Scene scene;

	public JScene(JSceneHeader header, SceneSettings settings, Scene scene) {
		this.header = header;
		this.settings = settings;
		this.scene = scene;
	}

	public JSceneHeader getHeader() {
		return header;
	}

	public SceneSettings getSettings() {
		return settings;
	}

	public Scene getScene() {
		return scene;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		JScene jScene = (JScene) o;
		return Objects.equals(header, jScene.header) && Objects.equals(settings, jScene.settings) && Objects.equals(scene, jScene.scene);
	}

	@Override
	public int hashCode() {
		return Objects.hash(header, settings, scene);
	}

	@Override
	public String toString() {
		return "JScene{" +
			"header=" + header +
			", settings=" + settings +
			", scene=" + scene +
			'}';
	}
}
