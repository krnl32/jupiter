package com.github.krnl32.jupiter.renderer;

import org.joml.Matrix4f;

public class RenderSpriteCommand implements RenderCommand {
	private final Matrix4f transform;
	private final SpriteRenderData spriteRenderData;

	public RenderSpriteCommand(Matrix4f transform, SpriteRenderData spriteRenderData) {
		this.transform = transform;
		this.spriteRenderData = spriteRenderData;
	}

	@Override
	public void execute(Renderer renderer) {
		renderer.drawSprite(transform, spriteRenderData);
	}
}
