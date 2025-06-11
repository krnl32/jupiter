package com.github.krnl32.jupiter.asset;

import com.github.krnl32.jupiter.game.Level;

public class LevelAsset extends Asset {
	private final String levelPath;
	private Level level;

	public LevelAsset(String levelFileName) {
		super(AssetType.Level);
		this.levelPath = getRootPath() + "\\levels\\" + levelFileName;
	}

	public Level getLevel() {
		return level;
	}

	@Override
	protected boolean load() {
		level = new Level(levelPath);
		setState(AssetState.Loaded);
		return true;
	}

	@Override
	protected boolean reload() {
		return load();
	}

	@Override
	protected void unload() {
		level = null;
		setState(AssetState.Unloaded);
	}
}
