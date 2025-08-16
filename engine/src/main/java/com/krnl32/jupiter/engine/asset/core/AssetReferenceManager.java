package com.krnl32.jupiter.engine.asset.core;

import com.krnl32.jupiter.engine.asset.handle.AssetId;

import java.util.HashMap;
import java.util.Map;

public class AssetReferenceManager {
	private final Map<AssetId, Integer> refCounter = new HashMap<>();

	public void acquire(AssetId assetId) {
		int refCount = refCounter.getOrDefault(assetId, 0);
		refCount++;
		refCounter.put(assetId, refCount);
	}

	public boolean release(AssetId assetId) {
		int refCount = refCounter.getOrDefault(assetId, 0);

		if (refCount <= 0) {
			refCounter.remove(assetId);
			return true;
		}

		refCount--;
		refCounter.put(assetId, refCount);

		return false;
	}

	public int getRefCount(AssetId assetId) {
		return refCounter.getOrDefault(assetId, 0);
	}
}
