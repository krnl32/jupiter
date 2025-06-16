package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.ecs.Component;
import org.joml.Vector4f;

public class SpriteRendererComponent implements Component {
	public int index;
	public Vector4f color;
	public AssetID textureAssetID;

	public SpriteRendererComponent(int index, Vector4f color, AssetID textureAssetID) {
		this.index = index;
		this.color = color;
		this.textureAssetID = textureAssetID;
	}
}
