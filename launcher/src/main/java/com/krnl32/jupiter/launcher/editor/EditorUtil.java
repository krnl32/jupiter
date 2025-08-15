package com.krnl32.jupiter.launcher.editor;

import com.krnl32.jupiter.engine.core.Logger;

import java.nio.file.Path;

public class EditorUtil {
	public static String extractVersionFromEditorPath(Path path) {
		try {
			String fileName = path.getFileName().toString();
			if (fileName.endsWith(".jar")) {
				String baseName = fileName.substring(0, fileName.length() - 4);
				String[] parts = baseName.split("-");
				if (parts.length >= 2) {
					return parts[parts.length - 1];
				}
			}
		} catch (Exception e) {
			Logger.error("EditorUtil failed to extractVersionFromEditorPath for Path({}): {}", path, e.getMessage());
		}
		return "0";
	}
}
