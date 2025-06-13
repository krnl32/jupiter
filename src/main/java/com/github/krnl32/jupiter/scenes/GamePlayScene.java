package com.github.krnl32.jupiter.scenes;

import com.github.krnl32.jupiter.game.Scene;

public class GamePlayScene extends Scene {
	@Override
	public void onCreate() {

	}

	@Override
	public void onActivate() {

	}

	@Override
	public void onUnload() {

	}

	//	AssetID levelAssetID;
//
//	public GamePlayScene(AssetID levelAssetID) {
//		this.levelAssetID = levelAssetID;
//	}
//
//	@Override
//	public void load() {
//		LevelAsset levelAsset = AssetManager.getInstance().getAsset(levelAssetID);
//		if (levelAsset == null) {
//			Logger.error("GamePlayScene Level({}) Failed to get Level Asset", levelAssetID);
//			return;
//		}
//
//		if (!levelAsset.isLoaded()) {
//			Logger.error("GamePlayScene Level({}) Not Loaded", levelAssetID);
//			return;
//		}
//	}
}
