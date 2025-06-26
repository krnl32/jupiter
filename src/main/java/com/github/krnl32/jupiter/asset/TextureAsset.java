package com.github.krnl32.jupiter.asset;

import com.github.krnl32.jupiter.renderer.Texture2D;

public class TextureAsset extends Asset {
	private final String texturePath;
	private Texture2D texture;

	public TextureAsset(String texturePath) {
		super(AssetType.TEXTURE);
		this.texturePath = texturePath;
	}

	public String getTexturePath() {
		return texturePath;
	}

	public Texture2D getTexture() {
		return texture;
	}

	@Override
	protected boolean load() {
		texture = new Texture2D(getRootPath() + texturePath);
		setState(AssetState.LOADED);
		return true;
	}

	@Override
	protected boolean reload() {
		if (texture != null)
			texture.destroy();
		return load();
	}

	@Override
	protected void unload() {
		if (texture != null) {
			texture.destroy();
			texture = null;
		}
		setState(AssetState.UNLOADED);
	}
}
