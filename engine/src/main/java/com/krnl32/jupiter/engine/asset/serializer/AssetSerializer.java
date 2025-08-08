package com.krnl32.jupiter.engine.asset.serializer;

import com.krnl32.jupiter.engine.asset.handle.Asset;

public interface AssetSerializer<T extends Asset, R> {
	byte[] serialize(T asset);
	R deserialize(byte[] data);
}
