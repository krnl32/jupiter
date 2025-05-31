package com.github.krnl32.jupiter.scenes;

import com.github.krnl32.jupiter.game.Level;
import com.github.krnl32.jupiter.game.Scene;

public class GamePlayScene extends Scene {
	private Level level; // level is a data container

	public GamePlayScene(Level level) {
		this.level = level;
	}

	@Override
	public void load() {
		// Loop over Level Data and Spawn Actors based on it
		// For Level.Actors, super.addActor(etc....);
	}
}
