package com.krnl32.jupiter.engine.asset.types;

import com.krnl32.jupiter.engine.asset.Asset;
import com.krnl32.jupiter.engine.asset.AssetState;
import com.krnl32.jupiter.engine.asset.AssetType;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.ui.font.Font;
import com.krnl32.jupiter.engine.utility.FontLoader;

public class FontAsset extends Asset {
	private final String fontPath;
	private final int fontSize;
	private Font font;

	public FontAsset(String fontPath, int fontSize) {
		super(AssetType.FONT);
		this.fontPath = fontPath;
		this.fontSize = fontSize;
	}

	public Font getFont() {
		return font;
	}

	@Override
	protected boolean load() {
		font = FontLoader.loadFont(ProjectContext.getAssetDirectory() + "/" + fontPath, fontSize, 512, 512);
		if (font == null) {
			setState(AssetState.MISSING);
			return false;
		}
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
		if (font != null) {
			font.destroy();
			font = null;
		}
		setState(AssetState.UNLOADED);
	}
}
