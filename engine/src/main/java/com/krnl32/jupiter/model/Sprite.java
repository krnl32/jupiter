package com.krnl32.jupiter.model;

import com.krnl32.jupiter.asset.AssetID;
import org.joml.Vector4f;

public class Sprite {
	private final int index;
	private final Vector4f color;
	private final AssetID textureAssetID;
	private final float[] textureUV;

	public Sprite(int index, Vector4f color, AssetID textureAssetID) {
		this.index = index;
		this.color = color;
		this.textureAssetID = textureAssetID;
		this.textureUV = new float[] {
			0.0f, 0.0f, // BL
			1.0f, 0.0f, // BR
			1.0f, 1.0f, // TR
			0.0f, 1.0f  // TL
		};
	}

	public Sprite(int index, Vector4f color, AssetID textureAssetID, float[] textureUV) {
		this.index = index;
		this.color = color;
		this.textureAssetID = textureAssetID;
		this.textureUV = textureUV;
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

	public float[] getTextureUV() {
		return textureUV;
	}
}
