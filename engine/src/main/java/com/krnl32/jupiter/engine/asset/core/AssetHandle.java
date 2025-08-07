package com.krnl32.jupiter.engine.asset.core;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetId;

public class AssetHandle<T extends Asset> implements AutoCloseable {
	private final AssetId assetId;
	private final AssetManager assetManager;

	public AssetHandle(AssetId assetId, AssetManager assetManager) {
		this.assetId = assetId;
		this.assetManager = assetManager;
		this.assetManager.acquireAsset(assetId);
	}

	public T get() {
		return assetManager.getLoadedAsset(assetId);
	}

	@Override
	public void close() throws Exception {
		assetManager.releaseAsset(assetId);
	}
}
