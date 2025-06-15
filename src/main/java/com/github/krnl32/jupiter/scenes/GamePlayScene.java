package com.github.krnl32.jupiter.scenes;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.SceneAsset;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.game.Scene;
import com.github.krnl32.jupiter.serializer.SceneSerializer;
import com.github.krnl32.jupiter.systems.CameraSystem;
import com.github.krnl32.jupiter.systems.KeyboardMovementSystem;
import com.github.krnl32.jupiter.systems.MovementSystem;
import com.github.krnl32.jupiter.systems.RenderSystem;
import org.json.JSONObject;

public class GamePlayScene extends Scene {
	private final AssetID sceneAssetID;

	public GamePlayScene(AssetID sceneAssetID) {
		this.sceneAssetID = sceneAssetID;
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
		SceneAsset sceneAsset = AssetManager.getInstance().getAsset(sceneAssetID);
		if (sceneAsset == null) {
			Logger.error("GamePlayScene Scene({}) Null", sceneAssetID);
			return;
		}

		if (!sceneAsset.isLoaded()) {
			Logger.error("GamePlayScene Scene({}) Not Loaded", sceneAssetID);
			return;
		}

		JSONObject sceneData = sceneAsset.getSceneData();
		if (sceneData == null) {
			Logger.error("GamePlayScene Scene({}) Has No Data", sceneAssetID);
			return;
		}

		new SceneSerializer().deserialize(sceneData, this);
	}

	@Override
	public void onUnload() {
	}
}
