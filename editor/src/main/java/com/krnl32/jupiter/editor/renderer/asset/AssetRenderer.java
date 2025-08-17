package com.krnl32.jupiter.editor.renderer.asset;

import com.krnl32.jupiter.engine.asset.handle.Asset;

public interface AssetRenderer<T extends Asset> {
	void render(T asset);
}
