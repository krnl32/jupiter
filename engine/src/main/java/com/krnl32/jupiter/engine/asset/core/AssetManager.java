package com.krnl32.jupiter.engine.asset.core;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetId;

public interface AssetManager {
	<T extends Asset> T getAsset(AssetId assetId);
	boolean isAssetLoaded(AssetId assetId);
	boolean isAssetRegistered(AssetId assetId);
	void unloadAsset(AssetId assetId);
}
