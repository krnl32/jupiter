package com.github.krnl32.jupiter.asset;

import com.github.krnl32.jupiter.renderer.Texture2D;

public class TextureAsset extends Asset {
	private final String texturePath;
	private Texture2D texture;

	public TextureAsset(String texturePath) {
		super(AssetType.Texture);
		this.texturePath = getRootPath() + texturePath;
	}

	@Override
	public boolean load() {
		texture = new Texture2D(texturePath);
		setState(AssetState.Loaded);
		return true;
	}

	@Override
	public boolean reload() {
		return load();
	}

	@Override
	public void unload() {
		texture.destroy();
		setState(AssetState.Unloaded);
	}
}
