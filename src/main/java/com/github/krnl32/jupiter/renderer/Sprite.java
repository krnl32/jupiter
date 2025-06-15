package com.github.krnl32.jupiter.renderer;

import com.github.krnl32.jupiter.asset.AssetID;
import org.joml.Vector4f;

public class Sprite {
	private int width, height;
	private int index;
	private Vector4f color;
	private AssetID textureAssetID;

	public Sprite(int width, int height, int index, Vector4f color, AssetID textureAssetID) {
		this.width = width;
		this.height = height;
		this.index = index;
		this.color = color;
		this.textureAssetID = textureAssetID;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getIndex() {
		return index;
	}

	public Vector4f getColor() {
		return color;
	}

	public AssetID getTextureAssetID() {
		return textureAssetID;
	}
}
