package com.krnl32.jupiter;

import com.krnl32.jupiter.core.Logger;

public class Main {
	public static void main(String[] args) {
		Logger.info("Jupiter Game Engine");
		Game game = new Game("JupiterGame", 1024, 768);
		game.run();
	}
}
