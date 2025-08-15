package com.krnl32.jupiter.engine.asset.types;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.scene.SceneSettings;

public class SceneAsset extends Asset {
	private final SceneSettings settings;
	private final Scene scene;

	public SceneAsset(SceneSettings settings, Scene scene) {
		super(AssetType.SCENE);
		this.settings = settings;
		this.scene = scene;
	}

	public SceneAsset(AssetId assetId, SceneSettings settings, Scene scene) {
		super(assetId, AssetType.SCENE);
		this.settings = settings;
		this.scene = scene;
	}

	public SceneSettings getSettings() {
		return settings;
	}

	public Scene getScene() {
		return scene;
	}
}
