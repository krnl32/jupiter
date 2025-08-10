package com.krnl32.jupiter.engine.asset.types;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.scene.Scene;

public class SceneAsset extends Asset {
	private final Scene scene;

	public SceneAsset(Scene scene) {
		super(AssetType.SCENE);
		this.scene = scene;
	}

	public SceneAsset(AssetId assetId, Scene scene) {
		super(assetId, AssetType.SCENE);
		this.scene = scene;
	}

	public Scene getScene() {
		return scene;
	}
}
