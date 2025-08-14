package com.krnl32.jupiter.editor.asset.loaders;

import com.krnl32.jupiter.engine.asset.handle.AssetDescriptor;
import com.krnl32.jupiter.engine.asset.loader.AssetLoader;
import com.krnl32.jupiter.engine.asset.types.ScriptAsset;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.script.ScriptDefinition;
import com.krnl32.jupiter.engine.script.ScriptSettings;

import java.nio.file.Path;

public class EditorLuaScriptAssetLoader implements AssetLoader<ScriptAsset> {
	@Override
	public ScriptAsset load(AssetDescriptor assetDescriptor) {
		Path scriptPath = ProjectContext.getInstance().getAssetDirectory().resolve(assetDescriptor.getAssetPath());

		// Script Settings
		boolean hotReload = (boolean) assetDescriptor.getSettings().get("hotReload");
		boolean compile = (boolean) assetDescriptor.getSettings().get("compile");
		ScriptSettings settings = new ScriptSettings(hotReload, compile);

		return new ScriptAsset(assetDescriptor.getAssetId(), settings, new ScriptDefinition(scriptPath, settings));
	}

	@Override
	public void unload(ScriptAsset asset) {
	}
}
