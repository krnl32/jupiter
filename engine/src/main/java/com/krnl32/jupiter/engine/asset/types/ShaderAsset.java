package com.krnl32.jupiter.engine.asset.types;

import com.krnl32.jupiter.engine.asset.Asset;
import com.krnl32.jupiter.engine.asset.AssetState;
import com.krnl32.jupiter.engine.asset.AssetType;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.renderer.Shader;

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
		shader = new Shader(vertexShaderFilePath, fragmentShaderFilePath);
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
		if (shader != null) {
			shader.destroy();
			shader = null;
		}
		setState(AssetState.UNLOADED);
	}
}
