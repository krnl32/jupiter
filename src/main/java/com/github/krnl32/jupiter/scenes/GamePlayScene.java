package com.github.krnl32.jupiter.scenes;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.LevelAsset;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.game.Scene;
import com.github.krnl32.jupiter.systems.CameraSystem;
import com.github.krnl32.jupiter.systems.KeyboardMovementSystem;
import com.github.krnl32.jupiter.systems.MovementSystem;
import com.github.krnl32.jupiter.systems.RenderSystem;

public class GamePlayScene extends Scene {
	AssetID levelAssetID;

	public GamePlayScene(AssetID levelAssetID) {
		this.levelAssetID = levelAssetID;
	}

	@Override
	public void onCreate() {
		// Register Systems
		addSystem(new MovementSystem(getRegistry()), 0, true);
		addSystem(new KeyboardMovementSystem(getRegistry()), 1, true);
		addSystem(new CameraSystem(getRegistry()), 2, true);
		addSystem(new RenderSystem(getRegistry()));
	}

	@Override
	public void onActivate() {
		LevelAsset levelAsset = AssetManager.getInstance().getAsset(levelAssetID);
		if (levelAsset == null) {
			Logger.error("TestScene Level({}) Null", levelAssetID);
			return;
		}

		if (!levelAsset.isLoaded()) {
			Logger.error("TestScene Level({}) Not Loaded", levelAssetID);
			return;
		}

		levelAsset.getLevel().load(this);
	}

	@Override
	public void onUnload() {
	}
}
