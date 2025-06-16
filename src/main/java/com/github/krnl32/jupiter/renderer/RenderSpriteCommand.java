package com.github.krnl32.jupiter.renderer;

import org.joml.Matrix4f;

public class RenderSpriteCommand implements RenderCommand {
	private final Matrix4f transform;
	private final SpriteRenderData spriteRenderData;
	private final float[] textureUV;

	public RenderSpriteCommand(Matrix4f transform, SpriteRenderData spriteRenderData, float[] textureUV) {
		this.transform = transform;
		this.spriteRenderData = spriteRenderData;
		this.textureUV = textureUV;
	}

	@Override
	public void execute(Renderer renderer) {
		renderer.drawSprite(transform, spriteRenderData, textureUV);
	}
}
