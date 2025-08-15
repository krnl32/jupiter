package com.krnl32.jupiter.launcher;

import com.krnl32.jupiter.engine.core.EngineSettings;
import com.krnl32.jupiter.engine.core.WindowSettings;
import com.krnl32.jupiter.launcher.launcher.Launcher;

public class Main {
	public static void main(String[] args) {
		Launcher launcher = new Launcher(new EngineSettings(new WindowSettings("JupiterLauncher", 1024, 768, true)));
		launcher.run();
	}
}
