package com.krnl32.jupiter.editor;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.editor.editor.Editor;

public class Main {
	public static void main(String[] args) {
		Logger.info("Jupiter Editor...");
		Editor editor = new Editor("JupiterEditor", 1920, 1080);
		editor.run();
	}
}
