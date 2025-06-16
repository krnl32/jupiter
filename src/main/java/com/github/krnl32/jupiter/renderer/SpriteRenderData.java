package com.github.krnl32.jupiter.renderer;

import org.joml.Vector4f;

public class SpriteRenderData {
	private final int index;
	private final Vector4f color;
	private final Texture2D texture;

	public SpriteRenderData(int index, Vector4f color, Texture2D texture) {
		this.index = index;
		this.color = color;
		this.texture = texture;
	}

	public int getIndex() {
		return index;
	}

	public Vector4f getColor() {
		return color;
	}

	public Texture2D getTexture() {
		return texture;
	}
}
