package com.krnl32.jupiter.editor.asset.loaders;

import com.krnl32.jupiter.engine.asset.handle.AssetDescriptor;
import com.krnl32.jupiter.engine.asset.loader.AssetLoader;
import com.krnl32.jupiter.engine.asset.types.SceneAsset;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.sceneserializer.SceneSerializer;
import com.krnl32.jupiter.engine.utility.FileIO;
import org.json.JSONObject;

import java.nio.file.Path;
import java.util.Map;

public class EditorSceneAssetLoader implements AssetLoader<SceneAsset> {
	private final SceneSerializer<Map<String, Object>> sceneSerializer;

	public EditorSceneAssetLoader(SceneSerializer<Map<String, Object>> sceneSerializer) {
		this.sceneSerializer = sceneSerializer;
	}

	@Override
	public SceneAsset load(AssetDescriptor assetDescriptor) {
		try {
			Path assetPath = ProjectContext.getInstance().getAssetDirectory().resolve(assetDescriptor.getAssetPath());
			String sceneData = FileIO.readFileContent(assetPath);

			Scene scene = sceneSerializer.deserialize(new JSONObject(sceneData).toMap());
			if (scene == null) {
				Logger.error("EditorSceneAssetLoader Failed to Deserialize SceneAsset({})", assetDescriptor.getAssetId());
				return null;
			}

			return new SceneAsset(assetDescriptor.getAssetId(), scene.getSceneSettings(), scene);
		} catch (Exception e) {
			Logger.error("EditorSceneAssetLoader Failed to Load Asset({}): {}", assetDescriptor.getAssetId(), e.getMessage());
			return null;
		}
	}

	@Override
	public void unload(SceneAsset asset) {
	}
}
