package com.krnl32.jupiter.engine.asset;

import java.util.UUID;

public abstract class Asset {
	private final AssetID id;
	private final AssetType type;
	private AssetState state;

	public Asset(AssetType type) {
		id = new AssetID(UUID.randomUUID());
		this.type = type;
		this.state = AssetState.UNLOADED;
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
