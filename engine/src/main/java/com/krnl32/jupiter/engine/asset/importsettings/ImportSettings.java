package com.krnl32.jupiter.engine.asset.importsettings;

import java.util.Map;

public interface ImportSettings {
	Map<String, Object> toMap();
	void fromMap(Map<String, Object> data);
}
