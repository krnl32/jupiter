package com.krnl32.jupiter.engine.asset.serializer;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetType;

import java.util.HashMap;
import java.util.Map;

public class AssetSerializerRegistry {
	private static final Map<Class<? extends Asset>, AssetSerializer<? extends Asset, ?>> classToSerializer = new HashMap<>();
	private static final Map<Class<? extends Asset>, AssetType> classToType = new HashMap<>();
	private static final Map<AssetType, Class<? extends Asset>> typeToClass = new HashMap<>();

	public static <T extends Asset, R> void register(AssetType assetType, Class<T> assetClass, AssetSerializer<T, R> assetSerializer) {
		classToSerializer.put(assetClass, assetSerializer);
		typeToClass.put(assetType, assetClass);
		classToType.put(assetClass, assetType);
	}

	public static <T extends Asset> void unregister(Class<T> assetClass) {
		AssetType type = classToType.remove(assetClass);
		if (type != null) {
			typeToClass.remove(type);
		}
		classToSerializer.remove(assetClass);
	}

	public static <T extends Asset> void unregister(AssetType assetType) {
		Class<? extends Asset> assetClass = typeToClass.remove(assetType);
		if (assetClass != null) {
			classToType.remove(assetClass);
		}
		classToSerializer.remove(assetClass);
	}


	@SuppressWarnings("unchecked")
	public static <T extends Asset, R> AssetSerializer<T, R> getSerializer(Class<T> assetClass) {
		return (AssetSerializer<T,R>) classToSerializer.get(assetClass);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Asset, R> AssetSerializer<T, R> getSerializer(AssetType assetType) {
		return (AssetSerializer<T,R>) getSerializer(typeToClass.get(assetType));
	}

	public static boolean hasSerializer(Class<? extends Asset> assetClass) {
		return classToSerializer.containsKey(assetClass);
	}

	public static boolean hasSerializer(AssetType assetType) {
		return typeToClass.containsKey(assetType);
	}
}
