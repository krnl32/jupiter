package com.krnl32.jupiter.components.renderer;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.ecs.Component;
import org.joml.Vector4f;

public class SpriteRendererComponent implements Component {
	public int index;
	public Vector4f color;
	public AssetID textureAssetID;
	public float[] textureUV;

	public SpriteRendererComponent(int index, Vector4f color, AssetID textureAssetID) {
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

	public SpriteRendererComponent(int index, Vector4f color, AssetID textureAssetID, float[] textureUV) {
		this.index = index;
		this.color = color;
		this.textureAssetID = textureAssetID;
		this.textureUV = textureUV;
	}
}
