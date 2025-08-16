package com.krnl32.jupiter.editor.events.asset;

import com.krnl32.jupiter.engine.asset.handle.Asset;
import com.krnl32.jupiter.engine.event.Event;

public class AssetSelectedEvent implements Event {
	private final Asset asset;

	public AssetSelectedEvent(Asset asset) {
		this.asset = asset;
	}

	public Asset getAsset() {
		return asset;
	}
}
