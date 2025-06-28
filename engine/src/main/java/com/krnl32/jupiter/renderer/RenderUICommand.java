package com.krnl32.jupiter.renderer;

import org.joml.Matrix4f;

public class RenderUICommand implements RenderCommand {
	private final RenderPacket renderPacket;
	private final Matrix4f transform;
	private final float[] textureUV;

	public RenderUICommand(RenderPacket renderPacket, Matrix4f transform, float[] textureUV) {
		this.renderPacket = renderPacket;
		this.transform = transform;
		this.textureUV = textureUV;
	}

	public RenderPacket getRenderPacket() {
		return renderPacket;
	}

	public Matrix4f getTransform() {
		return transform;
	}

	public float[] getTextureUV() {
		return textureUV;
	}
}
