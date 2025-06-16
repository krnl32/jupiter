package com.github.krnl32.jupiter.renderer;

import org.joml.Vector3f;

public class RenderSpriteCommand implements RenderCommand {
	private final Vector3f position;
	private final Vector3f rotation;
	private final SpriteRenderData spriteRenderData;

	public RenderSpriteCommand(Vector3f position, Vector3f rotation, SpriteRenderData spriteRenderData) {
		this.position = position;
		this.rotation = rotation;
		this.spriteRenderData = spriteRenderData;
	}

	@Override
	public void execute(Renderer renderer) {
		renderer.drawSprite(position, rotation, spriteRenderData);
	}
}
