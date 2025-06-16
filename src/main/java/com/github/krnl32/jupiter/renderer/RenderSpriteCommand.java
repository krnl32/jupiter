package com.github.krnl32.jupiter.renderer;

import org.joml.Vector3f;

public class RenderSpriteCommand implements RenderCommand {
	private final Vector3f position;
	private final SpriteRenderData spriteRenderData;

	public RenderSpriteCommand(Vector3f position, SpriteRenderData spriteRenderData) {
		this.position = position;
		this.spriteRenderData = spriteRenderData;
	}

	@Override
	public void execute(Renderer renderer) {
		renderer.drawSprite(position, spriteRenderData);
	}
}
