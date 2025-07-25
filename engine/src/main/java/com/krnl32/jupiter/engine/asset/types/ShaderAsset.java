package com.krnl32.jupiter.engine.asset.types;

import com.krnl32.jupiter.engine.asset.Asset;
import com.krnl32.jupiter.engine.asset.AssetState;
import com.krnl32.jupiter.engine.asset.AssetType;
import com.krnl32.jupiter.engine.renderer.Shader;

public class ShaderAsset extends Asset {
	private final String vertexFilePath;
	private final String fragmentFilePath;
	private Shader shader;

	public ShaderAsset(String vertexFileName, String fragmentFileName) {
		super(AssetType.SHADER);
		this.vertexFilePath = getRootPath() + vertexFileName;
		this.fragmentFilePath = getRootPath() + fragmentFileName;
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
