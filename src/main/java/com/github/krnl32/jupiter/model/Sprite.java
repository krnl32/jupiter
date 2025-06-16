package com.github.krnl32.jupiter.model;

import com.github.krnl32.jupiter.asset.AssetID;
import org.joml.Vector4f;

public class Sprite {
	private final int index;
	private final Vector4f color;
	private final AssetID textureAssetID;

	public Sprite(int index, Vector4f color, AssetID textureAssetID) {
		this.index = index;
		this.color = color;
		this.textureAssetID = textureAssetID;
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
