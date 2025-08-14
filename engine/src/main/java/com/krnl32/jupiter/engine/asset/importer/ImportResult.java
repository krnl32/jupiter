package com.krnl32.jupiter.engine.asset.importer;

import com.krnl32.jupiter.engine.asset.handle.Asset;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImportResult<T extends Asset> {
	private final T asset;
	private final Map<String, Object> importSettings;
	private final String importerName;

	public ImportResult(T asset, String importerName) {
		this.asset = asset;
		this.importSettings = new HashMap<>();
		this.importerName = importerName;
	}

	public ImportResult(T asset, Map<String, Object> importSettings, String importerName) {
		this.asset = asset;
		this.importSettings = importSettings;
		this.importerName = importerName;
	}

	public T getAsset() {
		return asset;
	}

	public Map<String, Object> getImportSettings() {
		return Collections.unmodifiableMap(importSettings);
	}

	public Object getImportSettings(String key) {
		return importSettings.get(key);
	}

	public void setImportSettings(Map<String, Object> settings) {
		importSettings.putAll(settings);
	}

	public void setImportSettings(String key, Object value) {
		importSettings.put(key, value);
	}

	public String getImporterName() {
		return importerName;
	}
}
