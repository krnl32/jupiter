package com.github.krnl32.jupiter.asset;

import com.github.krnl32.jupiter.renderer.Texture2D;

public class TextureAsset extends Asset {
	private final String textureAssetPath;
	private Texture2D texture;

	public TextureAsset(String textureAssetPath) {
		super(AssetType.TEXTURE);
		this.textureAssetPath = textureAssetPath;
	}

	public String getTextureAssetPath() {
		return textureAssetPath;
	}

	public Texture2D getTexture() {
		return texture;
	}

	@Override
	protected boolean load() {
		texture = new Texture2D(getRootPath() + "\\textures\\" + textureAssetPath);
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
