package com.krnl32.jupiter.engine.oldAsset.types;

import com.krnl32.jupiter.engine.oldAsset.Asset;
import com.krnl32.jupiter.engine.oldAsset.AssetState;
import com.krnl32.jupiter.engine.oldAsset.AssetType;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.renderer.texture.*;

import java.nio.file.Path;

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
		texture = new Texture2D(new TextureSettings(TextureType.TEXTURE_2D, TextureWrapMode.REPEAT, TextureFilterMode.NEAREST, true), Path.of(ProjectContext.getInstance().getAssetDirectory(), texturePath));
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
