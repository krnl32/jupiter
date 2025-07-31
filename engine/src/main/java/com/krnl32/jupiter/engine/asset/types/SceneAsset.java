package com.krnl32.jupiter.engine.asset.types;

import com.krnl32.jupiter.engine.asset.Asset;
import com.krnl32.jupiter.engine.asset.AssetState;
import com.krnl32.jupiter.engine.asset.AssetType;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.utility.FileIO;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SceneAsset extends Asset {
	private final String scenePath;
	private JSONObject sceneData;

	public SceneAsset(String scenePath) {
		super(AssetType.SCENE);
		this.scenePath = ProjectContext.getInstance().getAssetDirectory() + "/" + scenePath;
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
		unload();
		return load();
	}

	@Override
	protected void unload() {
		sceneData = null;
		setState(AssetState.UNLOADED);
	}
}
