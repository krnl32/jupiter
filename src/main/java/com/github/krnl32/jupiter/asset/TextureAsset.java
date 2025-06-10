package com.github.krnl32.jupiter.asset;

import com.github.krnl32.jupiter.renderer.Texture2D;

public class TextureAsset extends Asset {
	private final String texturePath;
	private Texture2D texture;

	public TextureAsset(String textureFileName) {
		super(AssetType.Texture);
		this.texturePath = getRootPath() + "\\textures\\" + textureFileName;
	}

	public Texture2D getTexture() {
		return texture;
	}

	@Override
	protected boolean load() {
		texture = new Texture2D(texturePath);
		setState(AssetState.Loaded);
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
		setState(AssetState.Unloaded);
	}
}
