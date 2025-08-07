package com.krnl32.jupiter.engine.asset.serializer;

import com.krnl32.jupiter.engine.asset.handle.Asset;

public interface AssetSerializer<T extends Asset> {
	byte[] serialize(T asset);
	T deserialize(byte[] data);
}
