package com.github.krnl32.jupiter.renderer;

import org.joml.Vector3f;

public class RenderSpriteCommand implements RenderCommand {
	private Vector3f position;
	private Sprite sprite;

	public RenderSpriteCommand(Vector3f position, Sprite sprite) {
		this.position = position;
		this.sprite = sprite;
	}

	@Override
	public void execute(Renderer renderer) {
		renderer.drawSprite(position, sprite);
	}
}
