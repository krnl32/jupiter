package com.github.krnl32.jupiter.renderer;

import org.joml.Vector4f;

public class SpriteRenderData {
	private final float width, height;
	private final int index;
	private final Vector4f color;
	private final Texture2D texture;

	public SpriteRenderData(float width, float height, int index, Vector4f color, Texture2D texture) {
		this.width = width;
		this.height = height;
		this.index = index;
		this.color = color;
		this.texture = texture;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
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
