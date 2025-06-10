package com.github.krnl32.jupiter;

import com.github.krnl32.jupiter.core.Logger;

public class Main {
	public static void main(String[] args) {
		Logger.info("Jupiter Game Engine");
		Game game = new Game("JupiterGame", 800, 600);
		game.run();
	}
}
