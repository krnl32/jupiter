package com.krnl32.jupiter.launcher;

import com.krnl32.jupiter.launcher.launcher.Launcher;

public class Main {
	public static void main(String[] args) {
		Launcher launcher = new Launcher("JupiterLauncher", 1024, 768);
		launcher.run();
	}
}
