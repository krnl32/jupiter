package com.krnl32.jupiter.engine.renderer;

import com.krnl32.jupiter.engine.renderer.texture.Texture2D;
import org.joml.Vector4f;

public class RenderPacket {
	private final int index;
	private final Vector4f color;
	private final Texture2D texture;

	public RenderPacket(int index, Vector4f color, Texture2D texture) {
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
