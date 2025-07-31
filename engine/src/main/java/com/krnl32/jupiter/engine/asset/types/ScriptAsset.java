package com.krnl32.jupiter.engine.asset.types;

import com.krnl32.jupiter.engine.asset.Asset;
import com.krnl32.jupiter.engine.asset.AssetState;
import com.krnl32.jupiter.engine.asset.AssetType;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.script.ScriptDefinition;

import java.io.File;

public class ScriptAsset extends Asset {
	private final String scriptPath;
	private ScriptDefinition scriptDefinition;

	public ScriptAsset(String scriptPath) {
		super(AssetType.SCRIPT);
		this.scriptPath = scriptPath;
	}

	public String getRelativePath() {
		return scriptPath;
	}

	public String getAbsolutePath() {
		return ProjectContext.getAssetDirectory() + "/" + scriptPath;
	}

	public ScriptDefinition getScriptDefinition() {
		return scriptDefinition;
	}

	@Override
	protected boolean load() {
		File scriptFile = new File(getAbsolutePath());
		if (!scriptFile.exists()) {
			setState(AssetState.MISSING);
			Logger.error("ScriptAsset Failed to Load, File({}) Doesn't Exist", scriptPath);
			return false;
		}

		scriptDefinition = new ScriptDefinition(getAbsolutePath());
		setState(AssetState.LOADED);
		return true;
	}

	@Override
	protected boolean reload() {
		unload();
		return load();
	}

	@Override
	protected void unload() {
		scriptDefinition = null;
		setState(AssetState.UNLOADED);
	}
}
