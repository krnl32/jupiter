package com.krnl32.jupiter.engine.asset.importer;

import com.krnl32.jupiter.engine.asset.handle.Asset;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImportResult<T extends Asset> {
	private final T asset;
	private final Map<String, Object> metadata;
	private final String importerName;

	public ImportResult(T asset, String importerName) {
		this.asset = asset;
		this.metadata = new HashMap<>();
		this.importerName = importerName;
	}

	public T getAsset() {
		return asset;
	}

	public Map<String, Object> getMetadata() {
		return Collections.unmodifiableMap(metadata);
	}

	public Object getMetadata(String key) {
		return metadata.get(key);
	}

	public void setMetadata(String key, Object value) {
		metadata.put(key, value);
	}

	public String getImporterName() {
		return importerName;
	}
}
