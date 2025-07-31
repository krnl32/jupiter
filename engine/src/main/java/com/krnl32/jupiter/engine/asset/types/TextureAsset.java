package com.krnl32.jupiter.engine.asset.types;

import com.krnl32.jupiter.engine.asset.Asset;
import com.krnl32.jupiter.engine.asset.AssetState;
import com.krnl32.jupiter.engine.asset.AssetType;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.renderer.Texture2D;

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
		texture = new Texture2D(ProjectContext.getAssetDirectory() + "/" + texturePath);
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
		if (texture != null) {
			texture.destroy();
			texture = null;
		}
		setState(AssetState.UNLOADED);
	}
}
