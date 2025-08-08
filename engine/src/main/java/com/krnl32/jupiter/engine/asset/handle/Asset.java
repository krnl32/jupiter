package com.krnl32.jupiter.engine.asset.handle;

public abstract class Asset {
	private final AssetId id;
	private final AssetType type;
	private AssetState state;

	public Asset(AssetType type) {
		this.id	= new AssetId();
		this.type = type;
		this.state = AssetState.NONE;
	}

	public Asset(AssetId id, AssetType type) {
		this.id = id;
		this.type = type;
		this.state = AssetState.NONE;
	}

	public AssetId getId() {
		return id;
	}

	public AssetType getType() {
		return type;
	}

	public AssetState getState() {
		return state;
	}

	public void setState(AssetState state) {
		this.state = state;
	}

	public boolean isValid() {
		return state != AssetState.INVALID;
	}
}
