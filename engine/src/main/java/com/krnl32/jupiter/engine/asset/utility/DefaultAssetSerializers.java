package com.krnl32.jupiter.engine.asset.utility;

import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.asset.serializer.AssetSerializerRegistry;
import com.krnl32.jupiter.engine.asset.serializer.types.TextureAssetSerializer;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;

public class DefaultAssetSerializers {
	public static void registerAll() {
		AssetSerializerRegistry.register(AssetType.TEXTURE, TextureAsset.class, new TextureAssetSerializer());
	}
}
