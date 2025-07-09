package com.krnl32.jupiter.scenes;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.SceneAsset;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.scene.Scene;
import com.krnl32.jupiter.serializer.SceneSerializer;
import com.krnl32.jupiter.systems.*;
import com.krnl32.jupiter.systems.ui.*;
import org.json.JSONObject;

public class GamePlayScene extends Scene {
	private final AssetID sceneAssetID;

	public GamePlayScene(AssetID sceneAssetID) {
		this.sceneAssetID = sceneAssetID;
	}

	@Override
	public void onCreate() {
		// Register Systems
		addSystem(new KeyboardControlSystem(getRegistry()), 1, true);
		addSystem(new CameraSystem(getRegistry()), 2, true);
		addSystem(new RenderSystem(getRegistry()));
		addSystem(new LifetimeSystem(getRegistry()));
		addSystem(new ProjectileEmitterSystem(getRegistry()));
		addSystem(new DamageSystem(getRegistry()));
		addSystem(new HealthSystem(getRegistry()));
		addSystem(new DestroySystem(getRegistry()));
		addSystem(new BlinkSystem(getRegistry()));
		addSystem(new ParticleSystem(getRegistry()));
		addSystem(new DeathEffectSystem(getRegistry()));
		addSystem(new UILayoutSystem(getRegistry()));
		addSystem(new UIInputSystem(getRegistry()));
		addSystem(new UIRenderSystem(getRegistry()));
		addSystem(new UIButtonSystem(getRegistry()));
		addSystem(new UITextRenderSystem(getRegistry()));
		addSystem(new UIScrollSystem(getRegistry()));
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
