package com.krnl32.jupiter.engine.asset.types;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetId;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.renderer.texture.Texture2D;
import com.krnl32.jupiter.engine.renderer.texture.TextureSettings;

public class TextureAsset extends Asset {
	private final TextureSettings settings;
	private final byte[] data;
	private final Texture2D texture;

	public TextureAsset(TextureSettings settings, byte[] data) {
		super(AssetType.TEXTURE);
		this.settings = settings;
		this.data = data;
		this.texture = new Texture2D(settings, data);
	}

	public TextureAsset(AssetId assetId, TextureSettings settings, byte[] data) {
		super(assetId, AssetType.TEXTURE);
		this.settings = settings;
		this.data = data;
		this.texture = new Texture2D(settings, data);
	}

	public TextureSettings getSettings() {
		return settings;
	}

	public byte[] getData() {
		return data;
	}

	public Texture2D getTexture() {
		return texture;
	}
}
