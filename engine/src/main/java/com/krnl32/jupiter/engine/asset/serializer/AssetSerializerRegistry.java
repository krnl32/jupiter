package com.krnl32.jupiter.engine.asset.serializer;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetType;

import java.util.HashMap;
import java.util.Map;

public class AssetSerializerRegistry {
	private final Map<AssetType, AssetSerializer<? extends Asset, ?>> assetTypeToSerializer = new HashMap<>();

	public <T extends Asset, R> void register(AssetType assetType, AssetSerializer<T, R> assetSerializer) {
		assetTypeToSerializer.put(assetType, assetSerializer);
	}

	public void unregister(AssetType assetType) {
		assetTypeToSerializer.remove(assetType);
	}

	@SuppressWarnings("unchecked")
	public <T extends Asset, R> AssetSerializer<T, R> getSerializer(AssetType assetType) {
		return (AssetSerializer<T,R>) assetTypeToSerializer.get(assetType);
	}

	public boolean hasSerializer(AssetType assetType) {
		return assetTypeToSerializer.containsKey(assetType);
	}
}
