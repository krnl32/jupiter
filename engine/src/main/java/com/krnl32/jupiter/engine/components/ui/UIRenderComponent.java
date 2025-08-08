package com.krnl32.jupiter.engine.components.ui;

import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.ecs.Component;
import org.joml.Vector4f;

public class UIRenderComponent implements Component {
	public int index;
	public Vector4f color;
	public AssetId textureAssetId;
	public float[] textureUV;

	public UIRenderComponent(int index, Vector4f color, AssetId textureAssetId) {
		this.index = index;
		this.color = color;
		this.textureAssetId = textureAssetId;
		this.textureUV = new float[] {
			0.0f, 0.0f, // BL
			1.0f, 0.0f, // BR
			1.0f, 1.0f, // TR
			0.0f, 1.0f  // TL
		};
	}

	public UIRenderComponent(int index, Vector4f color, AssetId textureAssetId, float[] textureUV) {
		this.index = index;
		this.color = color;
		this.textureAssetId = textureAssetId;
		this.textureUV = textureUV;
	}
}
