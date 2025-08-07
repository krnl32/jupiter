package com.krnl32.jupiter.engine.asset.utility;

import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.asset.loader.AssetLoaderRegistry;
import com.krnl32.jupiter.engine.asset.loader.types.TextureAssetLoader;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;

public class DefaultAssetLoaders {
	public static void registerAll() {
		AssetLoaderRegistry.register(AssetType.TEXTURE, TextureAsset.class, new TextureAssetLoader());
	}
}
