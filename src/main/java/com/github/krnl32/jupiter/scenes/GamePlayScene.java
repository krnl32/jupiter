package com.github.krnl32.jupiter.scenes;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.LevelAsset;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.game.Scene;

public class GamePlayScene extends Scene {
	AssetID levelAssetID;

	public GamePlayScene(AssetID levelAssetID) {
		this.levelAssetID = levelAssetID;
	}

	@Override
	public void load() {
		LevelAsset levelAsset = AssetManager.getInstance().getAsset(levelAssetID);
		if (levelAsset == null) {
			Logger.error("GamePlayScene Level({}) Null", levelAssetID);
			return;
		}

		if (!levelAsset.isLoaded()) {
			Logger.error("GamePlayScene Level({}) Not Loaded", levelAssetID);
			return;
		}

		for(var go: levelAsset.getLevel().getGameObjects())
			addGameObject(go);
	}
}
