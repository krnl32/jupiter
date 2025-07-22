package com.krnl32.jupiter.scenes;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.types.SceneAsset;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.scene.Scene;
import com.krnl32.jupiter.serializer.SceneSerializer;
import org.json.JSONObject;

public class GamePlayScene extends Scene {
	private final AssetID sceneAssetID;

	public GamePlayScene(AssetID sceneAssetID) {
		this.sceneAssetID = sceneAssetID;
	}

	@Override
	public void onCreate() {
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
