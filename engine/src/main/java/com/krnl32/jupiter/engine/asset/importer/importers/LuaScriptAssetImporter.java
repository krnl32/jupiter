package com.krnl32.jupiter.engine.asset.importer.importers;

import com.krnl32.jupiter.engine.asset.importer.AssetImporter;
import com.krnl32.jupiter.engine.asset.importer.ImportRequest;
import com.krnl32.jupiter.engine.asset.importer.ImportResult;
import com.krnl32.jupiter.engine.asset.importsettings.ImportSettings;
import com.krnl32.jupiter.engine.asset.importsettings.types.ScriptAssetImportSettings;
import com.krnl32.jupiter.engine.asset.types.ScriptAsset;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.script.ScriptSettings;
import com.krnl32.jupiter.engine.script.lua.LuaScriptDefinition;
import com.krnl32.jupiter.engine.utility.FileIO;

import java.nio.file.Path;

public class LuaScriptAssetImporter implements AssetImporter<ScriptAsset> {
	@Override
	public boolean supports(ImportRequest request) {
		String fileExtension = FileIO.getFileExtension(Path.of(request.getSource()));
		return (fileExtension != null && fileExtension.equals("lua"));
	}

	@Override
	public ImportResult<ScriptAsset> importAsset(ImportRequest request) {
		ScriptAssetImportSettings importSettings = (ScriptAssetImportSettings)
			(request.getImportSettings() == null ? getDefaultSettings() : request.getImportSettings());

		Path scriptPath = ProjectContext.getInstance().getAssetDirectory().resolve(request.getSource());
		ScriptAsset scriptAsset = new ScriptAsset(importSettings.getSettings(), new LuaScriptDefinition(scriptPath, importSettings.getSettings()));
		return new ImportResult<>(scriptAsset, importSettings.toMap(), getClass().getSimpleName());
	}

	@Override
	public ImportSettings getDefaultSettings() {
		ScriptSettings settings = new ScriptSettings(
			true,
			false
		);
		return new ScriptAssetImportSettings(settings);
	}
}
