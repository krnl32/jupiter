package com.krnl32.jupiter.engine.asset.loader;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetDescriptor;

public interface AssetLoader<T extends Asset> {
	T load(AssetDescriptor assetDescriptor);
	void unload(T asset);
}
