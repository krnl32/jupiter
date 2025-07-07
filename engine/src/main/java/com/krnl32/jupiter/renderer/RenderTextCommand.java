package com.krnl32.jupiter.renderer;

import org.joml.Matrix4f;

public class RenderTextCommand implements RenderCommand {
	private final RenderPacket renderPacket;
	private final Matrix4f transform;
	private final float[] textureUV;
	private final ClipRect clipRect;

	public RenderTextCommand(RenderPacket renderPacket, Matrix4f transform, float[] textureUV, ClipRect clipRect) {
		this.renderPacket = renderPacket;
		this.transform = transform;
		this.textureUV = textureUV;
		this.clipRect = clipRect;
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

	public ClipRect getClipRect() {
		return clipRect;
	}
}
