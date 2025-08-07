package com.krnl32.jupiter.engine.oldAsset.types;

import com.krnl32.jupiter.engine.oldAsset.Asset;
import com.krnl32.jupiter.engine.oldAsset.AssetState;
import com.krnl32.jupiter.engine.oldAsset.AssetType;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.renderer.Shader;
import com.krnl32.jupiter.engine.utility.FileIO;

public class ShaderAsset extends Asset {
	private final String vertexShaderFilePath;
	private final String fragmentShaderFilePath;
	private Shader shader;

	public ShaderAsset(String vertexShaderFilePath, String fragmentShaderFilePath) {
		super(AssetType.SHADER);
		this.vertexShaderFilePath = ProjectContext.getInstance().getAssetDirectory() + "/" + vertexShaderFilePath;
		this.fragmentShaderFilePath = ProjectContext.getInstance().getAssetDirectory() + "/" + fragmentShaderFilePath;
	}

	public Shader getShader() {
		return shader;
	}

	@Override
	protected boolean load() {
		try {
			shader = new Shader(FileIO.readFileContent(vertexShaderFilePath), FileIO.readFileContent(fragmentShaderFilePath));
			setState(AssetState.LOADED);
			return true;
		} catch (Exception e) {
			Logger.error("Failed to Load ShaderAsset({}, {}): {}", vertexShaderFilePath, fragmentShaderFilePath, e.getMessage());
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
		if (shader != null) {
			shader.destroy();
			shader = null;
		}
		setState(AssetState.UNLOADED);
	}
}
