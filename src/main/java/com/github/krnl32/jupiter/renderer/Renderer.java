package com.github.krnl32.jupiter.renderer;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
	private final List<RenderCommand> commandQueue = new ArrayList<>();
	private final SpriteBatch spriteBatch = new SpriteBatch();
	private Camera camera;

	public void beginFrame() {
		// clear screen
		// bind shader -> set Shader Uniform Camera
		commandQueue.clear();
		spriteBatch.begin();
	}

	public void endFrame() {
		for (var cmd: commandQueue)
			cmd.execute(this);
		spriteBatch.render();
		// unbind shader
	}

	public void submit(RenderCommand cmd) {
		commandQueue.add(cmd);
	}

	public void drawSprite(Vector3f position, Sprite sprite) {
		spriteBatch.addSprite(position, sprite);
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
}
