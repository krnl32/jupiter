package com.krnl32.jupiter.renderer;

import org.joml.Matrix4f;

public class RenderSpriteCommand implements RenderCommand {
	private final Matrix4f transform;
	private final RenderPacket renderPacket;
	private final float[] textureUV;

	public RenderSpriteCommand(Matrix4f transform, RenderPacket renderPacket, float[] textureUV) {
		this.transform = transform;
		this.renderPacket = renderPacket;
		this.textureUV = textureUV;
	}

	public Matrix4f getTransform() {
		return transform;
	}

	public RenderPacket getRenderPacket() {
		return renderPacket;
	}

	public float[] getTextureUV() {
		return textureUV;
	}
}
