package com.krnl32.jupiter.engine.asset.loader;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetMetadata;

public interface AssetLoader<T extends Asset> {
	T load(AssetMetadata assetMetadata);
	void unload(T asset);
}
