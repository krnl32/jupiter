package com.krnl32.jupiter.engine.asset.importer;

import com.krnl32.jupiter.engine.asset.importsettings.ImportSettings;

public class ImportRequest {
	private final String source;
	private final byte[] data;
	private final ImportSettings importSettings;

	public ImportRequest(String source, byte[] data, ImportSettings importSettings) {
		this.source = source;
		this.data = data;
		this.importSettings = importSettings;
	}

	public String getSource() {
		return source;
	}

	public byte[] getData() {
		return data;
	}

	public ImportSettings getImportSettings() {
		return importSettings;
	}
}
