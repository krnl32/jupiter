package com.github.krnl32.jupiter.asset;

import java.util.UUID;

public abstract class Asset {
	private final AssetID id;
	private final AssetType type;
	private AssetState state;
	private final String rootPath;

	public Asset(AssetType type) {
		id = new AssetID(UUID.randomUUID());
		this.type = type;
		this.state = AssetState.UNLOADED;
		this.rootPath = System.getProperty("user.dir") + "\\assets";
	}

	public AssetID getId() {
		return id;
	}

	public AssetType getType() {
		return type;
	}

	public AssetState getState() {
		return state;
	}

	protected void setState(AssetState state) {
		this.state = state;
	}

	public String getRootPath() {
		return rootPath;
	}

	public boolean isValid() {
		return state != AssetState.INVALID;
	}

	public boolean isLoaded() {
		return state == AssetState.LOADED;
	}

	protected abstract boolean load();
	protected abstract boolean reload();
	protected abstract void unload();
}
