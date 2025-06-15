package com.github.krnl32.jupiter.asset;

import com.github.krnl32.jupiter.renderer.Shader;

public class ShaderAsset extends Asset{
	private final String vertexFilePath;
	private final String fragmentFilePath;
	private Shader shader;

	public ShaderAsset(String vertexFileName, String fragmentFileName) {
		super(AssetType.SHADER);
		this.vertexFilePath = getRootPath() + "\\shaders\\" + vertexFileName;
		this.fragmentFilePath = getRootPath() + "\\shaders\\" + fragmentFileName;
	}

	public Shader getShader() {
		return shader;
	}

	@Override
	protected boolean load() {
		shader = new Shader(vertexFilePath, fragmentFilePath);
		setState(AssetState.LOADED);
		return true;
	}

	@Override
	protected boolean reload() {
		if (shader != null)
			shader.destroy();
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
