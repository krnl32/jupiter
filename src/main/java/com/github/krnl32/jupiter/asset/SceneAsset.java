package com.github.krnl32.jupiter.asset;

import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.utility.FileIO;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SceneAsset extends Asset {
	private final String scenePath;
	private JSONObject sceneData;

	public SceneAsset(String scenePath) {
		super(AssetType.SCENE);
		this.scenePath = getRootPath() + scenePath;
	}

	public JSONObject getSceneData() {
		return sceneData;
	}

	@Override
	protected boolean load() {
		try {
			sceneData = new JSONObject(FileIO.readFileContent(scenePath));
			setState(AssetState.LOADED);
			return true;
		} catch (IOException | JSONException e) {
			Logger.error("SceneAsset Failed to Read File({})", scenePath);
			setState(AssetState.MISSING);
			return false;
		}
	}

	@Override
	protected boolean reload() {
		return load();
	}

	@Override
	protected void unload() {
		sceneData = null;
		setState(AssetState.UNLOADED);
	}
}
