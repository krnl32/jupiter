package com.krnl32.jupiter.editor.asset.importers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krnl32.jupiter.engine.asset.importer.AssetImporter;
import com.krnl32.jupiter.engine.asset.importer.ImportRequest;
import com.krnl32.jupiter.engine.asset.importer.ImportResult;
import com.krnl32.jupiter.engine.asset.importsettings.ImportSettings;
import com.krnl32.jupiter.engine.asset.importsettings.types.SceneAssetImportSettings;
import com.krnl32.jupiter.engine.asset.types.SceneAsset;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.scene.SceneSettings;
import com.krnl32.jupiter.engine.serializer.SceneSerializer;
import com.krnl32.jupiter.engine.utility.FileIO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class JSONSceneAssetImporter implements AssetImporter<SceneAsset> {
	@Override
	public boolean supports(ImportRequest request) {
		String fileExtension = FileIO.getFileExtension(Path.of(request.getSource()));
		if (!fileExtension.equals("jscene")) {
			return false;
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.readTree(request.getData());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public ImportResult<SceneAsset> importAsset(ImportRequest request) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> sceneData = mapper.readValue(request.getData(), Map.class);

			Scene scene = SceneSerializer.deserialize(sceneData);
			if (scene == null) {
				Logger.error("JSONSceneAssetImporter Failed to Deserialize Scene({})", request.getSource());
				return null;
			}

			SceneAssetImportSettings importSettings = new SceneAssetImportSettings(scene.getSceneSettings());
			SceneAsset sceneAsset = new SceneAsset(importSettings.getSettings(), scene);
			return new ImportResult<>(sceneAsset, importSettings.toMap(), getClass().getSimpleName());
		} catch (IOException e) {
			Logger.error("JSONSceneAssetImporter Failed to Parse({})", request.getSource());
			return null;
		}
	}

	@Override
	public ImportSettings getDefaultSettings() {
		SceneSettings settings = new SceneSettings(
			9.8f
		);
		return new SceneAssetImportSettings(settings);
	}
}
