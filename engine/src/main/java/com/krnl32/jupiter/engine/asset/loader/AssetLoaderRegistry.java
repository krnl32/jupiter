package com.krnl32.jupiter.engine.asset.loader;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.asset.handle.AssetType;

import java.util.HashMap;
import java.util.Map;

public class AssetLoaderRegistry {
	private static final Map<Class<? extends Asset>, AssetLoader<? extends Asset>> classToLoader = new HashMap<>();
	private static final Map<Class<? extends Asset>, AssetType> classToType = new HashMap<>();
	private static final Map<AssetType, Class<? extends Asset>> typeToClass = new HashMap<>();

	public static <T extends Asset> void register(AssetType assetType, Class<T> assetClass, AssetLoader<T> assetLoader) {
		classToLoader.put(assetClass, assetLoader);
		typeToClass.put(assetType, assetClass);
		classToType.put(assetClass, assetType);
	}

	public static <T extends Asset> void unregister(Class<T> assetClass) {
		AssetType type = classToType.remove(assetClass);
		if (type != null) {
			typeToClass.remove(type);
		}
		classToLoader.remove(assetClass);
	}

	public static <T extends Asset> void unregister(AssetType assetType) {
		Class<? extends Asset> assetClass = typeToClass.remove(assetType);
		if (assetClass != null) {
			classToType.remove(assetClass);
		}
		classToLoader.remove(assetClass);
	}


	@SuppressWarnings("unchecked")
	public static <T extends Asset> AssetLoader<T> getLoader(Class<T> assetClass) {
		return (AssetLoader<T>) classToLoader.get(assetClass);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Asset> AssetLoader<T> getLoader(AssetType assetType) {
		return (AssetLoader<T>) getLoader(typeToClass.get(assetType));
	}

	public static boolean hasLoader(Class<? extends Asset> assetClass) {
		return classToLoader.containsKey(assetClass);
	}

	public static boolean hasLoader(AssetType assetType) {
		return typeToClass.containsKey(assetType);
	}
}
