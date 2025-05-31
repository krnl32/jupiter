package com.github.krnl32.jupiter;

import com.github.krnl32.jupiter.core.Engine;
import com.github.krnl32.jupiter.game.Level;
import com.github.krnl32.jupiter.game.World;
import com.github.krnl32.jupiter.scenes.GamePlayScene;

public class Game extends Engine {
	private World world;

	public Game(String name, int width, int height) {
		super(name, width, height);
	}

	@Override
	public boolean onInit() {
		world = new World();

		Level level1 = new Level();
		level1.loadFromFile("level1Data.txt");
		world.addScene("level1", new GamePlayScene(level1));
		world.switchScene("level1");


		return true;
	}

	@Override
	public void onInput(float dt) {

	}

	@Override
	public void onUpdate(float dt) {
		world.onUpdate(dt);
	}

	@Override
	public void onRender(float dt) {
		world.onRender(dt);
	}
}
