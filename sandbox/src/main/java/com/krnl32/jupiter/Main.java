package com.krnl32.jupiter;

import com.krnl32.jupiter.core.Logger;

public class Main {
	public static void main(String[] args) {
		Logger.info("Jupiter Sandbox...");
		Sandbox sandbox = new Sandbox("JupiterSandbox", 1024, 768);
		sandbox.run();
	}
}
